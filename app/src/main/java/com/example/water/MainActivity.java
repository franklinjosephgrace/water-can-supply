package com.example.water;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //initialize
    EditText editTextNumOfCans, editTextAmount, editTextName, editTextPhone, editTextDate, editTextAddress, editTextLandmark;
    Button buttonOrder, buttonViewOrder;
    //a list to store all the User from firebase database
    List<Order> orders;
    DatabaseReference databaseReference;
    private String UserName;

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getRefrance for user table
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");

        editTextNumOfCans = (EditText) findViewById(R.id.editTextNumOfCans);
        editTextAmount = (EditText) findViewById(R.id.editTextAmount);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextLandmark = (EditText) findViewById(R.id.editTextLandmark);

        buttonOrder = (Button) findViewById(R.id.buttonOrder);
        buttonViewOrder = (Button) findViewById(R.id.buttonViewOrder);
        //list for store objects of order
        orders = new ArrayList<>();
        //adding an onclicklistener to button
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addUser()
                //the method is defined below
                //this method is actually performing the write operation
                addOrder();
            }
        });

        buttonViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewOrder.class);
                startActivity(intent);
            }
        });

        editTextNumOfCans.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int num1 = Integer.parseInt(editTextNumOfCans.getText().toString());
                    if (num1>0) {
                        int total = num1 * 30;
                        editTextAmount.setText(Integer.toString(total));
                        editTextName.requestFocus();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day+"/"+month+"/"+year;
                        editTextDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

    }

    private void addOrder() {

        //getting the values to save
        String numofcans = editTextNumOfCans.getText().toString().trim();
        String amount = editTextAmount.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String landmark = editTextLandmark.getText().toString().trim();

        if(numofcans.isEmpty()){
            editTextNumOfCans.setError("Number of Can is required!");
            editTextNumOfCans.requestFocus();
            return;
        }

        if(amount.isEmpty()){
            editTextAmount.setError("Amount is required!");
            editTextAmount.requestFocus();
            return;
        }

        if(name.isEmpty()){
            editTextName.setError("Name is required!");
            editTextName.requestFocus();
            return;
        }

        if(phone.isEmpty()){
            editTextPhone.setError("Phone Number is required!");
            editTextPhone.requestFocus();
            return;
        }

        if(phone.length() < 10){
            editTextPhone.setError("Please enter valid Phone number!");
            editTextPhone.requestFocus();
            return;
        }

        if(date.isEmpty()){
            editTextDate.setError("Date is required!");
            editTextDate.requestFocus();
            return;
        }

        if(address.isEmpty()){
            editTextAddress.setError("Address is required!");
            editTextAddress.requestFocus();
            return;
        }

        if(landmark.isEmpty()){
            editTextLandmark.setError("Land-Mark is required!");
            editTextLandmark.requestFocus();
            return;
        }

        //it will create a unique id and we will use it as the Primary Key for our User
        String id = databaseReference.push().getKey();
        //creating an User Object
        Order order = new Order(id, numofcans, amount, name, phone, date, address, landmark);
        //Saving the User
        databaseReference.child(id).setValue(order);
        editTextNumOfCans.setText("");
        editTextAmount.setText("");
        editTextName.setText("");
        editTextPhone.setText("");
        editTextDate.setText("");
        editTextAddress.setText("");
        editTextLandmark.setText("");
        Toast.makeText(this, "Order placed Successfully!", Toast.LENGTH_LONG).show();

//        //checking if the value is provided or not Here, you can Add More Validation as you required
//        if (!TextUtils.isEmpty(numofcans)) {
//            if (!TextUtils.isEmpty(amount)) {
//                if (!TextUtils.isEmpty(name)) {
//                    if (!TextUtils.isEmpty(phone)) {
//                        if (!TextUtils.isEmpty(date)) {
//                            if (!TextUtils.isEmpty(address)) {
//                                if (!TextUtils.isEmpty(landmark)) {
//                                    //it will create a unique id and we will use it as the Primary Key for our User
//                                    String id = databaseReference.push().getKey();
//                                    //creating an User Object
//                                    Order order = new Order(id, numofcans, amount, name, phone, date, address, landmark);
//                                    //Saving the User
//                                    databaseReference.child(id).setValue(order);
//                                    editTextNumOfCans.setText("");
//                                    editTextAmount.setText("");
//                                    editTextName.setText("");
//                                    editTextPhone.setText("");
//                                    editTextDate.setText("");
//                                    editTextAddress.setText("");
//                                    editTextLandmark.setText("");
//                                    Toast.makeText(this, "Order placed Successfully!", Toast.LENGTH_LONG).show();
//                                } else {
//                                    Toast.makeText(this, "Please enter Landmark", Toast.LENGTH_LONG).show();
//                                }
//                            } else {
//                                Toast.makeText(this, "Please enter Address", Toast.LENGTH_LONG).show();
//                            }
//                        } else {
//                            Toast.makeText(this, "Please enter Date", Toast.LENGTH_LONG).show();
//                        }
//                    } else {
//                        Toast.makeText(this, "Please enter Phone", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(this, "Please enter Name", Toast.LENGTH_LONG).show();
//                }
//            } else {
//                Toast.makeText(this, "Please enter Amount", Toast.LENGTH_LONG).show();
//            }
//        } else {
//            Toast.makeText(this, "Please enter Number of Cans", Toast.LENGTH_LONG).show();
//        }
    }
}