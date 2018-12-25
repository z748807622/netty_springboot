package com.zjy.netty_springboot.netty.transpondStreamServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.List;

public class WebSocketFrameDecoder extends MessageToMessageDecoder<WebSocketFrame> {
    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
        ByteBuf buff = msg.content();
        byte[] messageBytes = new byte[buff.readableBytes()];
        buff.readBytes(messageBytes);
        // TODO: 直接内存小心
        ByteBuf bytebuf = PooledByteBufAllocator.DEFAULT.buffer(); // 直接内存
        bytebuf.writeBytes(messageBytes);
        out.add(bytebuf.retain());
    }
}
