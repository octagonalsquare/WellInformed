package com.example.wellinformed2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

//well details activity that displays the details of a selected well
public class wellDetails extends AppCompatActivity implements View.OnClickListener {

    private Well selectedWell;
    private String selectedWellID;
    private List<String> keys = new ArrayList<>();
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
    private Map<String, WellInspection> ReportList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_details);
        Bundle extras = getIntent().getExtras();
        selectedWell = (Well)extras.get("Selected");
        selectedWellID = extras.getString("Selected ID");
        ReportList = (Map<String, WellInspection>)extras.get("Well Reports");

        inspectionButton = findViewById(R.id.buttonDetailsStartInspection);
        wellDetailsScrollView = findViewById(R.id.well_details_scroll_view);
        inspectionButton.setOnClickListener(this);

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
        WellInspection report = ReportList.get(keys.get(view.getId()));
        Intent i = new Intent(this, ViewReport.class);
        i.putExtra("Selected Report", report);
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
        TableLayout table = findViewById(R.id.ReportListTable);
        table.removeAllViews();

        keys.addAll(ReportList.keySet());
        for (int i = 0; i < ReportList.size(); i++)
        {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(MATCH_PARENT, 30);
            row.setLayoutParams(lp);

            TextView report = new TextView(this);
            report.setText(keys.get(i));
            report.setLayoutParams(lp);
            report.setId(i);

            report.setOnClickListener(this::onReportClick);

            if(i%2==0)
            {
                row.setBackgroundColor(getResources().getColor(R.color.evenRowBackground));
            }

            row.addView(report);
            table.addView(row, i);
        }

    }

}
