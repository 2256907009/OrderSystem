package com.example.spritgdemo1.controller;

import com.wlkjyy.Eloquent.DB;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;


@Controller
public class DeleteController {

    @Autowired
    DB JavawebDB;

    @GetMapping("/delete")
    @ResponseBody
    public String delete(HttpServletRequest request) throws SQLException {

        String data = request.getParameter("id");

        int state = JavawebDB.table("actual").where("id", data).delete();

        boolean i = Boolean.parseBoolean(String.valueOf(state));

        if(!i){
            System.out.println(i);
            return "<script>window.alert(\"删除成功\");window.location.href=\"/content\"</script>";
        }else{
            System.out.println(i);
            return "<script>window.alert(\"删除失败\");window.location.href=\"/content\"</script>";
        }

    }

}
