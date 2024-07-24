package com.example.spritgdemo1.controller;

import com.wlkjyy.Eloquent.DB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.HashMap;


@Controller
public class RegisterController {

    @GetMapping("/register")
    public String register(HttpServletRequest request){
        return "register";
    }

    @Autowired
    DB JavawebDB;


    @PostMapping("/register")
    @ResponseBody
    public String register_page(HttpServletRequest request , HttpServletResponse response) throws SQLException {
        String username = request.getParameter("username_register");
        String password = request.getParameter("password_register");
        String email = request.getParameter("email");

        boolean rename = JavawebDB.table("login").where("username", username).exists();


        if(!rename){

            boolean data = JavawebDB.table("login").insert(new HashMap<String, Object>() {
                {
                    put("username",username);
                    put("password",password);
                    put("email",email);
                }

            });

            if(data){
                return "<script>window.alert(\"注册成功\");window.location.href=\"/index\"</script>";
            }else{
                return "<script>window.alert(\"注册失败\");window.location.href=\"/index\"</script>";
            }

        }else{
            return "<script>window.alert(\"用户已存在！\");window.location.href=\"/index\"</script>";
        }

    }

}
