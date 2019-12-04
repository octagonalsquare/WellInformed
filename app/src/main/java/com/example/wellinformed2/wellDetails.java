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
    private ScrollView wellDetailsScrollView;
    public List<String> ReportKeys;
    public List<WellInspection> Reports;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_details);
        Bundle extras = getIntent().getExtras();
        selectedWell = (Well)extras.get("Selected");
        selectedWellID = extras.getString("Selected ID");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        inspectionButton = findViewById(R.id.buttonDetailsStartInspection);
        wellDetailsScrollView = findViewById(R.id.well_details_scroll_view);
        inspectionButton.setOnClickListener(this);
        ReportKeys = new ArrayList<>();
        Reports = new ArrayList<>();
        getReportKeys();

        displayWellDetails();
        displayReportList();
    }

    //Gives directions to each button to do if selected
    @Override
    public void onClick(View view)
    {
        Intent i = new Intent(this, WellInspectionActivity.class);
        i.putExtra("Selected Well", selectedWell);
        startActivity(i);
    }

    public void onReportClick(View view)
    {
        Intent i = new Intent(this, ViewReport.class);
        i.putExtra("Report Key", ReportKeys.get(view.getId()));
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

    public void displayReportList()
    {
        myRef = myRef.child("Well-InspectionReports/" + selectedWellID);

        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    WellInspection report = postSnapshot.getValue(WellInspection.class);
                    String key = postSnapshot.getKey();
                    Reports.add(report);
                    ReportKeys.add(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                System.out.println("The read failed: " + FirebaseError.ERROR_INTERNAL_ERROR);
            }
        });
        TableLayout table = findViewById(R.id.ReportListTable);
        table.removeAllViews();

        for (String r : ReportKeys)
        {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(MATCH_PARENT, 30);
            row.setLayoutParams(lp);

            TextView report = new TextView(this);
            report.setText(r);
            report.setLayoutParams(lp);
            report.setId(ReportKeys.indexOf(r));

            report.setOnClickListener(this::onReportClick);

            if(ReportKeys.indexOf(r)%2==0)
            {
                row.setBackgroundColor(getResources().getColor(R.color.evenRowBackground));
            }

            row.addView(report);
            table.addView(row, ReportKeys.indexOf(r));
        }
    }

    public void getReportKeys()
    {

    }
}
