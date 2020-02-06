package com.custom.rxbus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by: Ysw on 2020/2/3.
 */
public class RxBusRequest implements Parcelable {
    private String data;
    private int type;

    public String getData() {
        return data;
    }

    public int getType() {
        return type;
    }

    public RxBusRequest(String data, int type) {
        this.data = data;
        this.type = type;
    }

    protected RxBusRequest(Parcel in) {
        data = in.readString();
        type = in.readInt();
    }

    public static final Creator<RxBusRequest> CREATOR = new Creator<RxBusRequest>() {
        @Override
        public RxBusRequest createFromParcel(Parcel in) {
            return new RxBusRequest(in);
        }

        @Override
        public RxBusRequest[] newArray(int size) {
            return new RxBusRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
        dest.writeInt(type);
    }
}
