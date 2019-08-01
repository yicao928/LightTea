package com.a155337.lighttea.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

import static com.a155337.lighttea.Activity.MainActivity.billList;
import static com.a155337.lighttea.Activity.MainActivity.memberList;
import static com.a155337.lighttea.Activity.MainActivity.personalItemList;

//reutrn  a new bill to replace the old bill
public class EditSingleBill extends AppCompatActivity {
    private Button editBillButton;
    private EditText totalEditText;
    private Spinner paidPersonSpinner;
    private ListView personalItemListView;
    private PersonalItemAdapter adapter;

    private String[] nameList;
    private Bill newBill;
    private float personalItemTotal;
    private ArrayList<PersonalItem> personalItemListThis;
    private ArrayList<String> personalItemID;
    private String editID;

    private Bill billToEdit;
    int positionToEdit;

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
        editID = null;
        personalItemID = billToEdit.getPersonalItemID();
        personalItemListThis = new ArrayList<>();
        for(String i: personalItemID){
            personalItemListThis.add(personalItemList.findPersonalItemByID(i));
        }
        adapter = new PersonalItemAdapter(EditSingleBill.this, R.layout.person_item, personalItemListThis);
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

    private void setOnClickerListener(){
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

        personalItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionToEdit = position;
                PersonalItem personalItemToEdit = billToEdit.getPersonalItems().get(position);
                editID = personalItemToEdit.getID();
                Intent intent = new Intent(EditSingleBill.this, EditSinglePersonalItem.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.PERSONAL_TO_EDIT, personalItemToEdit);
                intent.putExtras(bundle);
                startActivityForResult(intent, Constant.NEW_EDIT_PERSONAL_ITEM);
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
            String paidPerson = paidPersonSpinner.getSelectedItem().toString();
            Date date = billToEdit.date;
            newBill = new Bill(paidPerson, total, date, personalItemID);
            return true;
        }
        else{
            Helper.showMessage("Please enter valid number", EditSingleBill.this);
            return false;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Bundle bundle = data.getExtras();
        PersonalItem newPersonalItem = (PersonalItem)bundle.getSerializable(Constant.PERSONAL_TO_EDIT);
        billToEdit.replacePersonItem(editID, newPersonalItem);
        personalItemListThis = billToEdit.getPersonalItems();

        adapter = new PersonalItemAdapter(EditSingleBill.this, R.layout.person_item, personalItemListThis);
        personalItemListView.setAdapter(adapter);
    }
}
