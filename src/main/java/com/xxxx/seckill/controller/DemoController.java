package com.xxxx.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/3/2 16:12
 * @Description:
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    /**
     * 测试页面跳转
     * @param model
     * @return
     */
    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("name","xxxxx") ;
        return "hello" ;
    }
}
