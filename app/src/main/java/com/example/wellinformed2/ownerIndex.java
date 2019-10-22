package com.example.wellinformed2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ownerIndex extends AppCompatActivity {

    TableLayout table;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_index);

        scrollView = findViewById(R.id.owner_scroll_view);
        displayOwnerTable();
    }

    public void displayOwnerTable() {
        table = new TableLayout(this);
        table.setStretchAllColumns(true);

        List<ownerIndex.Owner> ownerList = new ArrayList<>();

        ownerList.add(new ownerIndex.Owner("CJonesDigging","Palestine", "Texas"));

        ownerList.add(new ownerIndex.Owner("Israel", "Jerusalem", "NA"));

        ownerList.add(new ownerIndex.Owner( "Well Dig It", "Howe", "Texas"));

        ownerList.add(new ownerIndex.Owner( "Well Informed", "Tyler", "Texas"));


        for (int i = 0; i < ownerList.size(); i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT);

            row.setLayoutParams(lp);

            TextView name = new TextView(this);
            name.setText(ownerList.get(i).Name);

            TextView city = new TextView(this);
            city.setText(ownerList.get(i).City);

            TextView state = new TextView(this);
            state.setText(ownerList.get(i).State);

            row.addView(name);
            row.addView(city);
            row.addView(state);
            if(i%2 ==0)
            {
                row.setBackgroundColor(getResources().getColor(R.color.evenRowBackground));
            }
            table.addView(row, i);
        }
        scrollView.addView(table);
    }

    class Owner
    {
        public String Name;
        public String City;
        public String State;

        Owner(String name, String city, String state)
        {
            Name = name;
            City = city;
            State = state;
        }
    }
}
