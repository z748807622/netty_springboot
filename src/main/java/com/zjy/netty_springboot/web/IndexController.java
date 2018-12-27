package com.zjy.netty_springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {

    @RequestMapping()
    public String getIndex(){
        return "html/video2";
    }

}
