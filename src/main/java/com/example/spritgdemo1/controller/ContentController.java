package com.example.spritgdemo1.controller;

import com.example.spritgdemo1.utils.CookieUtil;
import com.example.spritgdemo1.utils.TokenGenerator;
import com.wlkjyy.Eloquent.DB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class ContentController {


    @Autowired
    DB JavawebDB;

    @GetMapping("/content")
    public String contentPage(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {

        boolean iscookie = CookieUtil.isCookieExpired(request);
        System.out.println(iscookie);

        if(iscookie){
            System.out.println("cookie还在有效期");
        }else {
            //重新生成cookie
            String token = TokenGenerator.generateRandomToken(16);
            CookieUtil.regenerateCookie(response,token,60);
        }


        /**
         * 检索所有行
         */
        ArrayList<HashMap<String, Object>> data = JavawebDB.table("actual").get();

        System.out.println(data);

        model.addAttribute("rows",data);


        return "content";
    }





}
