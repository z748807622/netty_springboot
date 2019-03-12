package com.zjy.netty_springboot.netty.messageEntity;

import java.io.Serializable;

public class SendMsg implements Serializable {
    /**
     * 去哪儿
     */
    private String go;

    /**
     * 消息类型
     */
    private MsgType type;

    private String content;

    public String getGo() {
        return go;
    }

    public void setGo(String go) {
        this.go = go;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
