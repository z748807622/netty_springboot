package com.zjy.netty_springboot.netty.webSocketChatServer.entity;

import io.netty.channel.Channel;

import java.util.concurrent.atomic.AtomicInteger;

public class UserInfo {
    private static AtomicInteger uidGener = new AtomicInteger(1000);

    private boolean isAuth = false; // 是否认证
    private boolean isAllow = true; // 是否可以发言
    private long time = 0;  // 登录时间
    private int userId;     // UID
    private String nick;    // 昵称
    private String addr;    // 地址
    private Channel channel;// 通道

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId() {
        this.userId = uidGener.incrementAndGet();
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public boolean isAllow() {
        return isAllow;
    }

    public void setAllow(boolean allow) {
        isAllow = allow;
    }
}
