package com.example.wellinformed2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "My Log:";

    //defining view objects
    private EditText edtEmail,edtPassword;
    private Button btnLogin;

    //defining firebase authentication object
    private FirebaseAuth mAuth;

    //defining firebase database & reference
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initializing firebase object
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        //if current user does not return null
        if(mAuth.getCurrentUser()!=null)
        { //that means the user is already logged in

            database = FirebaseDatabase.getInstance();

            mDatabase = database.getReference().child("users").child(user.getUid());

            //name, email, address, and photo url
            String name = user.getDisplayName();
            String email = user.getEmail();
            //Url photoUrl = user.getPhotoUrl();
            //and open profile activity
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            i.putExtra("name",name);
            i.putExtra("Email",email);
            //i.putExtra("PhotoUrl",photoUrl);

            //so close this activity
            finish();
            startActivity(i);

        }

        //initialize View

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnSignIn);

        btnLogin.setOnClickListener(this);

        btnLogin=findViewById(R.id.btnSignIn);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==btnLogin){
            userLogin();
            //startActivity(new Intent(this,MainActivity.class));
            //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            //startActivity(intent, options.toBundle());
            //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
    }

    private void userLogin() {

        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        //checking if email and password are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        //logging in the user
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                if(task.isSuccessful()){
                    //sign success transit to profile
                    Toast.makeText(LoginActivity.this,"Authentication Succeeded",Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                else
                {
                    //if sign-in fails display message to the user
                    Toast.makeText(LoginActivity.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
