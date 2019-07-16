package com.a155337.lighttea.Object;


import java.io.Serializable;

public class Member implements Serializable {
    private String name;
    private float balance;

    public Member(String name){
        this.name = name;
        balance = 0.0f;
    }

    public String getName(){
        return name;
    }
}
