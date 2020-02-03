package com.custom.rxbus.model;

/**
 * Created by: Ysw on 2020/2/2.
 */
public class RxBusTestModel {
    private String name;
    private String age;

    public RxBusTestModel() {
    }

    public RxBusTestModel(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "RxBusTestModel{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
