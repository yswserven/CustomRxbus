package com.custom.rxbus.core;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by: Ysw on 2020/2/2.
 */
public class RxBus {
    private static RxBus instance;
    private Map<Object, List<SubscribeMethod>> cacheMap;
    private ExecutorService executorService;
    private Handler handler;

    public static RxBus getInstance() {
        if (instance == null) {
            instance = new RxBus();
        }
        return instance;
    }

    private RxBus() {
        this.cacheMap = new HashMap<>();
        this.executorService = Executors.newCachedThreadPool();
        this.handler = new Handler(Looper.getMainLooper());
    }

    public void register(Object activity) {
        List<SubscribeMethod> list = cacheMap.get(activity);
        if (list == null) {
            List<SubscribeMethod> subscribeMethodList = getSubscribeMethods(activity);
            cacheMap.put(activity, subscribeMethodList);
        }
    }

    public void unRegister(Object activity){
        List<SubscribeMethod> list = cacheMap.get(activity);
        if (list!=null){
            cacheMap.remove(activity);
        }
    }

    private List<SubscribeMethod> getSubscribeMethods(Object activity) {
        List<SubscribeMethod> list = new ArrayList<>();
        Class<?> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        while (clazz != null) {
            String name = clazz.getName();
            if (name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.")) {
                break;
            }
            for (Method method : methods) {
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if (subscribe == null) {
                    continue;
                }
                Class<?>[] parameter = method.getParameterTypes();
                if (parameter.length != 1) {
                    throw new RuntimeException("RxBus只能接收一个参数");
                }
                ThreadMode threadMode = subscribe.threadMode();
                SubscribeMethod subscribeMethod = new SubscribeMethod(method, threadMode, parameter[0]);
                list.add(subscribeMethod);
            }
            clazz = clazz.getSuperclass();
        }
        return list;
    }

    public void post(final Object o) {
        Set<Object> keySet = cacheMap.keySet();
        for (final Object activity : keySet) {
            List<SubscribeMethod> list = cacheMap.get(activity);
            if (list != null) {
                for (final SubscribeMethod subscribeMethod : list) {
                    Class<?> type = subscribeMethod.getEventType();
                    if (type.isAssignableFrom(o.getClass())) {
                        switch (subscribeMethod.getThreadMode()) {
                            case MAIN:
                                if (Looper.myLooper() == Looper.getMainLooper()) {
                                    invoke(subscribeMethod, activity, o);
                                } else {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            invoke(subscribeMethod, activity, o);
                                        }
                                    });
                                }
                                break;
                            case ASYNC:
                                if (Looper.myLooper() == Looper.getMainLooper()) {
                                    executorService.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            invoke(subscribeMethod, activity, o);
                                        }
                                    });
                                } else {
                                    invoke(subscribeMethod, activity, o);
                                }
                                break;
                            case POSTING:
                                break;
                            case BACKGROUND:
                                break;
                            case MAIN_ORDERED:
                                break;
                        }

                    }
                }
            }
        }
    }


    /**
     * 反射调用方法
     *
     * @author Ysw created at 2020/2/2 12:37
     */
    private void invoke(SubscribeMethod subscribeMethod, Object activity, Object o) {
        Method method = subscribeMethod.getMethod();
        try {
            method.invoke(activity, o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
