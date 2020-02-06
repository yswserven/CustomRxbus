package com.custom.rxbus.core;

/**
 * Created by: Ysw on 2020/2/6.
 */
public class TypeCenter {
    private static volatile TypeCenter singleton = null;


    private TypeCenter() {}

    public static TypeCenter getInstance() {
        if (singleton == null) {
            synchronized (TypeCenter.class) {
                if (singleton == null) {
                    singleton = new TypeCenter();
                }
            }
        }
        return singleton;
    }
}
