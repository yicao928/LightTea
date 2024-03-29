package com.a155337.lighttea.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
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
import com.a155337.lighttea.Object.PersonalItemList;
import com.a155337.lighttea.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.joaquimley.faboptions.FabOptions;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView dateTextView;
    private static TextView totalSpending;
    private Button viewBillButton;
    private Button viewMemberButton;
    FloatingActionButton fab;
    private PieChart pieChart;
    FabOptions fabOptions;

    public static BillList billList;
    public static MemberList memberList;
    public static PersonalItemList personalItemList;
    private String firstDate;
    ArrayList<Integer> colors;


    private static SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = getSharedPreferences("com.a155337.lighttea", MODE_PRIVATE);
        if(firstRun()){
            firstTimeInit();
        }
        initViewsAndData();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
//            Intent intent = new Intent(MainActivity.this, ViewBills.class);
//            startActivityForResult(intent, Constant.UPDATE_BILL_LIST);
            //TODO
        } else if (id == R.id.nav_gallery) {
//            Intent intent = new Intent(MainActivity.this, AddMember.class);
//            startActivityForResult(intent, Constant.REQUEST_NEW_Member);
            //TODO
        } else if (id == R.id.nav_slideshow) {
//            Intent intent = new Intent(MainActivity.this, ViewMembers.class);
//            startActivity(intent);
            //TODO
        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            billList = new BillList();
            personalItemList = new PersonalItemList();
            updateBillList();
            updateTotal();
            updatePersonalItemList();
            updatePieChart();
            billList.assignBalanceForAll();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initViewsAndData(){
        dateTextView = findViewById(R.id.dateTextView);
        totalSpending = findViewById(R.id.totalSpending);

        viewBillButton = findViewById(R.id.viewBillButton);
        viewBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewBills.class);
                startActivityForResult(intent, Constant.UPDATE_BILL_LIST);
            }
        });

        viewMemberButton = findViewById(R.id.viewMemberButton);
        viewMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewMembers.class);
                startActivity(intent);
            }
        });


        fabOptions = findViewById(R.id.fab_options);
        fabOptions.setButtonsMenu(R.menu.add_menu);
        fabOptions.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()){
                    case R.id.addMember:
                        intent = new Intent(MainActivity.this, AddMember.class);
                        startActivityForResult(intent, Constant.REQUEST_NEW_Member);
                        break;
                    case R.id.addBill:
                        if(memberList.size() != 0){
                            intent = new Intent(MainActivity.this, AddBillActivity.class);
                            startActivityForResult(intent, Constant.REQUEST_NEW_BILL);
                        }
                        else{
                            Helper.showMessage("Please add a member first", MainActivity.this);
                        }
                        break;
                }
            }
        });

        if(firstRun())
            firstTimeInit();

        memberList = new MemberList();
        billList = new BillList();
        personalItemList = new PersonalItemList();
        try{
            FileInputStream fis = openFileInput("MemberList.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            memberList = (MemberList) ois.readObject();
            fis = openFileInput("BillList.txt");
            ois = new ObjectInputStream(fis);
            billList = (BillList) ois.readObject();
            fis = openFileInput("PersonalItemList.txt");
            ois = new ObjectInputStream(fis);
            personalItemList = (PersonalItemList) ois.readObject();
            ois.close();
            billList.assignBalanceForAll();
        }catch (Exception e){
            Helper.showMessage("Init Fail", this);
        }
        dateTextView.setText(settings.getString(Constant.FIRST_DATE, "00-00-00"));
        totalSpending.setText(String.valueOf(billList.getTotal()));
        initPieChart();
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
                billList.add(newBill);
                billList.increaseTotalBy(newBill.getFloatTotal());
                totalSpending.setText(String.valueOf(billList.getTotal()));
                updateBillList();
                updatePersonalItemList();
                memberList.clearBalance();
                billList.assignBalanceForAll();
                updatePieChart();
                Helper.showMessage("Success", MainActivity.this);
                break;
            case Constant.REQUEST_NEW_Member:
                if(data == null)
                    return;
                bundle = data.getExtras();
                Member newMember = (Member)bundle.getSerializable("new member");
                memberList.add(newMember);
                updateMemberList();
                memberList.clearBalance();
                billList.assignBalanceForAll();
                Helper.showMessage("Success", MainActivity.this);
                break;
            case Constant.UPDATE_BILL_LIST:
                updateBillList();
                updatePersonalItemList();
                updateTotal();
                billList.assignBalanceForAll();
                updatePieChart();
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
        personalItemList = new PersonalItemList();
        updateMemberList();
        updateBillList();
        updatePersonalItemList();
        Helper.showMessage("Welcome", this);
    }

    private boolean firstRun() {
        if(settings.getBoolean(Constant.FIRST_RUN, true)){
            settings.edit().putBoolean(Constant.FIRST_RUN, false).commit();
            return true;
        }
        return false;
    }

    public static void updateTotal(){
        float newTotal = billList.getTotal();
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

    public void updatePersonalItemList(){
        try{
            FileOutputStream fos = openFileOutput("PersonalItemList.txt", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(personalItemList);
            oos.close();
        }catch (Exception e){
            Helper.showMessage("Something Wrong", this);
        }
    }

    private void initPieChart(){
        pieChart = findViewById(R.id.pieChart);
        colors = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        pieChart.setData(getChartData());
    }

    private PieData getChartData(){
        HashMap<String, Float> categoryTotal = billList.getCategoryTotal();
        ArrayList<PieEntry> entries = new ArrayList<>();
        for(Map.Entry<String, Float> i: categoryTotal.entrySet()){
            entries.add(new PieEntry(i.getValue()));
        }
        PieDataSet dataSet = new PieDataSet(entries, "Total");
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        return data;
    }

    private void updatePieChart(){
        HashMap<String, Float> categoryTotal = billList.getCategoryTotal();
        ArrayList<PieEntry> entries = new ArrayList<>();
        for(Map.Entry<String, Float> i: categoryTotal.entrySet()){
            entries.add(new PieEntry(i.getValue()));
        }
        PieDataSet dataSet = new PieDataSet(entries, "Total");
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
    }
}
