package com.zjy.netty_springboot;

public abstract class CallBackTest {
    public abstract int callBackAdd(int a, int b);
    public int getValue(int a, int b){
        return callBackAdd(a,b);
    }
}
