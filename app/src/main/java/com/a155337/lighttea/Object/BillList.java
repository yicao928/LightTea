package com.a155337.lighttea.Object;

import java.io.Serializable;
import java.util.ArrayList;

public class BillList implements Serializable {
    private ArrayList<Bill> allBills;
    private float total;

    public BillList(){
        allBills = new ArrayList<>();
        total = 0.0f;
    }

    public void add(Bill newBill){
        allBills.add(newBill);
    }

    public void remove(int position){
        allBills.remove(position);
    }

    public ArrayList<Bill> getAllBills(){
        return allBills;
    }

    public void increaseTotalBy(float newBillTotal){
        total = total + newBillTotal;
    }

    public float getTotal(){
        return total;
    }

    public Bill getBill(int index){
        return allBills.get(index);
    }

    public void calculateBillListTotal(){
        total = 0.0f;
        for(Bill i: allBills){
            total = total + i.getFloatTotal();
        }
    }

    public void replace(int position, Bill newBill){
        Bill oldBill = allBills.get(position);
        oldBill.setTotal(newBill.getFloatTotal());
        oldBill.setPaidPerson(newBill.getPaidPerson());
        oldBill.setPersonalItemID(newBill.getPersonalItemID());
    }

    public void assignBalanceForAll(){
        for(Bill i:allBills){
            i.assignBalance();
        }
    }
}
