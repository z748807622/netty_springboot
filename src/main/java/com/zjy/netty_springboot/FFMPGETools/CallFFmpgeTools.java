package com.zjy.netty_springboot.FFMPGETools;

import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.logging.log4j.util.Strings;

public class CallFFmpgeTools extends CallCmdClass {

    private String ffmpge = "ffmpeg {loop} -re -i {file} -vcodec libx264 -f mpegts -codec:v" +
            " mpeg1video -vf drawtext=\"fontfile=arial.ttf:x=w-tw:fontcollor=white" +
            " :fonesize=30:text='%{localtime\\:%H\\:%M\\:%S}'\" udp://127.0.0.1:8888";

    @Override
    public void sendErrorInfo(ChannelGroup channels, String error) {
        channels.writeAndFlush(new TextWebSocketFrame("{'code':1000,'msg':"+error+"}"));
    }

    @Override
    public void sendLogInfo(ChannelGroup channels, String log) {
        channels.writeAndFlush(new TextWebSocketFrame("{'code':1001,'msg':"+ log +"}"));
    }

    /**
     * 设置播放文件名和是否循环播放
     * @param fileName
     * @param isLoop
     * @return
     */
    public String setFFmpgeOrder(String fileName, boolean isLoop){
        if (isLoop){
            ffmpge = ffmpge.replace("{loop}","-stream_loop -1");
        }
        if(!Strings.isBlank(fileName)){
            ffmpge = ffmpge.replace("{file}",fileName);
        }
        return ffmpge;
    }
}
