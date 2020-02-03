package com.custom.rxbus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by: Ysw on 2020/2/3.
 */
public class RxBusResponse implements Parcelable {
    protected RxBusResponse(Parcel in) {
    }

    public static final Creator<RxBusResponse> CREATOR = new Creator<RxBusResponse>() {
        @Override
        public RxBusResponse createFromParcel(Parcel in) {
            return new RxBusResponse(in);
        }

        @Override
        public RxBusResponse[] newArray(int size) {
            return new RxBusResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
