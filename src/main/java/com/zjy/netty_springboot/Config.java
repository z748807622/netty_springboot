package com.zjy.netty_springboot;

public class Config {

    public static String DEFAULT_HOST = "127.0.0.1";

    /**
     * 转发ffmpge UDP推流的websocket端口
     */
    public static int TRANSPOND_STREAM_SERVER_PORT = 8888;

    /**
     * 转发ffmpge UDP推流的websocket地址
     */
    public static String TRANSPOND_STREAM_SERVER_WEBSOCKET_URL = "ws://localhost:8888/ws";

    public static String ADDRESS_URL = "127.0.0.1";

    /**
     * 接收ffmpge UDP推流
     */
    public static int UDP_STREAM_SERVER_PORT = 4444;

    /**
     * 接收上传文件的端口
     */
    public static int UPLOAD_VIDEO_SERVER_PORT = 7777;


}
