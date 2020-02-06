package com.custom.rxbus.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;

import com.custom.rxbus.service.RxBusService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Ysw on 2020/2/3.
 */
public class ServiceConnectionManager {
    private static volatile ServiceConnectionManager singleton = null;
    private Map<Class<? extends RxBusService>, com.custom.rxbus.RxBusService> mRxBusServices = new HashMap<>();

    private ServiceConnectionManager() {
    }

    public static ServiceConnectionManager getInstance() {
        if (singleton == null) {
            synchronized (ServiceConnectionManager.class) {
                if (singleton == null) {
                    singleton = new ServiceConnectionManager();
                }
            }
        }
        return singleton;
    }

    public void bind(Context context, String packageName,
                     Class<? extends RxBusService> service) {
        Intent intent;
        if (TextUtils.isEmpty(packageName)) {
            intent = new Intent(context, service);
        } else {
            intent = new Intent();
            intent.setClassName(packageName, service.getName());
        }
        RxBusHermesServiceConnection connection = new RxBusHermesServiceConnection(service);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);


    }

    private class RxBusHermesServiceConnection implements ServiceConnection {
        private Class<? extends RxBusService> mClass;

        RxBusHermesServiceConnection(Class<? extends RxBusService> service) {
            mClass = service;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            com.custom.rxbus.RxBusService rxBusService = com.custom.rxbus.RxBusService.Stub.asInterface(service);
            mRxBusServices.put(mClass, rxBusService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
