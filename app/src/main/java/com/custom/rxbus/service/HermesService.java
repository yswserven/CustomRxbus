package com.custom.rxbus.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.custom.rxbus.RxBusRequest;
import com.custom.rxbus.RxBusResponse;
import com.custom.rxbus.RxBusService;
import com.custom.rxbus.core.Hermes;
import com.custom.rxbus.model.InstanceResponseMake;
import com.custom.rxbus.model.ObjectResponseMake;
import com.custom.rxbus.model.ResponseMake;

import androidx.annotation.Nullable;

/**
 * Created by: Ysw on 2020/2/3.
 */
public class HermesService extends Service {
    private RxBusService.Stub mBinder = new RxBusService.Stub() {
        @Override
        public RxBusResponse send(RxBusRequest request) throws RemoteException {
            ResponseMake responseMake = null;
            switch (request.getType()) {
                case Hermes.TYPE_GET:
                    responseMake = new InstanceResponseMake();
                    break;
                case Hermes.TYPE_NEW:
                    responseMake = new ObjectResponseMake();
                    break;
            }
            return responseMake.makeResponse(request);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

}
