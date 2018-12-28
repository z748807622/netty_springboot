package com.zjy.netty_springboot.netty.uploadVideoServer;

import com.zjy.netty_springboot.Config;
import com.zjy.netty_springboot.netty.transpondStreamServer.WebSocketFramePrepender;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrameDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class UploadVideoServer {

    @Bean(name = "getUploadDefLoopGroup")
    public DefaultEventLoopGroup getDefLoopGroup(){
        return new DefaultEventLoopGroup(8, new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "DEFAULTEVENTLOOPGROUP_" + index.incrementAndGet());
            }
        });
    }

    @Bean(name = "getUploadBossGroup")
    public NioEventLoopGroup bossGroup(){
        return new NioEventLoopGroup(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "BOSS_" + index.incrementAndGet());
            }
        });
    }

    @Bean(name = "getUploadWorkGroup")
    public NioEventLoopGroup workGroup(){
        return new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 10, new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "WORK_" + index.incrementAndGet());
            }
        });
    }

    @Bean(name = "UploadBootstrap")
    public ServerBootstrap bootstrap(){

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup(), workGroup())
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.SO_SNDBUF,1024 * 256)
                .option(ChannelOption.SO_RCVBUF, 1024 * 256)
                //.option(ChannelOption.TCP_NODELAY, true)
                //.option(ChannelOption.SO_BACKLOG, 1024)
                .localAddress(new InetSocketAddress(Config.UPLOAD_VIDEO_SERVER_PORT))
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(getDefLoopGroup(),
                                //请求解码器
                                new HttpServerCodec(),
                                //将多个消息转换成单一的消息对象
                                new HttpObjectAggregator(Integer.MAX_VALUE),
                                //支持异步发送大的码流，一般用于发送文件流
                                //new StringDecoder(),
                                //new StringEncoder(),
                                new ChunkedWriteHandler()
                                //检测链路是否读空闲
                                //new IdleStateHandler(60, 0, 0),
                                //处理握手和认证
                                //new UserAuthHandler(),
                                //处理消息的发送
                                ,new UploadVideoHandler()

                        );
                    }
                });
        return serverBootstrap;
    }


}
