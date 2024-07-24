package com.example.spritgdemo1;

public class Caller {
    private Callback callback;
    public Caller(Callback callback) {
        this.callback = callback;
    }

    public void doSomething() {
        String message = "Hello World!";
        callback.onCallback(message);
    }



}
