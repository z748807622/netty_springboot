package com.zjy.netty_springboot.netty.messageEntity;

public enum  MsgType {

    PING(101,"ping"),
    PONG(102,"pong"),
    USERMSG(200,"普通消息"),
    ADMINMSG(300,"管理员消息"),
    SYSTEMMSG(400,"系统消息");

    private final int code;
    private final String desc;

    private MsgType(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    //根据key获取枚举
    public static MsgType getEnumByKey(int code){

        for (MsgType msgType : MsgType.values()){
            if (msgType.getCode() == code){
                return msgType;
            }
        }
        return null;

    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}


