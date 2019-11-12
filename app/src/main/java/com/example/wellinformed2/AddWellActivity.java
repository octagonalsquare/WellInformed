package com.example.wellinformed2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddWellActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtWellName;
    EditText edtLatitude;
    EditText edtLongitude;
    EditText edtAddress;
    EditText edtWellType;
    EditText edtWellStatus;
    EditText edtOwnerName;
    EditText edtDateEntered;
    Button btnSubmitWell;

    FirebaseDatabase database;
    DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_well);

        database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getReference();

        edtWellName = findViewById(R.id.edtWellName);
        edtLatitude = findViewById(R.id.edtLatitude);
        edtLongitude = findViewById(R.id.edtLongitude);
        edtAddress = findViewById(R.id.edtAddress);
        edtWellType = findViewById(R.id.edtWellType);
        edtWellStatus = findViewById(R.id.edtStatus);
        edtOwnerName = findViewById(R.id.edtOwnerName);
        edtDateEntered = findViewById(R.id.edtDateEntered);
        btnSubmitWell = findViewById(R.id.btnSumbitWell);

        btnSubmitWell.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        submitWell();
    }

    public void submitWell(){
        final String wellName = edtWellName.getText().toString().trim();
        final String latitude = edtWellName.getText().toString().trim();
        final String longitude= edtWellName.getText().toString().trim();
        final String address = edtWellName.getText().toString().trim();
        final String wellType = edtWellName.getText().toString().trim();
        final String wellStatus = edtWellName.getText().toString().trim();
        final String ownerName = edtWellName.getText().toString().trim();
        final String dateEntered = edtWellName.getText().toString().trim();


        String key = mDatabaseRef.child("Well").push().getKey();
        Well newWell = new Well(wellName,latitude,longitude,address,wellType,wellStatus,ownerName,dateEntered);
        Map<String, Object> wellValues = newWell.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Well/" + key, wellValues);
        childUpdates.put("/Well-Driller/" + 123 + "/" + key, wellValues);

        mDatabaseRef.updateChildren(childUpdates);
    }
}
