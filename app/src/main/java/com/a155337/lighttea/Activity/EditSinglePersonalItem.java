package com.a155337.lighttea.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.a155337.lighttea.Helper.Constant;
import com.a155337.lighttea.Object.PersonalItem;
import com.a155337.lighttea.R;

import static com.a155337.lighttea.Activity.MainActivity.memberList;
import static com.a155337.lighttea.Activity.MainActivity.personalItemList;

public class EditSinglePersonalItem extends AppCompatActivity {
    private Spinner nameSpinner;
    private Button editPersonalButton;
    private Button deletePersonalItemButton;
    private EditText amountEditText;

    private String[] nameList;
    String editID;
    PersonalItem personalItemToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_single_personal_item);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        editID = bundle.getString(Constant.PERSONAL_TO_EDIT);
        personalItemToEdit = personalItemList.findPersonalItemByID(editID);
        initViews();
        setOnClickListener();
    }

    private void initViews(){
        nameList = memberList.getNameList();
        ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, nameList);
        nameSpinner = findViewById(R.id.nameSpinner);
        nameSpinner.setAdapter(nameAdapter);
        int k= nameAdapter.getCount();
        for(int i=0;i<k;i++){
            if(personalItemToEdit.getName().equals(nameAdapter.getItem(i).toString())){
                nameSpinner.setSelection(i,true);
                break;
            }
        }

        amountEditText = findViewById(R.id.amountEditText);
        amountEditText.setText(String.valueOf(personalItemToEdit.getPersonalTotal()));

        editPersonalButton = findViewById(R.id.editPersonalButton);
        deletePersonalItemButton = findViewById(R.id.deletePersonalItemButton);
    }

    private void setOnClickListener(){
        editPersonalButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PersonalItem newPersonalItem = new PersonalItem(nameSpinner.getSelectedItem().toString(), amountEditText.getText().toString());
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.PERSONAL_TO_EDIT, newPersonalItem);
                intent.putExtras(bundle);
                setResult(Constant.NEW_EDIT_PERSONAL_ITEM, intent);
                finish();
            }
        });

        deletePersonalItemButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setResult(Constant.DELETE_PERSONAL_ITEM);
                finish();
            }
        });
    }
}
