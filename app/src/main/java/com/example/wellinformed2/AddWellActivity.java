package com.example.wellinformed2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    DatePicker edtDateEntered;
    EditText edtOwnerCity;
    EditText edtOwnerState;
    EditText edtDrillerName;
    EditText edtDrillerCompany;
    EditText edtDrillerLicenseNumber;
    DatePicker edtDrillerLicenseExpirationDate;
    EditText edtOwnerAddress;
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
        edtOwnerAddress = findViewById(R.id.edtOwnerAddress);
        edtDrillerName = findViewById(R.id.edtDrillerName);
        edtDrillerLicenseNumber = findViewById(R.id.edtDrillerLicenseNumber);
        edtDrillerCompany = findViewById(R.id.edtDrillerCompany);
        edtDrillerLicenseExpirationDate = findViewById(R.id.edtDrillerLicenseExpirationDate);
        btnSubmitWell = findViewById(R.id.btnSumbitWell);

        btnSubmitWell.setOnClickListener(this);

    }

    //Onclick function executes when submit button is clicked
    @Override
    public void onClick(View view) {
        submitWell();
    }

    //This function takes all the details the user enters into the fields and stores them into a variable
    //once variables are assigned a value they are uploaded to the database
    public void submitWell(){
        final String wellName = edtWellName.getText().toString().trim();
        final String latitude = edtLatitude.getText().toString().trim();
        final String longitude= edtLongitude.getText().toString().trim();
        final String address = edtAddress.getText().toString().trim();
        final String wellType = edtWellType.getText().toString().trim();
        final String wellStatus = edtWellStatus.getText().toString().trim();
        final String ownerName = edtOwnerName.getText().toString().trim();
        final String dateEntered = edtDateEntered.getDayOfMonth() + "/" + edtDateEntered.getMonth()
                + "/" + edtDateEntered.getYear();
        final String drillerName = edtDrillerName.getText().toString().trim();

        if (TextUtils.isEmpty(wellName)) {
            Toast.makeText(this, "Please enter well name", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(latitude)) {
            Toast.makeText(this, "Please enter latitude coordinates", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(longitude)) {
            Toast.makeText(this, "Please enter longitude coordinates", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(drillerName)) {
            Toast.makeText(this, "Please enter driller name", Toast.LENGTH_LONG).show();
            return;
        }

        String key = drillerName + ":" + wellName;
        Well newWell = new Well(wellName, latitude, longitude, wellStatus, address, wellType,
                ownerName, dateEntered, drillerName);
        Map<String, Object> wellValues = newWell.ToMap();

        Map<String, Object> childUpdates = new HashMap<>();

        mDatabaseRef.child("Well").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean found = false;
                for( DataSnapshot data : dataSnapshot.getChildren())
                {
                    if (data.getKey().compareTo(key) == 0)
                    {
                        found = true;
                    }
                }
                if (!found)
                {
                    childUpdates.put("/Well/" + key, wellValues);
                    mDatabaseRef.updateChildren(childUpdates);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        submitOwner(key, newWell.Name);
        submitDriller(key, newWell.Name);
    }

    //submitOwner takes all the details the user enters into the fields of
    //the owner section and stores them in a variable
    //once variable are assigned a value they are uploaded to the database
    public void submitOwner(String wellKey, String wellName)
    {
        final String ownerName = edtOwnerName.getText().toString().trim();
        final String ownerCity = edtOwnerCity.getText().toString().trim();
        final String ownerState = edtOwnerState.getText().toString().trim();
        final String ownerAddress = edtOwnerAddress.getText().toString().trim();

        String key = ownerName;
        Owner newOwner = new Owner(ownerName, ownerAddress, ownerCity, ownerState);
        Map<String, Object> ownerValues = newOwner.ToMap();

        Map<String, Object> childUpdates = new HashMap<>();

        mDatabaseRef.child("Owner").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean found = false;
                for( DataSnapshot data : dataSnapshot.getChildren())
                {
                    if (data.getKey().compareTo(key) == 0)
                    {
                        found = true;
                    }
                }
                if (!found)
                {
                    childUpdates.put("/Owner/" + key, ownerValues);
                    childUpdates.put("/Owner-Well/" + key + "/" + wellKey, wellName);
                    mDatabaseRef.updateChildren(childUpdates);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //submitDriller takes all the details the user enters inot the fields of
    //the driller section and stores them in a variable
    //once variables are assigned a value they are uploaded to the database
    public void submitDriller(String wellKey, String wellName)
    {
        final String drillerName = edtDrillerName.getText().toString().trim();
        final String drillerCompany = edtDrillerCompany.getText().toString().trim();
        int temp;
        if (edtDrillerLicenseNumber.getText().toString().trim().compareTo("") != 0)
        {
            temp = Integer.parseInt(edtDrillerLicenseNumber.getText().toString().trim());
        }
        else
        {
            temp = 0;
        }
        final int drillerLicenseNumber = temp;
        final String drillerLicenseExpirationDate = edtDrillerLicenseExpirationDate.getDayOfMonth()
                + "/" + edtDrillerLicenseExpirationDate.getMonth() + "/" 
                + edtDrillerLicenseExpirationDate.getYear();

        String key = drillerName;
        Driller newDriller = new Driller(drillerName, drillerCompany, drillerLicenseNumber, drillerLicenseExpirationDate);
        Map<String, Object> wellValues = newDriller.ToMap();
        wellValues.remove(drillerName);
        Map<String, Object> childUpdates = new HashMap<>();

        mDatabaseRef.child("Driller").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean found = false;
                for( DataSnapshot data : dataSnapshot.getChildren())
                {
                    if (data.getKey().compareTo(key) == 0)
                    {
                        found = true;
                    }
                }

                if (!found)
                {
                    childUpdates.put("/Driller/" + key, wellValues);
                    childUpdates.put("/Driller-Well/" + key + "/" + wellKey, wellName);
                    mDatabaseRef.updateChildren(childUpdates);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
