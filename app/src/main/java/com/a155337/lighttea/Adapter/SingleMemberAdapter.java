package com.a155337.lighttea.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.a155337.lighttea.Object.Member;
import com.a155337.lighttea.R;

import java.util.List;

public class SingleMemberAdapter extends ArrayAdapter {
    private final int resourceId;

    public SingleMemberAdapter(Context context, int textViewResourceId, List<Member> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Member member = (Member) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView nameTextView = (TextView)view.findViewById(R.id.nameTextView);
        TextView balanceTextView = (TextView)view.findViewById(R.id.balanceTextView);
        nameTextView.setText(member.getName());
        balanceTextView.setText(String.valueOf(member.getBalance()));
        return view;
    }
}
