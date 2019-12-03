package com.example.wellinformed2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class WellInspectionActivity extends AppCompatActivity implements View.OnClickListener {

    TextView submitButton;
    String wellName;
    String wellID;
    String drillerName;

    FirebaseDatabase database;
    DatabaseReference mDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_inspection);

        Bundle extras = getIntent().getExtras();
        Well temp = (Well)extras.get("Selected Well");
        wellName = temp.Name;
        wellID = temp.ID;
        drillerName = temp.DrillerName;
        prepopulate();

        database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getReference();

        submitButton = findViewById(R.id.WellInspectionSubmit);

        submitButton.setOnClickListener(this);
    }

    //Submit button is clicked add report to well
    @Override
    public void onClick(View view)
    {
        if(view == submitButton)
        {
            createInspection();
        }
    }

    public void prepopulate()
    {
        EditText edit = findViewById(R.id.WellInspectionWellName);
        edit.setText(wellName);
        edit =  findViewById(R.id.WellInspectionWellID);
        edit.setText(wellID);
    }


    //report is created by taking all the fields the user entered
    public void createInspection()
    {
        Boolean complete = new Boolean(true);


        EditText edit = findViewById(R.id.WellInspectionWellName);
        Editable editable = edit.getText();
        String WellName = editable.toString();
        if(TextUtils.isEmpty(WellName))
        {
            complete = false;
            Toast.makeText(this, "Need Well Name", Toast.LENGTH_SHORT).show();
            return;
        }

        edit =  findViewById(R.id.WellInspectionWellID);
        editable = edit.getText();
        String WellID = editable.toString();
        if(TextUtils.isEmpty(WellID))
        {
            complete = false;
            Toast.makeText(this, "Need Well ID", Toast.LENGTH_SHORT).show();
            return;
        }

        DatePicker today = findViewById(R.id.WellInspectionTodaysDate);
        final String date = today.getDayOfMonth() + "/" + today.getMonth()
                + "/" + today.getYear();

        edit =  findViewById(R.id.WellInspectionWellAddress);
        editable = edit.getText();
        String WellAddress = editable.toString();
        if(TextUtils.isEmpty(WellAddress))
        {
            complete = false;
            Toast.makeText(this, "Need Well Address", Toast.LENGTH_SHORT).show();
            return;
        }

        edit =  findViewById(R.id.WellInspectionTotalDepth);
        editable = edit.getText();
        String TotalDepth = editable.toString();
        if(TextUtils.isEmpty(TotalDepth))
        {
            complete = false;
            Toast.makeText(this, "Need Total Depth", Toast.LENGTH_SHORT).show();
            return;
        }

        edit =  findViewById(R.id.WellInspectionWaterLevel);
        editable = edit.getText();
        String WaterLevel = editable.toString();
        if(TextUtils.isEmpty(WaterLevel))
        {
            complete = false;
            Toast.makeText(this, "Need Water Level", Toast.LENGTH_SHORT).show();
            return;
        }

        edit =  findViewById(R.id.WellInspectionCasingType);
        editable = edit.getText();
        String CasingType = editable.toString();
        if(TextUtils.isEmpty(CasingType))
        {
            complete = false;
            Toast.makeText(this, "Need Casing Type", Toast.LENGTH_SHORT).show();
            return;
        }

        edit =  findViewById(R.id.WellInspectionCasingHeight);
        editable = edit.getText();
        String CasingHeight = editable.toString();
        if(TextUtils.isEmpty(CasingHeight))
        {
            complete = false;
            Toast.makeText(this, "Need Casing Height", Toast.LENGTH_SHORT).show();
            return;
        }

        edit =  findViewById(R.id.WellInspectionSleeveType);
        editable = edit.getText();
        String SleeveType = editable.toString();
        if(TextUtils.isEmpty(SleeveType))
        {
            complete = false;
            Toast.makeText(this, "Need Sleeve Type", Toast.LENGTH_SHORT).show();
            return;
        }

        edit =  findViewById(R.id.WellInspectionSleeveHeight);
        editable = edit.getText();
        String SleeveHeight = editable.toString();
        if(TextUtils.isEmpty(SleeveHeight))
        {
            complete = false;
            Toast.makeText(this, "Need Sleeve Height", Toast.LENGTH_SHORT).show();
            return;
        }

        edit =  findViewById(R.id.WellInspectionScreenDepth);
        editable = edit.getText();
        String ScreenDepth = editable.toString();
        if(TextUtils.isEmpty(ScreenDepth))
        {
            complete = false;
            Toast.makeText(this, "Need Well Depth", Toast.LENGTH_SHORT).show();
            return;
        }

        edit =  findViewById(R.id.WellInspectionAnnularCementDepth);
        editable = edit.getText();
        String AnnularCementDepth = editable.toString();
        if(TextUtils.isEmpty(AnnularCementDepth))
        {
            complete = false;
            Toast.makeText(this, "Need Annular Cement Depth", Toast.LENGTH_SHORT).show();
            return;
        }

        edit = findViewById(R.id.WellInspectionAnnularCementVerified);
        editable = edit.getText();
        String AnnularCementVerified = editable.toString();
        if(TextUtils.isEmpty(AnnularCementVerified))
        {
            complete = false;
            Toast.makeText(this, "Need Annular Cement Verified", Toast.LENGTH_SHORT).show();
            return;
        }

        edit =  findViewById(R.id.WellInspectionPadDimensions);
        editable = edit.getText();
        String PadDimensions = editable.toString();
        if(TextUtils.isEmpty(PadDimensions))
        {
            complete = false;
            Toast.makeText(this, "Need Pad Dimensions", Toast.LENGTH_SHORT).show();
            return;
        }

        edit =  findViewById(R.id.WellInspectionFlowTestGPM);
        editable = edit.getText();
        String FlowTestGPM = editable.toString();
        if(TextUtils.isEmpty(FlowTestGPM))
        {
            complete = false;
            Toast.makeText(this, "Need Flow Test GPM", Toast.LENGTH_SHORT).show();
            return;
        }

        edit =  findViewById(R.id.WellInspectionFlowTestMethodTime);
        editable = edit.getText();
        String FlowTestMethodTime = editable.toString();
        if(TextUtils.isEmpty(FlowTestMethodTime))
        {
            complete = false;
            Toast.makeText(this, "Need Flow Test Method Time", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton radio = findViewById(R.id.WellInspectionSanitaryWellSeal);
        Boolean SanitaryWellSeal = radio.isSelected();

        edit =  findViewById(R.id.WellInspectionSepticDistance);
        editable = edit.getText();
        String SepticDistance = editable.toString();
        if(TextUtils.isEmpty(SepticDistance))
        {
            complete = false;
            Toast.makeText(this, "Need Septic Distance", Toast.LENGTH_SHORT).show();
            return;
        }

        edit =  findViewById(R.id.WellInspectionPropertyLineDistance);
        editable = edit.getText();
        String PropertyLineDistance = editable.toString();
        if(TextUtils.isEmpty(PropertyLineDistance))
        {
            complete = false;
            Toast.makeText(this, "Need Property Line Distance", Toast.LENGTH_SHORT).show();
            return;
        }

        edit =  findViewById(R.id.WellInspectionNearestWellDistance);
        editable = edit.getText();
        String NearestWellDistance = editable.toString();
        if(TextUtils.isEmpty(NearestWellDistance))
        {
            complete = false;
            Toast.makeText(this, "Need Nearest Well Distance", Toast.LENGTH_SHORT).show();
            return;
        }

        edit =  findViewById(R.id.WellInspectionAerobicSprayAreaDistance);
        editable = edit.getText();
        String AerobicSprayAreaDistance = editable.toString();
        if(TextUtils.isEmpty(AerobicSprayAreaDistance))
        {
            complete = false;
            Toast.makeText(this, "Need Aerobic Spray Area Distance", Toast.LENGTH_SHORT).show();
            return;
        }

        edit =  findViewById(R.id.WellInspectionSepticLateralLinesDistance);
        editable = edit.getText();
        String SepticLateralLinesDistance = editable.toString();
        if(TextUtils.isEmpty(SepticLateralLinesDistance))
        {
            complete = false;
            Toast.makeText(this, "Need Septic Lateral Lines Distance", Toast.LENGTH_SHORT).show();
            return;
        }

        edit =  findViewById(R.id.WellInspectionOtherContaminationSourcesDistance);
        editable = edit.getText();
        String OtherContaminationSourcesDistance = editable.toString();
        if(TextUtils.isEmpty(OtherContaminationSourcesDistance))
        {
            complete = false;
            Toast.makeText(this, "Need Other Contamination Sources Distance", Toast.LENGTH_SHORT).show();
            return;
        }

        if(complete)
        {
            WellInspection report = new WellInspection(WellName, WellID, WellAddress, TotalDepth,
                    WaterLevel, CasingType, CasingHeight, SleeveType, SleeveHeight, ScreenDepth,
                    AnnularCementDepth, AnnularCementVerified, PadDimensions, FlowTestGPM,
                    FlowTestMethodTime, SanitaryWellSeal, SepticDistance, PropertyLineDistance,
                    NearestWellDistance, AerobicSprayAreaDistance, SepticLateralLinesDistance,
                    OtherContaminationSourcesDistance);

            Map<String, Object> childUpdates = new HashMap<>();
            final String key = report.WellName + ":" + date;
            final String wellKey = drillerName + ":" + wellName;

            mDatabaseRef.child("InspectionReports").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Boolean found = false;

                    childUpdates.put("/InspectionReports/" + key, report);
                    childUpdates.put("/Well-InspectionReports/" + wellKey + "/" + key, report);
                    mDatabaseRef.updateChildren(childUpdates);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

}

class WellInspection implements Serializable
{
    public  String Key;
    public  String WellName;
    public  String WellID;
    public  String WellAddress;
    public  String TotalDepth;
    public  String WaterLevel;
    public  String CasingType;
    public  String CasingHeight;
    public  String SleeveType;
    public  String SleeveHeight;
    public  String ScreenDepth;
    public  String AnnularCementDepth;
    public  String AnnularCementVerified;
    public  String PadDimensions;
    public  String FlowTestGPM;
    public  String FlowTestMethodTime;
    public  Boolean SanitaryWellSeal;
    public  String SepticDistance;
    public  String PropertyLineDistance;
    public  String NearestWellDistance;
    public  String AerobicSprayAreaDistance;
    public  String SepticLateralLinesDistance;
    public  String OtherContaminationSourcesDistance;

    WellInspection(String wellName,
                   String wellID,
                   String wellAddress,
                   String totalDepth,
                   String waterLevel,
                   String casingType,
                   String casingHeight,
                   String sleeveType,
                   String sleeveHeight,
                   String screenDepth,
                   String annularCementDepth,
                   String annularCementVerified,
                   String padDimensions,
                   String flowTestGPM,
                   String flowTestMethodTime,
                   Boolean sanitaryWellSeal,
                   String septicDistance,
                   String propertyLineDistance,
                   String nearestWellDistance,
                   String aerobicSprayAreaDistance,
                   String septicLateralLinesDistance,
                   String otherContaminationSourcesDistance)
    {
        WellID = wellID;
        WellName = wellName;
        WellAddress = wellAddress;
        CasingType = casingType;
        FlowTestGPM = flowTestGPM;
        CasingHeight = casingHeight;
        SleeveHeight = sleeveHeight;
        PadDimensions = padDimensions;
        ScreenDepth = screenDepth;
        SleeveType = sleeveType;
        AnnularCementDepth = annularCementDepth;
        WaterLevel = waterLevel;
        TotalDepth = totalDepth;
        AnnularCementVerified = annularCementVerified;
        FlowTestMethodTime = flowTestMethodTime;
        SanitaryWellSeal = sanitaryWellSeal;
        SepticDistance = septicDistance;
        PropertyLineDistance = propertyLineDistance;
        NearestWellDistance = nearestWellDistance;
        AerobicSprayAreaDistance = aerobicSprayAreaDistance;
        SepticLateralLinesDistance = septicLateralLinesDistance;
        OtherContaminationSourcesDistance = otherContaminationSourcesDistance;
    }

}

