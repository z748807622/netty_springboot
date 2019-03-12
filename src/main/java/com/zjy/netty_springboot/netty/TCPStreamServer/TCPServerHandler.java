package com.zjy.netty_springboot.netty.TCPStreamServer;

import com.zjy.netty_springboot.netty.transpondStreamServer.MyChannelHandlerPool;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

@Component
@ChannelHandler.Sharable
public class TCPServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        ByteBuffer byteBuffer = ByteBuffer.allocate(byteBuf.readableBytes());
        byteBuf.readBytes(byteBuffer);
        ByteBuf ret = Unpooled.copiedBuffer(byteBuf);


        Charset charset = Charset.forName("UTF-8");
        CharsetDecoder charsetDecoder = charset.newDecoder();
        byteBuffer.flip();
        CharBuffer charBuffer = charsetDecoder.decode(byteBuffer);
        System.out.println(charBuffer.toString());

        MyChannelHandlerPool.channelGroup.writeAndFlush(new BinaryWebSocketFrame(ret));
        System.out.println("tcpStream is trans");

    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("tcpStream is connect");
        super.channelRegistered(ctx);
    }
}
