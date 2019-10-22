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

public class drillerIndex extends AppCompatActivity {

    TableLayout table;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driller_index);

        scrollView = findViewById(R.id.driller_scroll_view);
        displayDrillerTable();
    }

    public void displayDrillerTable() {
        table = new TableLayout(this);
        table.setStretchAllColumns(true);

        List<Driller> drillerList = new ArrayList<>();

        drillerList.add(new Driller("Craig",   "CJonesDigging",    "Active",
                "123 Jones St"));

        drillerList.add(new Driller("John",    "CJonesDigging",    "Active",
                "123 Jones St"));

        drillerList.add(new Driller("Mahershalalhashbaz",  "Israel", "Active",
                "123 Jones St"));

        drillerList.add(new Driller("Josh",    "Well Dig It",      "Active",
                "123 Jones St"));

        drillerList.add(new Driller("Caleb",   "Well Informed",    "Active",
                "123 Jones St"));

        drillerList.add(new Driller("Yash",    "Well Informed",    "Active",
                "123 Jones St"));

        drillerList.add(new Driller("Jesus",   "Well Informed",    "Active",
                "123 Jones St"));

        drillerList.add(new Driller("Will",    "Well Informed",    "Active",
                "123 Jones St"));

        drillerList.add(new Driller("Bill",    "Well Dig It",      "Active",
                "123 Jones St"));

        drillerList.add(new Driller("The Dark Lord", "Well Dig It", "Active",
                "123 Jones St"));


        for (int i = 0; i < drillerList.size(); i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT);

            row.setLayoutParams(lp);

            TextView name = new TextView(this);
            name.setText(drillerList.get(i).Name);

            TextView companyName = new TextView(this);
            companyName.setText(drillerList.get(i).CompanyName);

            TextView more = new TextView(this);
            more.setText("...");

            row.addView(name);
            row.addView(companyName);
            row.addView(more);
            if(i%2 ==0)
            {
                row.setBackgroundColor(getResources().getColor(R.color.evenRowBackground));
            }
            table.addView(row, i);
        }
        scrollView.addView(table);
    }

    class Driller
    {
        public String Name;
        public String CompanyName;
        public String ID;
        public String Address;
        public int LicenseNumber = 0;
        public Date LicenseExpirationDate = new Date();

        Driller(String name, String companyName, String id, String address, int licenseNumber,
                Date licenseExpirationDate)
        {
            Name = name;
            CompanyName = companyName;
            ID = id;
            Address = address;
            LicenseNumber = licenseNumber;
            LicenseExpirationDate = licenseExpirationDate;
        }

        Driller(String name, String companyName, String id, String address)
        {
            Name = name;
            CompanyName = companyName;
            ID = id;
            Address = address;
        }

        public void RenewLicense(int licenseNumber, Date licenseExpirationDate)
        {
            LicenseNumber = licenseNumber;
            LicenseExpirationDate = licenseExpirationDate;
        }
    }
}



