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

import com.a155337.lighttea.Helper.Constant;
import com.a155337.lighttea.Helper.Helper;
import com.a155337.lighttea.Object.Bill;
import com.a155337.lighttea.Object.BillList;
import com.a155337.lighttea.Object.Member;
import com.a155337.lighttea.Object.MemberList;
import com.a155337.lighttea.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView dateTextView;
    private static TextView totalSpending;
    private Button addBillButton;
    private Button settleBillsButton;

    public static BillList billList;
    public static MemberList memberList;
    private String firstDate;


    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = getSharedPreferences("com.a155337.tryfunctions", MODE_PRIVATE);
        if(firstRun()){
            firstTimeInit();
        }
        initViewsAndData();
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
            startActivityForResult(intent, Constant.UPDATE_BILL_LIST);
        } else if (id == R.id.nav_gallery) {
            //Add new member!!!!!
            //TODO
            Intent intent = new Intent(MainActivity.this, AddMember.class);
            startActivityForResult(intent, Constant.REQUEST_NEW_Member);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initViewsAndData(){
        dateTextView = findViewById(R.id.dateTextView);
        totalSpending = findViewById(R.id.totalSpending);
        addBillButton = findViewById(R.id.editBillButton);
        addBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(memberList.size() != 0){
                    Intent intent = new Intent(MainActivity.this, AddBillActivity.class);
                    startActivityForResult(intent, Constant.REQUEST_NEW_BILL);
                }
                else{
                    Helper.showMessage("Please add a member first", MainActivity.this);
                }
            }
        });
        settleBillsButton = findViewById(R.id.settleBillsButton);
        settleBillsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });
        if(firstRun())
            firstTimeInit();
        dateTextView.setText(settings.getString(Constant.FIRST_DATE, "00-00-00"));
        totalSpending.setText(String.valueOf(settings.getFloat(Constant.TOTAL_SPEDNING, 0.0f)));
        memberList = new MemberList();//Todo
        billList = new BillList();//Todo
        try{
            FileInputStream fis = openFileInput("MemberList.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            memberList = (MemberList) ois.readObject();
            fis = openFileInput("BillList.txt");
            ois = new ObjectInputStream(fis);
            billList = (BillList) ois.readObject();
            ois.close();
        }catch (Exception e){
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Bundle bundle;
        switch (resultCode){
            case Constant.REQUEST_NEW_BILL:
                if(data == null)
                    return;
                bundle = data.getExtras();
                Bill newBill = (Bill)bundle.getSerializable("new bill");
                newBill.assignBalance();
                billList.add(newBill);
                billList.increaseTotalBy(newBill.getFloatTotal());
                totalSpending.setText(String.valueOf(billList.getTotal()));
                updateBillList();
                Helper.showMessage("Success", MainActivity.this);
                break;
            case Constant.REQUEST_NEW_Member:
                if(data == null)
                    return;
                bundle = data.getExtras();
                Member newMember = (Member)bundle.getSerializable("new member");
                memberList.add(newMember);
                updateMemberList();
                Helper.showMessage("Success", MainActivity.this);
                break;
            case Constant.UPDATE_BILL_LIST:
                updateBillList();
                break;

        }
    }

    private void firstTimeInit(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = simpleDateFormat.format(date);
        currentDate = currentDate.substring(2);
        firstDate = currentDate;
        settings.edit().putString(Constant.FIRST_DATE, firstDate).commit();
        memberList = new MemberList();
        billList = new BillList();
        updateMemberList();
        updateBillList();
    }

    private boolean firstRun() {
        if(settings.getBoolean(Constant.FIRST_RUN, true)){
            settings.edit().putBoolean(Constant.FIRST_RUN, false).commit();
            return true;
        }
        return false;
    }

    public static void updateTotal(){
        float newTotal = 0;
        for(Bill i: billList.getAllBills()){
            newTotal = newTotal + i.getFloatTotal();
        }
        totalSpending.setText(String.valueOf(newTotal));
    }

    public void updateMemberList(){
        try{
            FileOutputStream fos = openFileOutput("MemberList.txt", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(memberList);
            oos.close();
        }catch (Exception e){
            Helper.showMessage("Something Wrong", this);
        }
    }

    public void updateBillList(){
        try{
            FileOutputStream fos = openFileOutput("BillList.txt", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(billList);
            oos.close();
        }catch (Exception e){
            Helper.showMessage("Something Wrong", this);
        }
    }
}
