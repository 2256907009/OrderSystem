package com.example.spritgdemo1.pojo;

import lombok.Data;
import org.springframework.stereotype.Controller;

@Data
@Controller
public class Inquire {
    private String orderName;
    private float orderMoney;
    private String orderDate;
    private String orderAddress;
    private String orderNumber;
    private Integer id;


    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public float getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(float orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Inquire{" +
                "orderName='" + orderName + '\'' +
                ", orderMoney=" + orderMoney +
                ", orderDate='" + orderDate + '\'' +
                ", orderAddress='" + orderAddress + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", id=" + id +
                '}';
    }
}
