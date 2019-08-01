package com.a155337.lighttea.Object;

import java.io.Serializable;
import java.util.Random;
import java.util.Date;

import static com.a155337.lighttea.Activity.MainActivity.memberList;

public class PersonalItem implements Serializable {
    private float personalTotal;
    private String name;
    private String ID;

    public PersonalItem(String name, String personalTotal){
        this.personalTotal = Float.valueOf(personalTotal);
        this.name = name;
        this.ID = new Date(System.currentTimeMillis()).toString() + String.valueOf(new Random().nextInt(10000));
    }

    public String getName(){
        return name;
    }

    public void setName(String newName){
        name = newName;
    }

    public float getPersonalTotal(){
        return personalTotal;
    }

    public void setPersonalTotal(float newPersonalTotal){
        personalTotal = newPersonalTotal;
    }

    public Member getMember(){
        return memberList.findMemberByName(name);
    }

    public String getID(){return ID;}
}
