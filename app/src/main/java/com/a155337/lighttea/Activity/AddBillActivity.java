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

import com.a155337.lighttea.Helper.Constant;
import com.a155337.lighttea.Helper.Helper;
import com.a155337.lighttea.Object.Bill;
import com.a155337.lighttea.Object.Member;
import com.a155337.lighttea.Object.PersonalItem;
import com.a155337.lighttea.Adapter.PersonalItemAdapter;
import com.a155337.lighttea.R;

import java.util.ArrayList;
import java.util.Date;

import static com.a155337.lighttea.Activity.MainActivity.memberList;

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
    private float personalItemTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        initViews();
        setOnClickerListener();
        personalItemTotal = 0.0f;
    }

    private void initViews(){
        personalItemList = new ArrayList<PersonalItem>();
        adapter = new PersonalItemAdapter(AddBillActivity.this, R.layout.person_item, personalItemList);
        personalItemListView = findViewById(R.id.personalItemListView);
        personalItemListView.setAdapter(adapter);

        nameList = memberList.getNameList();
        ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, nameList);
        paidPersonSpinner = findViewById(R.id.paidPersonSpinner);
        paidPersonSpinner.setAdapter(nameAdapter);
        nameSpinner = findViewById(R.id.nameSpinner);
        nameSpinner.setAdapter(nameAdapter);

        totalEditText = findViewById(R.id.totalEditText);
        amountEditText = findViewById(R.id.amountEditText);

        addPersonalButton = findViewById(R.id.editPersonalButton);
        addBillButton = findViewById(R.id.editBillButton);

    }

    private void setOnClickerListener(){
        addPersonalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Helper.isFloat(amountEditText.getText().toString())){
                    PersonalItem newPersonalItem = new PersonalItem(nameSpinner.getSelectedItem().toString(), amountEditText.getText().toString());
                    personalItemList.add(newPersonalItem);
                    personalItemListView.setAdapter(adapter);
                    personalItemTotal = personalItemTotal + newPersonalItem.getPersonalTotal();
                }
                else{
                    Helper.showMessage("Please enter valid number", AddBillActivity.this);
                }
            }
        });
        addBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(createBill()){
                    Intent result = new Intent(AddBillActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.NEW_BILL, newBill);
                    result.putExtras(bundle);
                    setResult(Constant.REQUEST_NEW_BILL, result);
                    finish();
                }
            }
        });
    }

    private boolean createBill(){
        if(Helper.isFloat(totalEditText.getText().toString())){
            float total = Float.valueOf(totalEditText.getText().toString());
            if(total < personalItemTotal){
                Helper.showMessage("Person item total is greater than bill total", AddBillActivity.this);
                return false;
            }
            Member paidPerson = memberList.findMemberByName(paidPersonSpinner.getSelectedItem().toString());
            Date date = new Date(System.currentTimeMillis());
            newBill = new Bill(paidPerson, total, date, personalItemList);
            return true;
        }
        else{
            Helper.showMessage("Please enter valid number", AddBillActivity.this);
            return false;
        }
    }

}
