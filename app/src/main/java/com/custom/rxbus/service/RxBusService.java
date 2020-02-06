package com.custom.rxbus.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.custom.rxbus.RxBusRequest;
import com.custom.rxbus.RxBusResponse;

import androidx.annotation.Nullable;

/**
 * Created by: Ysw on 2020/2/3.
 */
public class RxBusService extends Service {
    private com.custom.rxbus.RxBusService.Stub mBinder = new com.custom.rxbus.RxBusService.Stub() {
        @Override
        public RxBusResponse send(RxBusRequest request) throws RemoteException {
            return null;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

}
