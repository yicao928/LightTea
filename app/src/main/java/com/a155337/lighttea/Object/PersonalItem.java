package com.a155337.lighttea.Object;

import com.a155337.lighttea.Activity.MainActivity;
import com.a155337.lighttea.Object.Member;

import java.io.Serializable;

import static com.a155337.lighttea.Activity.MainActivity.memberList;

public class PersonalItem implements Serializable {
    private float personalTotal;
    private String name;

    public PersonalItem(String name, String personalTotal){
        this.personalTotal = Float.valueOf(personalTotal);
        this.name = name;
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
}
