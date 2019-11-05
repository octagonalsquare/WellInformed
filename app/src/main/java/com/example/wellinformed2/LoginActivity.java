package com.example.wellinformed2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            SQLConnection sqlConnection = new SQLConnection();
        }
        catch (Exception e)
        {
            System.out.println("BANANA");
        }

        setContentView(R.layout.activity_login);

        btnSignIn=findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==btnSignIn){
            //startActivity(new Intent(this,MainActivity.class));
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent, options.toBundle());
            //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
    }
}
