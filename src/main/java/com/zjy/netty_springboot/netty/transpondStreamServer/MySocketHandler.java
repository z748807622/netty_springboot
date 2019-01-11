package com.zjy.netty_springboot.netty.transpondStreamServer;

import com.zjy.netty_springboot.Config;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class MySocketHandler extends SimpleChannelInboundHandler<Object> {

    private static Logger logger = LoggerFactory.getLogger(MySocketHandler.class);

    WebSocketServerHandshaker handshaker;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocket(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent evnet = (IdleStateEvent) evt;
            // 判断Channel是否读空闲, 读空闲时移除Channel
            if (evnet.state().equals(IdleState.READER_IDLE)) {
                //final String remoteAddress = NettyUtil.parseChannelRemoteAddr(ctx.channel());
                logger.warn("NETTY SERVER PIPELINE: IDLE exception [{}]", 1);
                //UserInfoManager.removeChannel(ctx.channel());
                //UserInfoManager.broadCastInfo(ChatCode.SYS_USER_COUNT,UserInfoManager.getAuthUserCount());
            }
        }
        ctx.fireUserEventTriggered(evt);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        MyChannelHandlerPool.channelGroup.remove(ctx.channel());
        logger.info("ip-{}-  退出",ctx.channel().localAddress().toString());
    }



    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {

        //HttpRequest request = ((HttpRequest) _request);
        //HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);
        //response.headers().set("Sec-Websocket-Protocol",request.headers().get("Sec-Websocket-Protocol"));
        //DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
        //response.headers().set("Sec-WebSocket-Protocol",request.headers().get("Sec-WebSocket-Protocol"));
        if (!request.decoderResult().isSuccess() || !"websocket".equals(request.headers().get("Upgrade"))) {
            logger.warn("protobuf don't support websocket");
            ctx.channel().close();
            return;
        }
        WebSocketServerHandshakerFactory handshakerFactory = new WebSocketServerHandshakerFactory(
                Config.TRANSPOND_STREAM_SERVER_WEBSOCKET_URL, null, false);
        handshaker = handshakerFactory.newHandshaker(request);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            // 动态加入websocket的编解码处理
            //Set<Map.Entry<String,String>> heads = new HashSet<>();
            //Map<String,String> head = new HashMap<>();
            //head.put("Sec-WebSocket-Protocol",request.headers().get("Sec-WebSocket-Protocol"));
            //HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);
            //response.headers().set("Sec-Websocket-Protocol",request.headers().get("Sec-Websocket-Protocol"));
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
            response.headers().set("Sec-WebSocket-Protocol",request.headers().get("Sec-WebSocket-Protocol"));
            handshaker.handshake(ctx.channel(), request,response.headers(),ctx.channel().newPromise());
            /*UserInfo userInfo = new UserInfo();
            userInfo.setAddr(NettyUtil.parseChannelRemoteAddr(ctx.channel()));
            // 存储已经连接的Channel
            UserInfoManager.addChannel(ctx.channel());*/
            //ctx.writeAndFlush(response);

            //sendHttpResponse(ctx, request,response);
            //logger.info("连接成功");

            //添加到channelGroup 通道组
            MyChannelHandlerPool.channelGroup.add(ctx.channel());
            //ctx.pipeline().writeAndFlush(new TextWebSocketFrame("连接成功"));
            /*DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK);
            response.headers().set("Sec-WebSocket-Protocol",request.headers().get("Sec-WebSocket-Protocol"));
            response.headers().set("Sec-WebSocket-Version",request.headers().get("Sec-WebSocket-Version"));
            response.headers().set("Connection","Upgrade");
            response.headers().set("Sec-WebSocket-Accept","HSmrc0sMlYUkAGmm5OPpG2HaGWk=");
            //response.headers().set(request.headers());
            HttpUtil.setContentLength(response, 0);
            ctx.writeAndFlush(response);*/
            //ctx.writeAndFlush(new TextWebSocketFrame("连接成功!"));
        }
    }

    private void handleWebSocket(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 判断是否关闭链路命令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            MyChannelHandlerPool.channelGroup.remove(ctx.channel());
            logger.info("ip-{}-  退出",ctx.channel().localAddress().toString());
            return;
        }
        // 判断是否Ping消息
        if (frame instanceof PingWebSocketFrame) {
            logger.info("ping message:{}", frame.content().retain());
            ctx.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        // 支持文本消息
        if (frame instanceof TextWebSocketFrame) {
            //throw new UnsupportedOperationException(frame.getClass().getName() + " frame type not supported");
            String message = ((TextWebSocketFrame) frame).text();
            Channel channel = ctx.channel();
            logger.info("收到消息：{}",message);
            //后续消息交给MessageHandler处理
            ctx.fireChannelRead(frame.retain());
            String str = message;
            //ByteBuf buf = Unpooled.copiedBuffer(str.getBytes());
            MyChannelHandlerPool.channelGroup.writeAndFlush(new TextWebSocketFrame(str));
            return;
        }

        if(frame instanceof BinaryWebSocketFrame){
            logger.info("接收到二进制数据");
            ByteBuf buf = frame.content();
            ByteBuf ret = Unpooled.copiedBuffer(buf);
            //ctx.writeAndFlush(new TextWebSocketFrame("200"));
            //ctx.writeAndFlush(new BinaryWebSocketFrame(ret));
            MyChannelHandlerPool.channelGroup.writeAndFlush(new BinaryWebSocketFrame(ret));

        }

    }
}
