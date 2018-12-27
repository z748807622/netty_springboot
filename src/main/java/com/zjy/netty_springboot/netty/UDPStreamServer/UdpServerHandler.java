package com.zjy.netty_springboot.netty.UDPStreamServer;

import com.zjy.netty_springboot.netty.transpondStreamServer.MyChannelHandlerPool;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.socket.DatagramPacket;

public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static Logger logger = LoggerFactory.getLogger(UdpServerHandler.class);

    public static ChannelHandlerContext CTX;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        logger.info("UDP通道已经连接");
        CTX = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //logger.info(msg.getClass().getName());
        //System.out.println(msg instanceof DatagramPacket);
        if(msg instanceof DatagramPacket){
            //logger.info("channelRead 开始接收来自client的数据");
            //logger.info("clientMessage is: "+msg);
            DatagramPacket dd = (DatagramPacket) msg;
            ByteBuf bb = Unpooled.copiedBuffer(dd.content());
            //FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            //response.headers().set(HttpHeaderNames.CONTENT_TYPE,"video/mp4");
            //response.content().writeBytes(bb);
            //System.out.println("收到二进制流");
            MyChannelHandlerPool.channelGroup.writeAndFlush(new BinaryWebSocketFrame(bb));
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
// 接受client的消息
        logger.info("开始接收来自client的数据");
        //final ByteBuf buf = Unpooled.copiedBuffer(msg.getData());
        //int readableBytes = buf.readableBytes();
        //byte[] content = new byte[readableBytes];
        //buf.readBytes(content);
        //String clientMessage = new String(content,"UTF-8");
        logger.info("clientMessage is: "+msg.content());
        /*if(clientMessage.contains("UdpServer")){
            ctx.writeAndFlush(new DatagramPacket(Unpooled.wrappedBuffer("helloClient".getBytes()),msg.sender()));
        }*/

    }
}
