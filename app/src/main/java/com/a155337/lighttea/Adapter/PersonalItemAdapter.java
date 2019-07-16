package com.a155337.lighttea.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.a155337.lighttea.Object.PersonalItem;
import com.a155337.lighttea.R;

import java.util.List;

public class PersonalItemAdapter extends ArrayAdapter {
    private final int resourceId;

    public PersonalItemAdapter(Context context, int textViewResourceId, List<PersonalItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PersonalItem personalItem = (PersonalItem) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView price = (TextView) view.findViewById(R.id.price);
        name.setText(personalItem.getName());
        price.setText(String.valueOf(personalItem.getPrice()));
        return view;
    }

}
