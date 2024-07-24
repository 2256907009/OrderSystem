package com.example.spritgdemo1;

public class Main {

    public static void main(String[] args) {
        Callback callback = new Callback() {
            @Override
            public void onCallback(String message) {
                System.out.println("回调函数被调用：" + message);
            }
        };


        Caller caller = new Caller(callback);
        caller.doSomething();








    }










}
