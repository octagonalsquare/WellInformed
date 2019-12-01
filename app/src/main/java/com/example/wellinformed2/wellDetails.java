package com.example.wellinformed2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//well details activity that displays the details of a selected well
public class wellDetails extends AppCompatActivity implements View.OnClickListener
{


    private Well selectedWell;
    private String selectedWellID;

    private TextView wellIDView;
    private TextView wellNameView;
    private TextView wellLatitudeView;
    private TextView wellLongitudeView;
    private TextView wellAddressView;
    private TextView wellStatusView;
    private TextView wellTypeView;
    private TextView wellDateEnteredView;
    private TextView wellOwnerView;
    private Button inspectionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_details);
        Bundle extras = getIntent().getExtras();
        selectedWell = (Well)extras.get("Selected");
        selectedWellID = extras.getString("Selected ID");

        inspectionButton = findViewById(R.id.buttonDetailsStartInspection);
        inspectionButton.setOnClickListener(this);

        displayWellDetails();
    }

    //Gives directions to each button to do if selected
    @Override
    public void onClick(View view)
    {
        Intent i = new Intent(this, wellInspection.class);
        i.putExtra("Selected Well", selectedWell);
        startActivity(i);
    }

    //displays well details by setting the text of the appropriate text view
    public void displayWellDetails()
    {
        wellIDView = findViewById(R.id.txvDetailsID);
        wellNameView = findViewById(R.id.txvDetailsWellName);
        wellLatitudeView = findViewById(R.id.txvDetailsLatitude);
        wellLongitudeView = findViewById(R.id.txvDetailsLongitude);
        wellAddressView = findViewById(R.id.txvDetailsAddress);
        wellStatusView = findViewById(R.id.txvDetailsStatus);
        wellTypeView = findViewById(R.id.txvDetailsWellType);
        wellDateEnteredView = findViewById(R.id.txvDetailsDateEntered);
        wellOwnerView = findViewById(R.id.txvDetailsOwner);

        wellIDView.setText(selectedWellID);
        wellNameView.setText(selectedWell.Name);
        wellLatitudeView.setText(selectedWell.Latitude);
        wellLongitudeView.setText(selectedWell.Longitude);
        wellAddressView.setText(selectedWell.Address);
        wellStatusView.setText(selectedWell.Status);
        wellTypeView.setText(selectedWell.Type);
        wellDateEnteredView.setText(selectedWell.Date);
        wellOwnerView.setText(selectedWell.Owner);
    }
}
