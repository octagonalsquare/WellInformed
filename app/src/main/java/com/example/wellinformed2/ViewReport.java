package com.example.wellinformed2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewReport extends AppCompatActivity
{
    public WellInspection report;
    private String key;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        Bundle extras = getIntent().getExtras();
        key = (String) extras.get("Report Key");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        getReport();

        displayReport();
    }

    private void displayReport()
    {
        TextView name = findViewById(R.id.ReportWellName);
        name.setText(report.WellName);

        TextView id = findViewById(R.id.ReportWellID);
        id.setText(report.WellID);

        TextView date = findViewById(R.id.ReportDate);
        String dateString = new String();
        dateString = report.Key;
        dateString = dateString.split(":")[1];
        date.setText(dateString);

        TextView address = findViewById(R.id.ReportWellAddress);
        address.setText(report.WellAddress);

        TextView totalDepth = findViewById(R.id.ReportTotalDepth);
        totalDepth.setText(report.TotalDepth);

        TextView waterLevel = findViewById(R.id.ReportWaterLevel);
        waterLevel.setText(report.WaterLevel);

        TextView casingType = findViewById(R.id.ReportCasingType);
        casingType.setText(report.CasingType);

        TextView casingHeight = findViewById(R.id.ReportCasingHeight);
        casingHeight.setText(report.CasingHeight);

        TextView sleeveType = findViewById(R.id.ReportSleeveType);
        sleeveType.setText(report.SleeveType);

        TextView sleeveHeight = findViewById(R.id.ReportSleeveHeight);
        sleeveHeight.setText(report.SleeveHeight);

        TextView screenDepth = findViewById(R.id.ReportScreenDepth);
        screenDepth.setText(report.ScreenDepth);

        TextView annularDepth = findViewById(R.id.ReportAnnularCementDepth);
        annularDepth.setText(report.AnnularCementDepth);

        TextView annularVerified = findViewById(R.id.ReportAnnularCementVerified);
        annularVerified.setText(report.AnnularCementVerified);

        TextView padDimensions = findViewById(R.id.ReportPadDimensions);
        padDimensions.setText(report.PadDimensions);

        TextView flowTestGPM = findViewById(R.id.ReportFlowTestGPM);
        flowTestGPM.setText(report.FlowTestGPM);

        TextView flowTestTime = findViewById(R.id.ReportFlowTestMethodTime);
        flowTestTime.setText(report.FlowTestMethodTime);

        TextView sanitarySeal = findViewById(R.id.ReportSanitaryWellSeal);
        if (report.SanitaryWellSeal)
            sanitarySeal.setText("True");
        else
            sanitarySeal.setText("False");

        TextView septicDistane = findViewById(R.id.ReportSepticDistance);
        septicDistane.setText(report.SepticDistance);

        TextView propertyLine = findViewById(R.id.ReportPropertyLineDistance);
        propertyLine.setText(report.PropertyLineDistance);

        TextView nearestWell = findViewById(R.id.ReportNearestWellDistance);
        nearestWell.setText(report.NearestWellDistance);

        TextView aerobicSpray = findViewById(R.id.ReportAerobicSprayAreaDistance);
        aerobicSpray.setText(report.AerobicSprayAreaDistance);

        TextView septicLateral = findViewById(R.id.ReportSepticLateralLinesDistance);
        septicLateral.setText(report.SepticLateralLinesDistance);

        TextView otherContSources = findViewById(R.id.ReportOtherContaminationSourcesDistance);
        otherContSources.setText(report.OtherContaminationSourcesDistance);
    }

    public void getReport()
    {
        myRef = myRef.child("InspectionReports/" + key);

        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    if (postSnapshot.getKey() == key)
                    {
                        report = postSnapshot.getValue(WellInspection.class);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                System.out.println("The read failed: " + FirebaseError.ERROR_INTERNAL_ERROR);
            }
        });

    }
}
