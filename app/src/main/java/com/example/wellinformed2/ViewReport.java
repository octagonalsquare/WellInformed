package com.example.wellinformed2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ViewReport extends AppCompatActivity
{
    private WellInspection report;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        Bundle extras = getIntent().getExtras();
        report = (WellInspection)extras.get("Selected Report");

    }

}
