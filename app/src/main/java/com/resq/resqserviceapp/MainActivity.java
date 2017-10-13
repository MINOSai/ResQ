package com.resq.resqserviceapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    static boolean loggedin;
    Intent intent;
    static ComplaintDetails complaintDetails = new ComplaintDetails();
    SharedPreferences sharedPreferences;

    public void call(){
        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel:91-984-109-1568"));
        try {
            startActivity(phoneIntent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void cardButtonClickListener(View view) {
        boolean isComplain = false, isWeb = false;
        intent = new Intent(getApplicationContext(), ComplaintActivity.class);
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        switch (view.getId()) {
// stabilizerAirCooled
            case R.id.stabilizerAirCooledComplain:
                intent.putExtra("product", "airCooledStabilizer");
                isComplain = true;
                break;
            case R.id.stabilizerAirCooledView:
                customTabsIntent.launchUrl(this, Uri.parse("http://resqtechnologies.in/Stabilizers.html"));
                break;
//stabilizerOilCooled
            case R.id.stabilizerOilCooledComplain:
                intent.putExtra("product", "oilCooledStabilizer");
                isComplain = true;
                break;
            case R.id.stabilizerOilCooledView:
                customTabsIntent.launchUrl(this, Uri.parse("http://resqtechnologies.in/Stabilizers.html"));
                break;
//stabilizerUltraisolation
            case R.id.stabilizerUltraisolationComplain:
                intent.putExtra("product", "ultraIsolation");
                isComplain = true;
                break;
            case R.id.stabilizerUltraisolationView:
                customTabsIntent.launchUrl(this, Uri.parse("http://resqtechnologies.in/Stabilizers.html"));
                break;
//ups9145
            case R.id.ups9145Complain:
                intent.putExtra("product", "ups9145Complain");
                isComplain = true;
                break;
            case R.id.ups9145View:
                customTabsIntent.launchUrl(this, Uri.parse("http://resqtechnologies.in/Eaton9145UPS.html"));
                break;
//ups9395
            case R.id.ups9395Complain:
                intent.putExtra("product", "ups9395Complain");
                isComplain = true;
                break;
            case R.id.ups9395View:
                customTabsIntent.launchUrl(this, Uri.parse("http://resqtechnologies.in/Eaton9395UPS.html"));
                break;
//ups9390
            case R.id.ups9390Complain:
                intent.putExtra("product", "ups9390Complain");
                isComplain = true;
                break;
            case R.id.ups9390View:
                customTabsIntent.launchUrl(this, Uri.parse("http://resqtechnologies.in/Eaton9390.html"));
                break;
//ups93E
            case R.id.ups93EComplain:
                intent.putExtra("product", "ups93EComplain");
                isComplain = true;
                break;
            case R.id.ups93EView:
                customTabsIntent.launchUrl(this, Uri.parse("http://resqtechnologies.in/Eaton9E80to200kvaUps.html"));
                break;
        }
//        intent.putExtra("product","airCooledStabilizer");
        if (isComplain) {
            startActivity(intent);
        }
    }

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        sharedPreferences = getApplicationContext().getSharedPreferences("com.minosai.resqserviceapp", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("customerDetails", "");
        if (json != "") {
            complaintDetails.customerDetails = gson.fromJson(json, complaintDetails.customerDetails.getClass());
        }

        intent = getIntent();
        if (intent.getBooleanExtra("registerComplaint", false)) {
            new AlertDialog.Builder(this)
                    .setTitle("Registered")
                    .setMessage("Your complaint is registered successfully.\n\nComplaint "
                            + complaintDetails.getComplaintID() +
                            ".\n\nOur service team will contact you as soon as possible.")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setNegativeButton(null, null)
                    .show();
        }

        loggedin = sharedPreferences.getBoolean("loggedInStatus", false);

        if (!loggedin) {
            intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        } else {
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.accountedit) {
            intent = new Intent(getApplicationContext(), RegisterActivity.class);
            intent.putExtra("editDetails", true);
            startActivity(intent);
            return true;
        } else if (id == R.id.newComplaintButton) {
            intent = new Intent(getApplicationContext(), ComplaintActivity.class);
            intent.putExtra("product", "");
            startActivity(intent);
            return true;
        } else if (id == R.id.complaintHistory) {
            intent = new Intent(getApplicationContext(), ComplaintList.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.callMenuOption) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE}, 1);
            }else{
                call();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED) {
                call();
            }
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position){
            switch (position){
                case 0:
                    Tab1Ups tab1 = new Tab1Ups();
                    return tab1;
                case 1:
                    Tab2Stabilizer tab2 = new Tab2Stabilizer();
                    return tab2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "UPS";
                case 1:
                    return "stabilizer";
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
