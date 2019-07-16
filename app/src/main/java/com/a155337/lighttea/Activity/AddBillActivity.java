package com.a155337.lighttea.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.a155337.lighttea.Object.Bill;
import com.a155337.lighttea.Object.Member;
import com.a155337.lighttea.Object.PersonalItem;
import com.a155337.lighttea.Adapter.PersonalItemAdapter;
import com.a155337.lighttea.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddBillActivity extends AppCompatActivity {
    private ArrayList<PersonalItem> personalItemList;
    private Button addPersonalButton;
    private Button addBillButton;
    private EditText totalEditText;
    private EditText amountEditText;
    private Spinner paidPersonSpinner;
    private Spinner nameSpinner;
    private ListView personalItemListView;
    private PersonalItemAdapter adapter;

    private String[] nameList;
    private Bill newBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        initViews();

    }

    private void initViews(){
        personalItemList = new ArrayList<PersonalItem>();
        adapter = new PersonalItemAdapter(AddBillActivity.this, R.layout.person_item, personalItemList);
        personalItemListView = findViewById(R.id.personalItemListView);
        personalItemListView.setAdapter(adapter);

        nameList = MainActivity.getNameList();
        ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, nameList);
        paidPersonSpinner = findViewById(R.id.paidPersonSpinner);
        paidPersonSpinner.setAdapter(nameAdapter);
        nameSpinner = findViewById(R.id.nameSpinner);
        nameSpinner.setAdapter(nameAdapter);

        totalEditText = findViewById(R.id.totalEditText);
        amountEditText = findViewById(R.id.amountEditText);

        addPersonalButton = findViewById(R.id.addPersonalButton);
        addPersonalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFloat(amountEditText.getText().toString())){
                    PersonalItem newPersonalItem = new PersonalItem(nameSpinner.getSelectedItem().toString(), amountEditText.getText().toString());
                    personalItemList.add(newPersonalItem);
                    personalItemListView.setAdapter(adapter);
                }
                else{
                    showMessage("Please enter valid number");
                }
            }
        });

        addBillButton = findViewById(R.id.addBillButton);
        addBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(createBill()){
                    Intent result = new Intent(AddBillActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("new bill", newBill);
                    result.putExtras(bundle);
                    setResult(1, result);
                    finish();
                }
            }
        });
    }

    private boolean isFloat(String s){
        if(s == null || s.equals(""))
            return false;
        for(int i = 0; i < s.length(); i++){
            int decimalPoint = 0;
            if(!Character.isDigit(s.charAt(i)) && s.charAt(i) != '.'){
                return false;
            }
            else if(s.charAt(i) == '.'){
                decimalPoint++;
                if(decimalPoint > 1)
                    return false;
            }
        }
        return true;
    }

    private boolean createBill(){
        if(isFloat(totalEditText.getText().toString())){
            float total = Float.valueOf(totalEditText.getText().toString());
            Member paidPerson = MainActivity.findMemberByName(paidPersonSpinner.getSelectedItem().toString());
            Date date = new Date(System.currentTimeMillis());
            newBill = new Bill(paidPerson, total, date, personalItemList);
            return true;
        }
        else{
            showMessage("Please enter valid number");
            return false;
        }
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
