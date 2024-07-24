package com.example.spritgdemo1.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/service_supper")
public class ServiceSupperController {

    @GetMapping
    public String serviceSupper() {
        return "service_supper";
    }

    @GetMapping("/shipping")
    public String serviceSupperShipping() {
        return "shipping";
    }

    @GetMapping("/content")
    public String serviceSupperContent() {
        return "content";
    }









}
