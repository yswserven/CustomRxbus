package com.custom.rxbus.core;

import android.content.Context;

import com.custom.rxbus.service.RxBusService;


/**
 * Created by: Ysw on 2020/2/3.
 */
public class RxBusHermes {
    private static volatile RxBusHermes singleton = null;

    private Context mContext;

    private RxBusHermes() {
    }

    public static RxBusHermes getInstance() {
        if (singleton == null) {
            synchronized (RxBusHermes.class) {
                if (singleton == null) {
                    singleton = new RxBusHermes();
                }
            }
        }
        return singleton;
    }

    public void connect(Context context, Class<? extends RxBusService> service) {
        connectApp(context, null, service);
    }

    private void connectApp(Context context, String packageName,
                            Class<? extends RxBusService> service) {
        init(context);
    }

    private void init(Context context) {
        mContext = context.getApplicationContext();
    }


    public void register(Class<?> clazz) {

    }
}
