package com.a155337.lighttea.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.a155337.lighttea.Object.Bill;
import com.a155337.lighttea.Object.Member;
import com.a155337.lighttea.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView dateTextView;
    private TextView totalSpending;
    private Button addBillButton;
    private Button settleBillsButton;

    private static List<Member> memberlist;
    public static List<Bill> billList;
    private float total;
    private String firstDate;

    SharedPreferences settings;
    private static final String FIRST_RUN = "first run";
    private static final String TOTAL_SPEDNING = "total spending";
    private static final String FIRST_DATE = "first date";
    private static final int REQUEST_NEW_BILL = 1;
    private static final int REQUEST_NEW_Member = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = getSharedPreferences("com.a155337.tryfunctions", MODE_PRIVATE);
        if(firstRun()){
            firstTimeInit();
        }
        else{
            initViewsAndData();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // View all bill!!!!!
            //TODO
            Intent intent = new Intent(MainActivity.this, ViewBills.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            //Add new member!!!!!
            //TODO
            Intent intent = new Intent(MainActivity.this, AddMember.class);
            startActivityForResult(intent, REQUEST_NEW_Member);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static Member findMemberByName(String name){
        for( Member i: memberlist){
            if(i.getName().equals(name))
                return i;
        }
        return null;
    }

    public static String[] getNameList(){
        String[] nameList = new String[memberlist.size()];
        for(int i = 0; i < memberlist.size(); i++){
            nameList[i] = memberlist.get(i).getName();
        }
        return nameList;
    }

    private void initViewsAndData(){
        dateTextView = findViewById(R.id.dateTextView);
        totalSpending = findViewById(R.id.totalSpending);
        addBillButton = findViewById(R.id.addBillButton);
        addBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(memberlist.size() != 0){
                    Intent intent = new Intent(MainActivity.this, AddBillActivity.class);
                    startActivityForResult(intent, REQUEST_NEW_BILL);
                }
                else{
                    showMessage("Please add a member first");
                }
            }
        });
        settleBillsButton = findViewById(R.id.settleBillsButton);
        settleBillsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if(firstRun())
            firstTimeInit();
        dateTextView.setText(settings.getString(FIRST_DATE, "00-00-00"));
        totalSpending.setText(String.valueOf(settings.getFloat(TOTAL_SPEDNING, 0.0f)));
        memberlist = new ArrayList<>();//Todo
        billList = new ArrayList<>();//Todo
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Bundle bundle = data.getExtras();
        switch (resultCode){
            case REQUEST_NEW_BILL:
                Bill newBill = (Bill)bundle.getSerializable("new bill");
                billList.add(newBill);
                total = total + newBill.getFloatTotal();
                settings.edit().putFloat(TOTAL_SPEDNING, total).commit();
                totalSpending.setText(String.valueOf(total));
                showMessage("Success");
                break;
            case REQUEST_NEW_Member:
                Member newMember = (Member)bundle.getSerializable("new member");
                memberlist.add(newMember);
                showMessage("Success");
                break;
        }
    }

    private void firstTimeInit(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = simpleDateFormat.format(date);
        currentDate = currentDate.substring(2);
        total = 0.0f;
        firstDate = currentDate;
        settings.edit().putString(FIRST_DATE, firstDate).commit();
        settings.edit().putFloat(TOTAL_SPEDNING, total).commit();
        memberlist = new ArrayList<>();
        billList = new ArrayList<>();
    }

    private boolean firstRun() {
        if(settings.getBoolean(FIRST_RUN, true)){
            settings.edit().putBoolean(FIRST_RUN, false).commit();
            return true;
        }
        return false;
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
