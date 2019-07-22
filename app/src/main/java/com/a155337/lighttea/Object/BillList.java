package com.a155337.lighttea.Object;

import java.util.ArrayList;

public class BillList {
    private ArrayList<Bill> allBills;
    private float total;

    public BillList(){
        allBills = new ArrayList<>();
        total = 0.0f;
    }

    public void add(Bill newBill){
        allBills.add(newBill);
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

    public void replace(int position, Bill newBill){
        Bill oldBill = allBills.get(position);
        oldBill.setTotal(newBill.getFloatTotal());
        oldBill.setPaidPerson(newBill.getPaidPersonMember());
    }
}
