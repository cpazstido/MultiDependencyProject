package com.cf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/aop")
public class TestController {

    @RequestMapping("test")
    public void testAop(HttpServletRequest rq, HttpServletResponse response){
        try {
            response.addHeader("pragma", "no-cache");
            response.addHeader("cache-control", "no-cache,must-revalidate");
            response.addHeader("expires", "0");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setDateHeader("Expires", 0L);
            response.getWriter().write("aop");
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
