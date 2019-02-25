package com.hongyu.zorelib.bean;


public class BaseEntity<T> {
    public int code;
    public String msg = "";
    public T data;

    @Override
    public String toString() {
        return "BaseEntity{" +
                "status='" + code + '\'' +
                ", info='" + msg + '\'' +
                ", body=" + data +
                '}';
    }
}
