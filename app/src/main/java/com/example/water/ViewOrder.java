package com.example.water;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ViewOrder extends AppCompatActivity {

    EditText editTextNumOfCans, editTextAmount, editTextName, editTextPhone, editTextDate, editTextAddress, editTextLandmark;
    ListView listViewOrders;
    List<Order> orders;
    DatabaseReference databaseReference;
    private String UserName;

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        //getRefrance for user table
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");

        editTextNumOfCans = (EditText) findViewById(R.id.editTextNumOfCans);
        editTextAmount = (EditText) findViewById(R.id.editTextAmount);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextLandmark = (EditText) findViewById(R.id.editTextLandmark);
        listViewOrders = (ListView) findViewById(R.id.listViewOrders);

        //list for store objects of order
        orders = new ArrayList<>();

        // list item click listener
        listViewOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = orders.get(position);
                CallUpdateAndDeleteDialog(order.getOrderid(), order.getNumofcans(), order.getAmount(), order.getName(), order.getPhone(), order.getDate(), order.getAddress(), order.getLandmark());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous User list
                orders.clear();
                //getting all nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting User from firebase console
                    Order order = postSnapshot.getValue(Order.class);
                    //adding User to the list
                    orders.add(order);
                }
                //creating Userlist adapter
                OrderList UserAdapter = new OrderList(ViewOrder.this, orders);
                //attaching adapter to the listview
                listViewOrders.setAdapter(UserAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void CallUpdateAndDeleteDialog(final String orderid, String numofcans, String amount, String name, String phone, String date, String address, String landmark) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);
        //Access Dialog views
        final EditText updateTextnumofcans = (EditText) dialogView.findViewById(R.id.updateTextnumofcans);
        final EditText updateTextamount = (EditText) dialogView.findViewById(R.id.updateTextamount);
        final EditText updateTextname = (EditText) dialogView.findViewById(R.id.updateTextname);
        final EditText updateTextphone = (EditText) dialogView.findViewById(R.id.updateTextphone);
        final EditText updateTextdate = (EditText) dialogView.findViewById(R.id.updateTextdate);
        final EditText updateTextaddress = (EditText) dialogView.findViewById(R.id.updateTextaddress);
        final EditText updateTextlandmark = (EditText) dialogView.findViewById(R.id.updateTextlandmark);

        updateTextnumofcans.setText(numofcans);

        updateTextnumofcans.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int num1 = Integer.parseInt(updateTextnumofcans.getText().toString());
                    if (num1>0) {
                        int total = num1 * 30;
                        updateTextamount.setText(Integer.toString(total));
                        updateTextname.requestFocus();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        updateTextamount.setText(amount);

        updateTextname.setText(name);

        updateTextphone.setText(phone);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        updateTextdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ViewOrder.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day+"/"+month+"/"+year;
                        updateTextdate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        updateTextdate.setText(date);

        updateTextaddress.setText(address);

        updateTextlandmark.setText(landmark);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateOrder);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteOrder);
        final Button buttonPayment = (Button) dialogView.findViewById(R.id.buttonPayment);
        //username for set dialog title
        dialogBuilder.setTitle(UserName);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        // Click listener for Update data
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numofcans = updateTextnumofcans.getText().toString().trim();
                String amount = updateTextamount.getText().toString().trim();
                String name = updateTextname.getText().toString().trim();
                String phone = updateTextphone.getText().toString().trim();
                String date = updateTextdate.getText().toString().trim();
                String address = updateTextaddress.getText().toString().trim();
                String landmark = updateTextlandmark.getText().toString().trim();

                if(numofcans.isEmpty()){
                    updateTextnumofcans.setError("Number of Can is required!");
                    updateTextnumofcans.requestFocus();
                    return;
                }

                if(amount.isEmpty()){
                    updateTextamount.setError("Amount is required!");
                    updateTextamount.requestFocus();
                    return;
                }

                if(name.isEmpty()){
                    updateTextname.setError("Name is required!");
                    updateTextname.requestFocus();
                    return;
                }

                if(phone.isEmpty()){
                    updateTextphone.setError("Phone Number is required!");
                    updateTextphone.requestFocus();
                    return;
                }

                if(phone.length() < 10){
                    updateTextphone.setError("Please enter valid Phone number!");
                    updateTextphone.requestFocus();
                    return;
                }

                if(date.isEmpty()){
                    updateTextdate.setError("Date is required!");
                    updateTextdate.requestFocus();
                    return;
                }

                if(address.isEmpty()){
                    updateTextaddress.setError("Address is required!");
                    updateTextaddress.requestFocus();
                    return;
                }

                if(landmark.isEmpty()){
                    updateTextlandmark.setError("Land-Mark is required!");
                    updateTextlandmark.requestFocus();
                    return;
                }
                //Method for update data
                updateOrder(orderid, numofcans, amount, name, phone, date, address, landmark);
                alertDialog.dismiss();
                //checking if the value is provided or not Here, you can Add More Validation as you required
//                if (!TextUtils.isEmpty(numofcans)) {
//                    if (!TextUtils.isEmpty(amount)) {
//                        if (!TextUtils.isEmpty(name)) {
//                            if (!TextUtils.isEmpty(phone)) {
//                                if (!TextUtils.isEmpty(date)) {
//                                    if (!TextUtils.isEmpty(address)) {
//                                        if (!TextUtils.isEmpty(landmark)) {
//                                            //Method for update data
//                                            updateOrder(orderid, numofcans, amount, name, phone, date, address, landmark);
//                                            alertDialog.dismiss();
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
            }
        });

        // Click listener for Delete data
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Method for delete data
                deleteOrder(orderid);
                alertDialog.dismiss();
            }
        });

        //Payment here using UPI
        buttonPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewOrder.this, PaymentActivity.class));
                alertDialog.dismiss();
            }
        });
    }

    private boolean updateOrder(String id, String numofcans, String amount, String name, String phone, String date, String address, String landmark) {
        //getting the specified User reference
        DatabaseReference UpdateReference = FirebaseDatabase.getInstance().getReference("Orders").child(id);
        Order order = new Order(id, numofcans, amount, name, phone, date, address, landmark);
        //update User to firebase
        UpdateReference.setValue(order);
        Toast.makeText(getApplicationContext(), "Order Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteOrder(String id) {
        //getting the specified User reference
        DatabaseReference DeleteReference = FirebaseDatabase.getInstance().getReference("Orders").child(id);
        //removing User
        DeleteReference.removeValue();
        Toast.makeText(getApplicationContext(), "Order Deleted", Toast.LENGTH_LONG).show();
        return true;
    }
}