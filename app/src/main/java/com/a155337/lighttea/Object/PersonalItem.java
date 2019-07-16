package com.a155337.lighttea.Object;

import com.a155337.lighttea.Activity.MainActivity;
import com.a155337.lighttea.Object.Member;

public class PersonalItem {
    private Member member;
    private float price;
    private String name;

    public PersonalItem(String name, String price){
        this.price = Float.valueOf(price);
        this.name = name;
        member = MainActivity.findMemberByName(name);
    }

    public String getName(){
        return name;
    }

    public float getPrice(){
        return price;
    }
}
