package com.example.wellinformed2;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

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
