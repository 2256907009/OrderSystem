package com.example.spritgdemo1.controller;

import com.wlkjyy.Eloquent.DB;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.HashMap;


@Controller
public class AddController {

    @GetMapping("/add")
    public String addage(){
        return "add";
    }


    @Autowired
    DB JavawebDB;

    @PostMapping("/add")
    @ResponseBody
    public String add_page(HttpServletRequest request) throws SQLException {
        String orderNumber = request.getParameter("orderNumber");
        String orderName = request.getParameter("orderName");
        String orderMoney = request.getParameter("orderMoney");
        String orderDate = request.getParameter("orderDate");
        String orderAddress = request.getParameter("orderAddress");

        boolean data = JavawebDB.table("actual").insert(new HashMap<String, Object>() {
            {
                put("orderNumber",orderNumber);
                put("orderName",orderName);
                put("orderDate",orderDate);
                put("orderAddress",orderAddress);
                put("orderMoney",orderMoney);
            }
        });


        if(data){
            return "<script>window.alert(\"添加成功\");window.location.href=\"/content\"</script>";
        }else{

            return "<script>window.alert(\"添加失败\");window.location.href=\"/content\"</script>";
        }


    }



}
