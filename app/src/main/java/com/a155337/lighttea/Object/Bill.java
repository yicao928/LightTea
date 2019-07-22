package com.a155337.lighttea.Object;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.a155337.lighttea.Activity.MainActivity.memberList;

public class Bill implements Serializable {
    public Date date;
    private float total;
    private Member paidPerson;
    private ArrayList<com.a155337.lighttea.Object.PersonalItem> personalItems;

    public Bill(Member paidPerson, float total, Date date, ArrayList<PersonalItem> personalItems){
        this.paidPerson = paidPerson;
        this.total = total;
        this.date = date;
        this.personalItems = personalItems;
    }

    public String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String shorterDate = simpleDateFormat.format(date);
        shorterDate = shorterDate.substring(2, shorterDate.length());
        return shorterDate;
    }

    public String getPaidPerson(){
        return paidPerson.getName();
    }

    public Member getPaidPersonMember(){
        return paidPerson;
    }

    public String getTotal(){
        return String.valueOf(total);
    }

    public float getFloatTotal(){
        return total;
    }

    public void assignBalance(){
        paidPerson.increaseBalance(total);
        float totalWithoutPersonalItem = total;
        for(PersonalItem i: personalItems){
            totalWithoutPersonalItem = totalWithoutPersonalItem - i.getPersonalTotal();
            i.getMember().decreaseBalance(i.getPersonalTotal());
        }
        memberList.decreaseBalanceForAll(totalWithoutPersonalItem);
    }

    public ArrayList<PersonalItem> getPersonalItems(){
        return personalItems;
    }

    public void setTotal(float newttotal){
        total = newttotal;
    }

    public void setPaidPerson(Member newPaidPerson){
        paidPerson = newPaidPerson;
    }
}
