package com.saphulot.rpc.common;

public enum ResponseCode {
    SUCCESS ,FAIL, ERROR404;
    private String name;
    private int value;

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }




}
