package com.zjy.netty_springboot.netty.chat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class WebSocketHandler extends ChannelInboundHandlerAdapter {

    WebSocketServerHandshaker handshaker;

    private static Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FullHttpRequest){
            FullHttpRequest request = (FullHttpRequest) msg;

            if(!request.decoderResult().isSuccess() || !"websocket".equals(request.headers().get("Upgrade"))){
                DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);

                if(response.status().code() != 200){
                    ByteBuf byteBuf = Unpooled.copiedBuffer("请求异常",CharsetUtil.UTF_8);
                    response.content().writeBytes(byteBuf);
                    byteBuf.release();
                }
                ctx.writeAndFlush(response);
                return;
            }

            WebSocketServerHandshakerFactory webSocketServerHandshakerFactory = new WebSocketServerHandshakerFactory("ws://localhost:6666/ws",null,false);
            handshaker = webSocketServerHandshakerFactory.newHandshaker(request);

            if(handshaker == null){
                webSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            }else {
                handshaker.handshake(ctx.channel(),request);
            }
        }else if(msg instanceof WebSocketFrame) {

            // region 判断是否是关闭链路的指令
            if(msg instanceof CloseWebSocketFrame){
                logger.info("关闭链路的指令 msg...");
                handshaker.close(ctx.channel(),(CloseWebSocketFrame) msg);
            }
            // region 纯文本消息
            else if(msg instanceof TextWebSocketFrame){
                logger.info("纯文本消息 msg...");
                String content = ((TextWebSocketFrame) msg).text();
                ctx.writeAndFlush(new TextWebSocketFrame("serverContent: " + msg));
            }
            // region 判断是否是ping消息
            else if(msg instanceof PingWebSocketFrame){
                logger.info("ping msg...");
                ctx.writeAndFlush(new PongWebSocketFrame(((PingWebSocketFrame)msg).content().retain()));
            }
            // region 二进制消息 此处使用了MessagePack编解码方式
            /*if (msg instanceof BinaryWebSocketFrame) {
                BinaryWebSocketFrame binaryWebSocketFrame = (BinaryWebSocketFrame) msg;
                ByteBuf content = binaryWebSocketFrame.content();
                logger.info("├ [二进制数据]:{}", content);
                final int length = content.readableBytes();
                final byte[] array = new byte[length];
                content.getBytes(content.readerIndex(), array, 0, length);
                MessagePack messagePack = new MessagePack();
                WebSocketMessageEntity webSocketMessageEntity = messagePack.read(array, WebSocketMessageEntity.class);
                logger.info("├ [解码数据]: {}", webSocketMessageEntity);
                WebSocketUsers.sendMessageToUser(webSocketMessageEntity.getAcceptName(), webSocketMessageEntity.getContent());
            }*/

        }
    }
}
