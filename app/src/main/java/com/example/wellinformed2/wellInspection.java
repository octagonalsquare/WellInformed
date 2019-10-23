package com.example.wellinformed2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class wellInspection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_inspection);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
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
    
    /*class WellInspection
    {
        public  String WellID;
        public  String WellName;
        public  String WellAddress;
        public  String CasingType;
        public  String FlowTestGPM;
        public  String CasingHeight;
        public  String SleeveHeight;
        public  String PadDimensions;
        public  String ScreenDepth;
        public  String SleeveType;
        public  String AnnularCementDepth;
        public  String WaterLevel;
        public  String TotalDepth;
        public  String AnnularCementVerified;
        public  String FlowTestMethodTime;
        public  String SanitaryWellSeal;
        public  String SepticDistance;
        public  String PropertyLineDistance;
        public  String NearestWellDistance;
        public  String AerobicSprayAreaDistance;
        public  String SepticLateralLinesDistance;
        public  String OtherContaminationSourcesDistance;
        
        WellInspection(String wellID, String wellName, String wellAddress, String casingType, 
                       String flowTestGPM, String casingHeight, String sleeveHeight,
                       String padDimensions, String screenDepth, String sleeveType, 
                       String annularCementDepth, String waterLevel, String totalDepth,
                       String annularCementVerified, String flowTestMethodTime,
                       String sanitaryWellSeal, String septicDistance, String propertyLineDistance,
                       String nearestWellDistance, String aerobicSprayAreaDistance,
                       String septicLateralLinesDistance, String otherContaminationSourcesDistance)
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
        
    }*/
}
