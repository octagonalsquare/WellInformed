package com.example.wellinformed2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class wellInspection extends AppCompatActivity implements View.OnClickListener
{
    TextView submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_inspection);

        submitButton = findViewById(R.id.WellInspectionSubmit);

        submitButton.setOnClickListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.nav_wellIndex:
                startActivity(new Intent(this, wellIndex.class));
                return true;
            case R.id.nav_wellOwner:
                startActivity(new Intent(this, ownerIndex.class));
                return true;
            case R.id.nav_wellDriller:
                startActivity(new Intent(this,drillerIndex.class));
                return true;
            case R.id.nav_navigation:
                startActivity(new Intent(this,navigation.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view)
    {
        if(view == submitButton)
        {
            createInspection();
        }
    }

    public void createInspection()
    {
        EditText edit = findViewById(R.id.WellInspectionWellName);
        Editable editable = edit.getText();
        String WellName = editable.toString();

        edit =  findViewById(R.id.WellInspectionWellID);
        editable = edit.getText();
        String WellID = editable.toString();

        edit =  findViewById(R.id.WellInspectionWellAddress);
        editable = edit.getText();
        String WellAddress = editable.toString();

        edit =  findViewById(R.id.WellInspectionTotalDepth);
        editable = edit.getText();
        String TotalDepth = editable.toString();

        edit =  findViewById(R.id.WellInspectionWaterLevel);
        editable = edit.getText();
        String WaterLevel = editable.toString();

        edit =  findViewById(R.id.WellInspectionCasingType);
        editable = edit.getText();
        String CasingType = editable.toString();

        edit =  findViewById(R.id.WellInspectionCasingHeight);
        editable = edit.getText();
        String CasingHeight = editable.toString();

        edit =  findViewById(R.id.WellInspectionSleeveType);
        editable = edit.getText();
        String SleeveType = editable.toString();

        edit =  findViewById(R.id.WellInspectionSleeveHeight);
        editable = edit.getText();
        String SleeveHeight = editable.toString();

        edit =  findViewById(R.id.WellInspectionScreenDepth);
        editable = edit.getText();
        String ScreenDepth = editable.toString();

        edit =  findViewById(R.id.WellInspectionAnnularCementDepth);
        editable = edit.getText();
        String AnnularCementDepth = editable.toString();

        RadioButton radio = findViewById(R.id.WellInspectionAnnularCementVerified);
        Boolean AnnularCementVerified = radio.isSelected();

        edit =  findViewById(R.id.WellInspectionPadDimensions);
        editable = edit.getText();
        String PadDimensions = editable.toString();

        edit =  findViewById(R.id.WellInspectionFlowTestGPM);
        editable = edit.getText();
        String FlowTestGPM = editable.toString();

        edit =  findViewById(R.id.WellInspectionFlowTestMethodTime);
        editable = edit.getText();
        String FlowTestMethodTime = editable.toString();

        radio = findViewById(R.id.WellInspectionSanitaryWellSeal);
        Boolean SanitaryWellSeal = radio.isSelected();

        edit =  findViewById(R.id.WellInspectionSepticDistance);
        editable = edit.getText();
        String SepticDistance = editable.toString();

        edit =  findViewById(R.id.WellInspectionPropertyLineDistance);
        editable = edit.getText();
        String PropertyLineDistance = editable.toString();

        edit =  findViewById(R.id.WellInspectionNearestWellDistance);
        editable = edit.getText();
        String NearestWellDistance = editable.toString();

        edit =  findViewById(R.id.WellInspectionAerobicSprayAreaDistance);
        editable = edit.getText();
        String AerobicSprayAreaDistance = editable.toString();

        edit =  findViewById(R.id.WellInspectionSepticLateralLinesDistance);
        editable = edit.getText();
        String SepticLateralLinesDistance = editable.toString();

        edit =  findViewById(R.id.WellInspectionOtherContaminationSourcesDistance);
        editable = edit.getText();
        String OtherContaminationSourcesDistance = editable.toString();
        
        WellInspection report = new WellInspection(WellName, WellID, WellAddress, TotalDepth,
                WaterLevel, CasingType, CasingHeight, SleeveType, SleeveHeight, ScreenDepth,
                AnnularCementDepth, AnnularCementVerified, PadDimensions, FlowTestGPM,
                FlowTestMethodTime, SanitaryWellSeal, SepticDistance, PropertyLineDistance,
                NearestWellDistance, AerobicSprayAreaDistance, SepticLateralLinesDistance,
                OtherContaminationSourcesDistance);
    }
    
    class WellInspection
    {
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
        public  Boolean AnnularCementVerified;
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
                Boolean annularCementVerified,
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
}
