package com.resq.resqserviceapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout company, person, email, number, address;
    SharedPreferences sharedPreferences;
    Intent intent;
    Button button;
    TextView textView;
    ConstraintLayout constraintLayout;

    public boolean validDetails(){
        boolean valid=true;
        if(company.getEditText().getText().toString().matches("")){
            valid = false;
            company.setError("Required Field");
        }else{
            company.setError(null);
        }
        if(person.getEditText().getText().toString().matches("")){
            valid = false;
            person.setError("Required Field");
        }else{
            person.setError(null);
        }
        if(email.getEditText().getText().toString().matches("")){
            valid = false;
            email.setError("Required Field");
        }else{
            email.setError(null);
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getEditText().getText().toString()).matches()){
            valid = false;
            email.setError("Invalid");
        }else{
            email.setError(null);
        }
        if(number.getEditText().getText().toString().matches("")){
            valid = false;
            number.setError("Required Field");
        }else{
            number.setError(null);
        }
        if(!android.util.Patterns.PHONE.matcher(number.getEditText().getText().toString()).matches()) {
            valid = false;
            number.setError("Invalid");
        }else{
            number.setError(null);
        }
        if(address.getEditText().getText().toString().matches("")){
            valid = false;
            address.setError("Required Field");
        }else{
            address.setError(null);
        }
        return valid;
    }

    public void saveDetails(){
        MainActivity.complaintDetails.customerDetails.setCompanyName(company.getEditText().getText().toString());
        MainActivity.complaintDetails.customerDetails.setContactPerson(person.getEditText().getText().toString());
        MainActivity.complaintDetails.customerDetails.seteMail(email.getEditText().getText().toString());
        MainActivity.complaintDetails.customerDetails.setContactNumber(Long.parseLong(String.valueOf(number.getEditText().getText())));
        MainActivity.complaintDetails.customerDetails.setAddress(address.getEditText().getText().toString());

//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.minosai.resqserviceapp", Context.MODE_PRIVATE);
        /*sharedPreferences.edit().putString("companyName",company.getEditText().getText().toString()).apply();
        sharedPreferences.edit().putString("contactPerson",person.getEditText().getText().toString()).apply();
        sharedPreferences.edit().putString("eMailID",email.getEditText().getText().toString()).apply();
        sharedPreferences.edit().putString("contactNumber",number.getEditText().getText().toString()).apply();
        sharedPreferences.edit().putString("address",address.getEditText().getText().toString()).apply();*/
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity.complaintDetails.customerDetails);
        prefEditor.putString("customerDetails",json);
        prefEditor.apply();
    }

    public void signup(View view){
        if(validDetails()) {
            saveDetails();
            intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            MainActivity.loggedin = true;
//            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.minosai.resqserviceapp", Context.MODE_PRIVATE);
            sharedPreferences = getApplicationContext().getSharedPreferences("com.minosai.resqserviceapp", Context.MODE_PRIVATE);
            sharedPreferences.edit().putBoolean("loggedInStatus", true).apply();
            if(intent.getBooleanExtra("editDetails",false)){
                Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Contact Details");
        constraintLayout = (ConstraintLayout) findViewById(R.id.registerBackground);

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (view.getId() == R.id.registerBackground) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        sharedPreferences = getApplicationContext().getSharedPreferences("com.minosai.resqserviceapp", Context.MODE_PRIVATE);
        button = (Button)findViewById(R.id.register);
        textView = (TextView)findViewById(R.id.textView);

        company = (TextInputLayout)findViewById(R.id.company);
        person = (TextInputLayout)findViewById(R.id.person);
        email = (TextInputLayout)findViewById(R.id.email);
        number = (TextInputLayout)findViewById(R.id.number);
        address = (TextInputLayout)findViewById(R.id.address);

        intent = getIntent();
        if(intent.getBooleanExtra("editDetails",false)){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            Gson gson = new Gson();
            String json = sharedPreferences.getString("customerDetails","");
            MainActivity.complaintDetails.customerDetails = gson.fromJson(json,MainActivity.complaintDetails.customerDetails.getClass());

            company.getEditText().setText(MainActivity.complaintDetails.customerDetails.getCompanyName());
            person.getEditText().setText(MainActivity.complaintDetails.customerDetails.getContactPerson());
            email.getEditText().setText(MainActivity.complaintDetails.customerDetails.geteMail());
            number.getEditText().setText(String.valueOf(MainActivity.complaintDetails.customerDetails.getContactNumber()));
            address.getEditText().setText(MainActivity.complaintDetails.customerDetails.getAddress());
            button.setText("save details");
            textView.setVisibility(View.GONE);
        }else{
            button.setText("register");
            textView.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("Registration");
        }

        address.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    signup(view);
                }
                return false;
            }
        });


    }

    @Override
    public void onBackPressed() {
        if(!intent.getBooleanExtra("editDetails",false)) {
            moveTaskToBack(true);
        }else{
            super.onBackPressed();
        }
    }
}
