package com.example.spritgdemo1.controller;

import com.wlkjyy.Eloquent.DB;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.HashMap;

@Controller
public class EditController {

    @Autowired
    DB JavawebDB;



    @GetMapping("/edit")
    public String edit(HttpServletRequest r, Model model) throws SQLException {

        String id = r.getParameter("id");

        HashMap<String, Object> data = JavawebDB.table("actual").where("id", id).first();

        model.addAttribute("row", data);


        return "edit";
    }


    @PostMapping("/edit")
    @ResponseBody
    public String editSubmit(HttpServletRequest request) throws SQLException {

        String orderNumber = request.getParameter("orderNumber");
        String orderName = request.getParameter("orderName");
        String orderMoney = request.getParameter("orderMoney");
        String orderDate = request.getParameter("orderDate");
        String orderAddress = request.getParameter("orderAddress");
        String id = request.getParameter("id");

        int state = JavawebDB.table("actual").where("id", id).update(new HashMap<>() {
            {
                put("orderNumber",orderNumber);
                put("orderName",orderName);
                put("orderDate",orderDate);
                put("orderAddress",orderAddress);
                put("orderMoney",orderMoney);

            }
        });

        if(state > 0){

            return "<script>window.alert(\"编辑成功\");window.location.href=\"/content\"</script>";
        }else{

            return "<script>window.alert(\"编辑失败\");window.location.href=\"/content\"</script>";
        }


    }

}
