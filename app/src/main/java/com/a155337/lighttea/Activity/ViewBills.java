package com.a155337.lighttea.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.a155337.lighttea.Adapter.SingleBillAdapter;
import com.a155337.lighttea.Helper.Constant;
import com.a155337.lighttea.Object.Bill;
import com.a155337.lighttea.Object.BillList;
import com.a155337.lighttea.R;

import java.util.List;

import static com.a155337.lighttea.Activity.MainActivity.billList;

public class ViewBills extends AppCompatActivity {
    private ListView allBillsList;
    private SingleBillAdapter adapter;
    private int positionToEdit;
    private int returnResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bills);
        returnResult = Constant.NO_CHANGE;

        allBillsList = findViewById(R.id.allBillsList);
        adapter = new SingleBillAdapter(ViewBills.this, R.layout.single_bill_when_view, billList.getAllBills());
        allBillsList = findViewById(R.id.allBillsList);
        allBillsList.setAdapter(adapter);
        allBillsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionToEdit = position;
                Bill onClickeBill = billList.getBill(position);
                Intent intent = new Intent(ViewBills.this, EditSingleBill.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.BILL_TO_EDIT, onClickeBill);
                intent.putExtras(bundle);
                startActivityForResult(intent, Constant.NEW_EDIT_BILL);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Bill newBill = (Bill)data.getExtras().getSerializable(Constant.NEW_BILL);
        billList.replace(positionToEdit, newBill);

        adapter = new SingleBillAdapter(ViewBills.this, R.layout.single_bill_when_view, billList.getAllBills());
        allBillsList.setAdapter(adapter);
        MainActivity.updateTotal();
        returnResult = Constant.UPDATE_BILL_LIST;
    }

    @Override
    public void onBackPressed() {
        setResult(returnResult);
        finish();
    }
}
