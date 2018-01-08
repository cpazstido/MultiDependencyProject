package com.cf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.charset.Charset;

@Controller
public class TestController {
    @RequestMapping(value = "index")
    public String index(Model model){
        String str = "加了我科技风";
        model.addAttribute("str","几个库了感觉");
        System.out.println(System.getProperty("file.encoding"));
        System.out.println(Charset.defaultCharset().name());
        return "index";
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("file.encoding"));
        System.out.println(Charset.defaultCharset().name());
        String str = "进来撒大家弗兰克";
        System.out.println(str);
    }
}
