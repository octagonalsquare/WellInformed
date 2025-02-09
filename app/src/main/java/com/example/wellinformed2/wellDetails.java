package com.example.wellinformed2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

//well details activity that displays the details of a selected well
public class wellDetails extends AppCompatActivity implements View.OnClickListener {

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
    private List<String> reportKeys;

    DatabaseReference mReportRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_details);
        Bundle extras = getIntent().getExtras();
        selectedWell = (Well)extras.get("Selected");
        selectedWellID = extras.getString("Selected ID");

        database = FirebaseDatabase.getInstance();
        mReportRef = database.getReference().child("Well-InspectionReports").child(selectedWell.ID);

        inspectionButton = findViewById(R.id.buttonDetailsStartInspection);
        inspectionButton.setOnClickListener(this);

        displayWellDetails();
        getReportKeys();
    }

    //Gives directions to each button to do if selected
    @Override
    public void onClick(View view)
    {
        Intent i = new Intent(this, WellInspectionActivity.class);
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

    //This add the report title to the well details page.
    public void displayReportList()
    {
        TableLayout table = findViewById(R.id.ReportListTable);
        table.removeAllViews();
        for (int i = 0; i < reportKeys.size(); i++)
        {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(MATCH_PARENT, 100);
            row.setLayoutParams(lp);

            TextView report = new TextView(this);
            report.setText(reportKeys.get(i));
            report.setTextSize(20);
            report.setLayoutParams(lp);
            report.setId(i);

            report.setOnClickListener(this::onReportClick);

            if(i%2==0)
            {
                row.setBackgroundColor(getResources().getColor(R.color.evenRowBackground));
            }

            row.addView(report);
            table.addView(row);
        }

    }

    //This grabs the well report keys from the database so that the data can be fetched
    public void getReportKeys(){
        reportKeys = new ArrayList<>();

        mReportRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShot:dataSnapshot.getChildren()){
                    String reportKeyTemp = String.valueOf(postSnapShot.getKey());
                    reportKeys.add(reportKeyTemp);
                }
                displayReportList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //This lets the app know which inspection report you want to open and view
    private void onReportClick(View view) {
        String selectedReport = reportKeys.get(view.getId());
        Intent i = new Intent(this, ViewReport.class);
        i.putExtra("Selected Report", selectedReport);
        startActivity(i);
    }


}
