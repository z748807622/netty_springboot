package com.zjy.netty_springboot.netty.messageEntity;

import java.nio.channels.Channel;
import java.util.concurrent.atomic.AtomicInteger;

public class UserInfo {

    private static AtomicInteger uidFener = new AtomicInteger(1000);

    private boolean isAuth = true;

    private long time = 0;

    private int userId;

    private String nick;

    private Channel channel;

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

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
