package com.example.spritgdemo1.controller;


import com.wlkjyy.Eloquent.DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {
    @Autowired
    DB JavawebDB;

    @GetMapping("/index")
    public String text(){
        return "index";
    }


    @GetMapping("/text.html")
    public String html(){
        return "text";
    }



}


