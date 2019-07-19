package com.a155337.lighttea.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.a155337.lighttea.Adapter.SingleBillAdapter;
import com.a155337.lighttea.Object.Bill;
import com.a155337.lighttea.R;

import java.util.List;

import static com.a155337.lighttea.Activity.MainActivity.billList;

public class ViewBills extends AppCompatActivity {
    private ListView allBillsList;
    private SingleBillAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bills);

        allBillsList = findViewById(R.id.allBillsList);
        adapter = new SingleBillAdapter(ViewBills.this, R.layout.single_bill_when_view, billList.getAllBills());
        allBillsList = findViewById(R.id.allBillsList);
        allBillsList.setAdapter(adapter);
    }
}
