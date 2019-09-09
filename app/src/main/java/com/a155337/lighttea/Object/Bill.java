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
    private String category;


    public Bill(String paidPerson, String category, float total, Date date, ArrayList<String> personalItemID){
        this.paidPerson = paidPerson;
        this.category = category;
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
        ArrayList<String> involvedMember = new ArrayList<>();
        ArrayList<PersonalItem> personalItems = new ArrayList<>();
        for(String i: personalItemID){
            personalItems.add(personalItemList.findPersonalItemByID(i));
            involvedMember.add((personalItemList.findPersonalItemByID(i).getName()));
        }
        float totalWithoutPersonalItem = total;
        for(PersonalItem i:personalItems){
            i.getMember().decreaseBalance(i.getPersonalTotal());
            totalWithoutPersonalItem = totalWithoutPersonalItem - i.getPersonalTotal();
        }
        float average = totalWithoutPersonalItem / involvedMember.size();
        for(String i: involvedMember){
            memberList.findMemberByName(i).decreaseBalance(average);
        }
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

    public String getCategory(){return category;}
}
