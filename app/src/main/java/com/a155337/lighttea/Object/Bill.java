package com.a155337.lighttea.Object;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.a155337.lighttea.Activity.MainActivity.memberList;
import static com.a155337.lighttea.Activity.MainActivity.personalItemList;

public class Bill implements Serializable {
    public Date date;
    private float total;
    private String paidPerson;
    private ArrayList<String> personalItemID;


    public Bill(String paidPerson, float total, Date date, ArrayList<String> personalItemID){
        this.paidPerson = paidPerson;
        this.total = total;
        this.date = date;
        this.personalItemID = personalItemID;
    }

    public void replacePersonItem(String personalItemID, PersonalItem newPersonalItem){
        PersonalItem oldPersonalItem = personalItemList.findPersonalItemByID(personalItemID);
        oldPersonalItem.setName(newPersonalItem.getName());
        oldPersonalItem.setPersonalTotal(newPersonalItem.getPersonalTotal());
    }

    public String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String shorterDate = simpleDateFormat.format(date);
        shorterDate = shorterDate.substring(2, shorterDate.length());
        return shorterDate;
    }

    public String getPaidPerson(){
        return paidPerson;
    }

    public Member getPaidPersonMember(){
        return memberList.findMemberByName(paidPerson);
    }

    public String getTotal(){
        return String.valueOf(total);
    }

    public float getFloatTotal(){
        return total;
    }

    public void assignBalance(){
        memberList.findMemberByName(paidPerson).increaseBalance(total);
        float totalWithoutPersonalItem = total;
        ArrayList<PersonalItem> personalItems = new ArrayList<>();
        for(String i: personalItemID){
            personalItems.add(personalItemList.findPersonalItemByID(i));
        }
        for(PersonalItem i: personalItems){
            totalWithoutPersonalItem = totalWithoutPersonalItem - i.getPersonalTotal();
            i.getMember().decreaseBalance(i.getPersonalTotal());
        }
        memberList.decreaseBalanceForAll(totalWithoutPersonalItem);
    }

    public ArrayList<PersonalItem> getPersonalItems(){
        ArrayList<PersonalItem> result = new ArrayList<>();
        for(String i: personalItemID){
            result.add(personalItemList.findPersonalItemByID(i));
        }
        return result;
    }

    public ArrayList<String> getPersonalItemID(){return personalItemID;}

    public void setTotal(float newttotal){
        total = newttotal;
    }

    public void setPaidPerson(String newPaidPerson){
        paidPerson = newPaidPerson;
    }

    public void setPersonalItemID(ArrayList<String> newPersonalItemID){personalItemID = newPersonalItemID;}

    public void deletePersonalItem(String id){
        personalItemID.remove(id);
    }
}
