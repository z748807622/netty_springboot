package com.zjy.netty_springboot;

public class CallBackMain extends CallBackTest {
    @Override
    public int callBackAdd(int a, int b) {
        return a * b;
    }

    public static void main(String[] args) {
        System.out.println(new CallBackMain().getValue(2,3));
    }


}
