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

import com.a155337.lighttea.Adapter.PersonalItemAdapter;
import com.a155337.lighttea.Helper.Constant;
import com.a155337.lighttea.Helper.Helper;
import com.a155337.lighttea.Object.Bill;
import com.a155337.lighttea.Object.Member;
import com.a155337.lighttea.Object.PersonalItem;
import com.a155337.lighttea.R;

import java.util.ArrayList;
import java.util.Date;

import static com.a155337.lighttea.Activity.MainActivity.memberList;

public class EditSingleBill extends AppCompatActivity {
    private ArrayList<PersonalItem> personalItemList;
    private Button editBillButton;
    private EditText totalEditText;
    private Spinner paidPersonSpinner;
    private ListView personalItemListView;
    private PersonalItemAdapter adapter;

    private String[] nameList;
    private Bill newBill;
    private float personalItemTotal;

    private Bill billToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_single_bill);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        billToEdit = (Bill) bundle.getSerializable(Constant.BILL_TO_EDIT);
        initViews();
        setOnClickerListener();
    }

    private void initViews(){
        personalItemList = billToEdit.getPersonalItems();
        adapter = new PersonalItemAdapter(EditSingleBill.this, R.layout.person_item, personalItemList);
        personalItemListView = findViewById(R.id.personalItemListView);
        personalItemListView.setAdapter(adapter);

        nameList = memberList.getNameList();
        ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, nameList);
        paidPersonSpinner = findViewById(R.id.paidPersonSpinner);
        paidPersonSpinner.setAdapter(nameAdapter);

        totalEditText = findViewById(R.id.totalEditText);
        totalEditText.setText(String.valueOf(billToEdit.getFloatTotal()));

        editBillButton = findViewById(R.id.editBillButton);

    }

    public void setOnClickerListener(){
        editBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValid()){
                    Intent result = new Intent(EditSingleBill.this, ViewBills.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.NEW_BILL, newBill);
                    result.putExtras(bundle);
                    setResult(Constant.NEW_EDIT_BILL, result);
                    finish();
                }
            }
        });
    }

    public boolean checkValid(){
        if(Helper.isFloat(totalEditText.getText().toString())){
            float total = Float.valueOf(totalEditText.getText().toString());
            if(total < personalItemTotal){
                Helper.showMessage("Person item total is greater than bill total", EditSingleBill.this);
                return false;
            }
            Member paidPerson = memberList.findMemberByName(paidPersonSpinner.getSelectedItem().toString());
            Date date = billToEdit.date;
            newBill = new Bill(paidPerson, total, date, personalItemList);
            return true;
        }
        else{
            Helper.showMessage("Please enter valid number", EditSingleBill.this);
            return false;
        }
    }
}
