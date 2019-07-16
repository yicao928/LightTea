package com.a155337.lighttea.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.a155337.lighttea.Object.Bill;
import com.a155337.lighttea.Object.PersonalItem;
import com.a155337.lighttea.R;

import java.util.List;

public class SingleBillAdapter extends ArrayAdapter {
    private final int resourceId;

    public SingleBillAdapter(Context context, int textViewResourceId, List<Bill> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bill bill = (Bill) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView date = (TextView) view.findViewById(R.id.dateTextView);
        TextView total = (TextView) view.findViewById(R.id.totalTextView);
        TextView payer = (TextView)view.findViewById(R.id.payerTextView);
        date.setText(bill.getDate());
        total.setText(bill.getTotal());
        payer.setText(bill.getPaidPerson());
        return view;
    }
}
