package com.a155337.lighttea.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.a155337.lighttea.Object.Member;
import com.a155337.lighttea.R;

public class AddMember extends AppCompatActivity {
    private EditText name;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        name = findViewById(R.id.nameEditText);
        add = findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Member newMember = new Member(name.getText().toString());
                Intent result = new Intent(AddMember.this, MainActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("new member", newMember);
                result.putExtras(bundle);
                setResult(2, result);
                finish();
            }
        });
    }
}
