// HermesService.aidl
package com.custom.rxbus;

// Declare any non-default types here with import statements

import com.custom.rxbus.RxBusRequest;
import com.custom.rxbus.RxBusResponse;

interface RxBusService {
    RxBusResponse send(in RxBusRequest request);
}
