package com.resq.resqserviceapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.IdRes;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ComplaintActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    ArrayList<String> types;
    EditText serialNumber,model, description;
    RadioGroup radioGroup;
    Intent intent;
    ConstraintLayout constraintLayout;
    String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss mmHHddMM").format(new Date());

    String email = "resqcustomercare@gmail.com";
    String subject = "";
    String message = "";

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean isValid(View view){
        boolean valid = true;
        if(serialNumber.getText().toString().matches("")){
            serialNumber.setError("Required Field");
            valid = false;
        }else{
            serialNumber.setError(null);
        }
        if(model.getText().toString().matches("")){
            model.setError("Required Field");
            valid = false;
        }else{
            model.setError(null);
        }
        if(radioGroup.getCheckedRadioButtonId()==-1){
            Snackbar snackbar = Snackbar.make(view, "Please select warranty type", Snackbar.LENGTH_SHORT);
            snackbar.show();
            valid = false;
        }
        return valid;
    }

    private void sendEmail() {
        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);
        //Executing sendmail to send email
        sm.execute();
    }

    public void registerComplaint(View view){

        if(isNetworkAvailable()) {

            if(isValid(view)) {

                MainActivity.complaintDetails.setSerialNumber(serialNumber.getText().toString());
                MainActivity.complaintDetails.setModel(model.getText().toString());
                MainActivity.complaintDetails.setDescription(description.getText().toString());
                MainActivity.complaintDetails.setDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                MainActivity.complaintDetails.setTime(new SimpleDateFormat("HH:mm").format(new Date()));

                String idType = "";
                if(MainActivity.complaintDetails.getType().equals("UPS")){
                    idType = "UPS";
                }else if(MainActivity.complaintDetails.getType().equals("Stabilizer")){
                    idType = "STB";
                }else if(MainActivity.complaintDetails.getType().equals("Inverter")){
                    idType = "INV";
                }else if(MainActivity.complaintDetails.getType().equals("Transformer")){
                    idType = "TRF";
                }
                subject = "ID: "+ idType + new SimpleDateFormat("ssMMmmdd").format(new Date());
                MainActivity.complaintDetails.setComplaintID(subject);

                message = MainActivity.complaintDetails.getFinalString();

                /*send mail*/
                sendEmail();
                /*send mail*/

                try{
                    SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("ComplaintData", MODE_PRIVATE,null);
                    sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS ComplaintDetails (ID VARCHAR, date VARCHAR, type VARCHAR, model VARCHAR)");
                    sqLiteDatabase.execSQL("INSERT INTO ComplaintDetails (ID, date, model, type) VALUES ('"+subject+"','"+MainActivity.complaintDetails.getDate()+"','"+MainActivity.complaintDetails.getType()+"', '"+MainActivity.complaintDetails.getModel()+"')");

                }catch (Exception e){
                    e.printStackTrace();
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("registerComplaint", true);
                startActivity(intent);
            }else{

            }
        }else {
            Snackbar snackbar = Snackbar.make(view, "Please check your internet connection", Snackbar.LENGTH_SHORT);
            snackbar.show();
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
        setContentView(R.layout.activity_complaint);

        constraintLayout = (ConstraintLayout) findViewById(R.id.complaintBackground);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (view.getId() == R.id.complaintBackground) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Complaint form");

        serialNumber = (EditText)findViewById(R.id.serial);
        model = (EditText)findViewById(R.id.model);
        description = (EditText)findViewById(R.id.description);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        spinner = (Spinner)findViewById(R.id.spinner);
        types = new ArrayList<String>();
        types.add("UPS");
        types.add("Stabilizer");
        types.add("Inverter");
        types.add("Transformer");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.warranty:
                        MainActivity.complaintDetails.setWarrantyType("In Warranty");
                        break;
                    case R.id.nowarranty:
                        MainActivity.complaintDetails.setWarrantyType("Not In Warranty");
                        break;
                    case R.id.amc:
                        MainActivity.complaintDetails.setWarrantyType("AMC - Annual Maintenance Contract");
                        break;
                    default:break;
                }
            }
        });

        intent=getIntent();
        switch (intent.getStringExtra("product")){
            case "airCooledStabilizer" :
                spinner.setSelection(1);
                model.setText("Air cooled");
                break;
            case "oilCooledStabilizer" :
                spinner.setSelection(1);
                model.setText("Oil cooled");
                break;
            case "ultraIsolation" :
                spinner.setSelection(1);
                model.setText("Ultra Isolation");
                break;
            case "ups9145Complain" :
                spinner.setSelection(0);
                model.setText("Eaton 9145");
                break;
            case "ups9395Complain" :
                spinner.setSelection(0);
                model.setText("Eaton 9395");
                break;
            case "ups9390Complain" :
                spinner.setSelection(0);
                model.setText("Eaton 9390");
                break;
            case "ups93EComplain" :
                spinner.setSelection(0);
                model.setText("Eaton 93E");
                break;
            default:
                spinner.setSelection(0);
        }

        description.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    registerComplaint(view);
                }
                return false;
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        MainActivity.complaintDetails.setType(adapterView.getItemAtPosition(i).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
