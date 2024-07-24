package com.example.spritgdemo1.controller;

import com.wlkjyy.Eloquent.DB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/pickup")
public class PickupController {

    @Autowired
    DB JavawebDB;


    @GetMapping
    public String Pickup(Model model, HttpServletRequest request, HttpServletResponse response) throws SQLException {

        /**
         *导出所有地址信息
         */
        ArrayList<HashMap<String, Object>> data = JavawebDB.table("pickup").get();
        System.out.println(data);
        model.addAttribute("rows", data);

        return "pickup";
    }


    @PostMapping("/add")
    public String addPickup(Model model, HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String contact = request.getParameter("contact");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        boolean data = JavawebDB.table("pickup").insert(new HashMap<>(){
            {
                put("contact", contact);
                put("address", address);
                put("phone", phone);
            }
        });
        return "pickup";
    }


    @GetMapping("/delete")
    @ResponseBody
    public String deletePickup(Model model, HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String data = request.getParameter("id");
        int state = JavawebDB.table("pickup").where("id", data).delete();

        boolean i = Boolean.parseBoolean(String.valueOf(state));

        System.out.println(i);
        if(!i) {
            System.out.println(false);
            return "<script>window.alert(\"删除成功\");window.location.href=\"/pickup\"</script>";
        }
        return data;
    }


    @ResponseBody
    @GetMapping("/edit")
    public HashMap<String, Object> editPickup(Model model, HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String id = request.getParameter("id");
        return JavawebDB.table("pickup").where("id", id).first();
    }


    @PostMapping("/edit")
    @ResponseBody
    public String updatePickup(Model model, HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String id = request.getParameter("id");
        String contact = request.getParameter("contact");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");

        int state = JavawebDB.table("pickup").where("id",id).update(new HashMap<String,Object>(){
            {
                put("contact", contact);
                put("address", address);
                put("phone", phone);
            }
        });

        if(state == 1) {
            return "<script>window.alert(\"编辑成功\");window.location.href=\"/pickup\"</script>";
        }

        return id;
    }



}
