package com.example.wellinformed2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class drillerIndex extends AppCompatActivity {

    TableLayout table;
    ScrollView scrollView;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driller_index);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        scrollView = findViewById(R.id.driller_scroll_view);
        displayDrillerTable();
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

    public void displayDrillerTable() {
        table = new TableLayout(this);
        table.setStretchAllColumns(true);

        List<Driller> drillerList = new ArrayList<>();

        /*myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Driller newDriller = dataSnapshot.getValue(Driller.class);
                drillerList.add(newDriller);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                System.out.println("Database Error:" + databaseError.getMessage());
            }
        });*/

        drillerList.add(new Driller("Craig", "CJonesDigging"));

        drillerList.add(new Driller("John", "CJonesDigging"));

        drillerList.add(new Driller("Josh","Well Dig It"));

        drillerList.add(new Driller("Caleb", "Well Informed"));

        drillerList.add(new Driller("Yash", "Well Informed"));

        drillerList.add(new Driller("Jesus","Well Informed"));

        drillerList.add(new Driller("Will", "Well Informed"));

        drillerList.add(new Driller("Bill","Well Dig It"));

        /*for (int i = 0; i < drillerList.size(); i++)
        {
            myRef.child("Driller").child(Integer.toString(i)).setValue(drillerList.get(i).ToMap());
        }*/


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
}

class Driller
{
    public String Name;
    public String CompanyName;
    public int LicenseNumber = 0;
    public String LicenseExpirationDate;

    Driller(String name, String companyName, int licenseNumber,
            String licenseExpirationDate)
    {
        Name = name;
        CompanyName = companyName;
        LicenseNumber = licenseNumber;
        LicenseExpirationDate = licenseExpirationDate;
    }

    Driller(String name, String companyName)
    {
        Name = name;
        CompanyName = companyName;
    }

    Driller(Map<String, Object> map)
    {
        Name = (String)map.get("Name");
        CompanyName = (String)map.get("CompanyName");
        LicenseNumber = (int)map.get("LicenseNumber");
        LicenseExpirationDate = (String) map.get("LicenseExpirationDate");
    }
    public void RenewLicense(int licenseNumber, String licenseExpirationDate)
    {
        LicenseNumber = licenseNumber;
        LicenseExpirationDate = licenseExpirationDate;
    }

    @Exclude
    public Map<String, Object> ToMap()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("Name", Name);
        map.put("CompanyName", CompanyName);
        map.put("LicenseNumber", LicenseNumber);
        map.put("LicenseExpirationDate", LicenseExpirationDate);
        return map;
    }
}

