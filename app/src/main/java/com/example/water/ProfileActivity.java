package com.example.water;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private TextView welcomeUser;
    private Button logout;
    private Button order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        welcomeUser = (TextView) findViewById(R.id.welcomeUser);
        order = (Button) findViewById(R.id.orderCan);
        logout = (Button) findViewById(R.id.signOut);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                finish();
            }
        });

        welcomeUser.setText("Dear User,"+
                "\nOrder Your Water Can through Online by This Application."+
                "\nHave a Nice Day");

//        user = FirebaseAuth.getInstance().getCurrentUser();
//        reference = FirebaseDatabase.getInstance().getReference("Users");
//        userID = user.getUid();

//        final TextView greetingTextView = (TextView) findViewById(R.id.greeting);
//        final TextView fullNameTextView = (TextView) findViewById(R.id.fullName);
//        final TextView phoneTextView = (TextView) findViewById(R.id.phone);
//        final TextView emailTextView = (TextView) findViewById(R.id.email);
//        final TextView passwordTextView = (TextView) findViewById(R.id.password);
//
//        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User userProfile = snapshot.getValue(User.class);
//
//                if(userProfile != null){
//                    String userfullName = userProfile.fullName;
//                    String userphone = userProfile.phone;
//                    String useremail = userProfile.email;
//                    String userpassword = userProfile.password;
//
//                    greetingTextView.setText("Welcome, " + userfullName + "!");
//                    fullNameTextView.setText(userfullName);
//                    phoneTextView.setText(userphone);
//                    emailTextView.setText(useremail);
//                    passwordTextView.setText(userpassword);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(ProfileActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.logo);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}