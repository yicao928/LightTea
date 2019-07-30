package com.a155337.lighttea.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.a155337.lighttea.Adapter.SingleBillAdapter;
import com.a155337.lighttea.Adapter.SingleMemberAdapter;
import com.a155337.lighttea.R;

import static com.a155337.lighttea.Activity.MainActivity.memberList;

public class ViewMembers extends AppCompatActivity {
    private ListView memberListView;
    private SingleMemberAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_members);
        memberListView = findViewById(R.id.memberListView);
        adapter = new SingleMemberAdapter(ViewMembers.this, R.layout.single_member_when_view, memberList.getAllMembers());
        memberListView.setAdapter(adapter);
    }
}
