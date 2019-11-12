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
    EditText edtOwnerCity;
    EditText edtOwnerState;
    EditText edtDrillerName;
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
        edtOwnerCity = findViewById(R.id.edtOwnerCity);
        edtOwnerState = findViewById(R.id.edtOwnerState);
        edtDrillerName = findViewById(R.id.edtDrillerName);
        btnSubmitWell = findViewById(R.id.btnSumbitWell);

        btnSubmitWell.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        submitWell();
    }

    public void submitWell(){
        final String wellName = edtWellName.getText().toString().trim();
        final String latitude = edtLatitude.getText().toString().trim();
        final String longitude= edtLongitude.getText().toString().trim();
        final String address = edtAddress.getText().toString().trim();
        final String wellType = edtWellType.getText().toString().trim();
        final String wellStatus = edtWellStatus.getText().toString().trim();
        final String ownerName = edtOwnerName.getText().toString().trim();
        final String dateEntered = edtDateEntered.getText().toString().trim();


        String key = mDatabaseRef.child("Well").push().getKey();
        Well newWell = new Well(wellName,latitude,longitude,address,wellType,wellStatus,ownerName,dateEntered);
        Map<String, Object> wellValues = newWell.ToMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Well/" + key, wellValues);

        submitOwner(key, newWell.Name);
        submitDriller(key);

        mDatabaseRef.updateChildren(childUpdates);
    }

    public void submitOwner(String wellKey, String wellName)
    {
        final String ownerName = edtOwnerName.getText().toString().trim();
        final String ownerCity = edtOwnerCity.getText().toString().trim();
        final String ownerState = edtOwnerState.getText().toString().trim();

        final String ownerAddress = "13214 Bob Ave. Tyler, Texas";

        String key = mDatabaseRef.child("Owner").push().getKey();
        Owner newOwner = new Owner(ownerName, ownerAddress, ownerCity, ownerState);
        Map<String, Object> ownerValues = newOwner.ToMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Owner/" + key, ownerValues);
        childUpdates.put("/Owner-Well/" + key + "/" + wellKey, wellName);
    }

    public void submitDriller(String wellKey)
    {
        final String drillerName = "Bob";
        final String drillerCompany = "Well Dig It";
        final int drillerLicenseNumber = 45678;
        final String drillerLicenseExpirationDate = "11/5/2020";

        String key = mDatabaseRef.child("Driller").push().getKey();
        Driller newDriller = new Driller(drillerName, drillerCompany, drillerLicenseNumber, drillerLicenseExpirationDate);
        Map<String, Object> wellValues = newDriller.ToMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Driller/" + key, wellValues);
        childUpdates.put("/Driller-Well/" + key + "/" + "Wells", wellKey);
    }
}
