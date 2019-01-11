package com.zjy.netty_springboot.netty.transpondStreamServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.AttributeKey;

import java.util.List;
import java.util.Map;

public class MyTwoSocketHandler extends WebSocketServerProtocolHandler {
    public MyTwoSocketHandler(String websocketPath) {
        super(websocketPath);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        super.handlerAdded(ctx);
    }

}
