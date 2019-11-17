package com.example.wellinformed2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

//This page displays different button to click to navigate and see different data from the database
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //define TextView & button variable
    TextView txvWellIndex;
    TextView txvWellOwner;
    TextView txvDrillerIndex;
    TextView txvNavigation;
    Button btnSignOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize TextView variable to layout
        txvWellIndex=findViewById(R.id.txvWellIndex);
        txvWellOwner=findViewById(R.id.txvWellOwner);
        txvDrillerIndex=findViewById(R.id.txvDrillerIndex);
        txvNavigation=findViewById(R.id.txvNavigation);
        btnSignOut = findViewById(R.id.btnSignOut);

        //set click listener
        txvWellIndex.setOnClickListener(this);
        txvWellOwner.setOnClickListener(this);
        txvDrillerIndex.setOnClickListener(this);
        txvNavigation.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
    }



    //when a button is clicked open a new activity
    @Override
    public void onClick(View view) {

        //well owners button is clicked go to well owners activity
        if(view==txvWellOwner){
            startActivity(new Intent(this, ownerIndex.class));
        }

        //well index button is clicked go to well index activity
        if(view==txvWellIndex){
            startActivity(new Intent(this, wellIndex.class));
        }

        //driller index is clicked go to driller index activity
        if(view==txvDrillerIndex){
            startActivity(new Intent(this, drillerIndex.class));
        }

        //navigation button is clicked go to google map activity
        if(view==txvNavigation){
            startActivity(new Intent(this, GoogleMapActivity.class));
        }

        //button sign out page is clicked sign out user and go to login page
        if(view==btnSignOut){
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
