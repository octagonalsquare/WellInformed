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
        key = extras.getString("Selected Report");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        getReport();

    }

    private void displayReport()
    {
        TextView name = findViewById(R.id.ReportWellName);
        name.setTextSize(20);
        name.setText(report.WellName);

        TextView id = findViewById(R.id.ReportWellID);
        id.setTextSize(20);
        id.setText(report.WellID);

        TextView date = findViewById(R.id.ReportDate);
        String dateString = new String();
        dateString = key;
        dateString = dateString.split(":")[1];
        dateString = dateString.substring(0,dateString.length()-2);
        date.setTextSize(20);
        date.setText(dateString);

        TextView address = findViewById(R.id.ReportWellAddress);
        address.setTextSize(20);
        address.setText(report.WellAddress);

        TextView totalDepth = findViewById(R.id.ReportTotalDepth);
        totalDepth.setTextSize(20);
        totalDepth.setText(report.TotalDepth);

        TextView waterLevel = findViewById(R.id.ReportWaterLevel);
        waterLevel.setTextSize(20);
        waterLevel.setText(report.WaterLevel);

        TextView casingType = findViewById(R.id.ReportCasingType);
        casingType.setTextSize(20);
        casingType.setText(report.CasingType);

        TextView casingHeight = findViewById(R.id.ReportCasingHeight);
        casingHeight.setTextSize(20);
        casingHeight.setText(report.CasingHeight);

        TextView sleeveType = findViewById(R.id.ReportSleeveType);
        sleeveType.setTextSize(20);
        sleeveType.setText(report.SleeveType);

        TextView sleeveHeight = findViewById(R.id.ReportSleeveHeight);
        sleeveHeight.setTextSize(20);
        sleeveHeight.setText(report.SleeveHeight);

        TextView screenDepth = findViewById(R.id.ReportScreenDepth);
        screenDepth.setTextSize(20);
        screenDepth.setText(report.ScreenDepth);

        TextView annularDepth = findViewById(R.id.ReportAnnularCementDepth);
        annularDepth.setTextSize(20);
        annularDepth.setText(report.AnnularCementDepth);

        TextView annularVerified = findViewById(R.id.ReportAnnularCementVerified);
        annularVerified.setTextSize(20);
        annularVerified.setText(report.AnnularCementVerified);

        TextView padDimensions = findViewById(R.id.ReportPadDimensions);
        padDimensions.setTextSize(20);
        padDimensions.setText(report.PadDimensions);

        TextView flowTestGPM = findViewById(R.id.ReportFlowTestGPM);
        flowTestGPM.setTextSize(20);
        flowTestGPM.setText(report.FlowTestGPM);

        TextView flowTestTime = findViewById(R.id.ReportFlowTestMethodTime);
        flowTestTime.setTextSize(20);
        flowTestTime.setText(report.FlowTestMethodTime);

        TextView sanitarySeal = findViewById(R.id.ReportSanitaryWellSeal);
        sanitarySeal.setTextSize(20);
        if (report.SanitaryWellSeal)
            sanitarySeal.setText("True");
        else
            sanitarySeal.setText("False");

        TextView septicDistane = findViewById(R.id.ReportSepticDistance);
        septicDistane.setTextSize(20);
        septicDistane.setText(report.SepticDistance);

        TextView propertyLine = findViewById(R.id.ReportPropertyLineDistance);
        propertyLine.setTextSize(20);
        propertyLine.setText(report.PropertyLineDistance);

        TextView nearestWell = findViewById(R.id.ReportNearestWellDistance);
        nearestWell.setTextSize(20);
        nearestWell.setText(report.NearestWellDistance);

        TextView aerobicSpray = findViewById(R.id.ReportAerobicSprayAreaDistance);
        aerobicSpray.setTextSize(20);
        aerobicSpray.setText(report.AerobicSprayAreaDistance);

        TextView septicLateral = findViewById(R.id.ReportSepticLateralLinesDistance);
        septicLateral.setTextSize(20);
        septicLateral.setText(report.SepticLateralLinesDistance);

        TextView otherContSources = findViewById(R.id.ReportOtherContaminationSourcesDistance);
        otherContSources.setTextSize(20);
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
                report = dataSnapshot.getValue(WellInspection.class);
                displayReport();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                System.out.println("The read failed: " + FirebaseError.ERROR_INTERNAL_ERROR);
            }
        });

    }
}
