package com.example.wellinformed2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class drillerIndex extends AppCompatActivity {

    TableLayout table;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driller_index);

        scrollView = findViewById(R.id.well_scroll_view);
        displayWellTable();
    }

    public void displayWellTable() {
        table = new TableLayout(this);
        table.setStretchAllColumns(true);

        List<Well> wellList = new ArrayList<>();

        wellList.add(new Well("1", "Well 1", "Active", "..."));
        wellList.add(new Well("2", "Well 2", "Active", "..."));
        wellList.add(new Well("3", "Well 3", "Active", "..."));
        wellList.add(new Well("4", "Well 4", "Active", "..."));
        wellList.add(new Well("5", "Well 5", "Active", "..."));
        wellList.add(new Well("6", "Well 6", "Active", "..."));
        wellList.add(new Well("7", "Well 7", "Active", "..."));
        wellList.add(new Well("8", "Well 8", "Active", "..."));
        wellList.add(new Well("9", "Well 9", "Active", "..."));
        wellList.add(new Well("10", "Well 10", "Active", "..."));


        for (int i = 0; i < wellList.size(); i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT);
            row.setLayoutParams(lp);

            TextView id = new TextView(this);
            id.setText(wellList.get(i).ID);

            TextView name = new TextView(this);
            name.setText(wellList.get(i).Name);

            TextView status = new TextView(this);
            status.setText(wellList.get(i).Status);

            TextView more = new TextView(this);
            more.setText(wellList.get(i).More);

            row.addView(id);
            row.addView(name);
            row.addView(status);
            row.addView(more);
            table.addView(row, i);
        }
        scrollView.addView(table);
    }
}



