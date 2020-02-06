package com.custom.rxbus.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.custom.rxbus.RxBusRequest;
import com.custom.rxbus.RxBusResponse;
import com.custom.rxbus.RxBusService;
import com.custom.rxbus.service.HermesService;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by: Ysw on 2020/2/3.
 */
public class ServiceConnectionManager {
    private static volatile ServiceConnectionManager singleton = null;
    private final ConcurrentHashMap<Class<? extends HermesService>, RxBusService>
            mRxBusServices = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Class<? extends HermesService>, HermesServiceConnection>
            mHermesServiceConnections = new ConcurrentHashMap<>();

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

    public void bind(Context context, String packageName, Class<? extends HermesService> service) {
        HermesServiceConnection connection = new HermesServiceConnection(service);
        mHermesServiceConnections.put(service, connection);
        Intent intent;
        if (TextUtils.isEmpty(packageName)) {
            intent = new Intent(context, service);
        } else {
            intent = new Intent();
            intent.setClassName(packageName, service.getName());
        }
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }


    public RxBusResponse request(Class<HermesService> hermesServiceClass, RxBusRequest request) {
        RxBusService rxBusService = mRxBusServices.get(hermesServiceClass);
        if (rxBusService != null) {
            try {
                return rxBusService.send(request);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private class HermesServiceConnection implements ServiceConnection {
        private Class<? extends HermesService> mClass;

        HermesServiceConnection(Class<? extends HermesService> service) {
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
