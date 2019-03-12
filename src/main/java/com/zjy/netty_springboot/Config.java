package com.zjy.netty_springboot;

public class Config {

    public static String DEFAULT_HOST = "192.168.0.104";

    /**
     * 转发ffmpge UDP推流的websocket端口
     */
    public static int TRANSPOND_STREAM_SERVER_PORT = 8888;

    /**
     * 转发ffmpge UDP推流的websocket地址
     */
    public static String TRANSPOND_STREAM_SERVER_WEBSOCKET_URL = "ws://localhost:8888/ws";

    public static String ADDRESS_URL = "192.168.0.104";

    /**
     * 接收ffmpge UDP推流
     */
    public static int UDP_STREAM_SERVER_PORT = 4444;

    /**
     * 接收上传文件的端口
     */
    public static int UPLOAD_VIDEO_SERVER_PORT = 7777;

    public static String UPLOAD_VIDEO_SERVER_WEBSOCKET_URL = "ws://localhost:7777/ws";

    public static String UPLOAD_VIDEO_FILE_DIR = "D:/test/";

    /**
     * 聊天室websocket端口
     */
    public static int CHAT_SERVER_PORT = 9999;

    public static String CHAT_SERVER_WEBSOCKET_URL = "ws//localhost:9999/ws";

    /**
     *
     */
    public static int TCP_STREAM_PORT = 6666;


}
