package com.example.wellinformed2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//drillerIndex activity displays all the current drillers in the database
public class drillerIndex extends AppCompatActivity
{

    TableLayout table;
    ScrollView scrollView;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driller_index);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        scrollView = findViewById(R.id.driller_scroll_view);
        displayDrillerTable(myRef);
    }

    //When this activity starts this is initiated to create an options menu in the
    //the activity window in the top right
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    //Gives directions to each button to do if selected
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.nav_dashboard:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.nav_signout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Gets the drillers information from the database
    public void displayDrillerTable(DatabaseReference mDrillerRef)
    {
        table = new TableLayout(this);
        table.setStretchAllColumns(true);

        mDrillerRef = mDrillerRef.child("Driller");
        final List<Driller> drillerIndexList = new ArrayList<>();
        drillerIndexList.clear();

        mDrillerRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Driller driller = postSnapshot.getValue(Driller.class);
                    drillerIndexList.add(driller);
                }

                addDrillerToTable(drillerIndexList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                System.out.println("The read failed: " + FirebaseError.ERROR_INTERNAL_ERROR);
            }
        });
    }

    //Adds a driller information to the table to be displayed
    private void addDrillerToTable(List<Driller> drillerIndexList)
    {

        for (int i = 0; i < drillerIndexList.size(); i++)
        {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 30);

            row.setLayoutParams(lp);

            TextView name = new TextView(this);
            name.setText(drillerIndexList.get(i).Name);
            name.setTextSize(20);


            TextView companyName = new TextView(this);
            companyName.setText(drillerIndexList.get(i).CompanyName.substring(0, Math.min(drillerIndexList.get(i).CompanyName.length(), 7))+"...");
            companyName.setTextSize(20);


            TextView licNum = new TextView(this);
            licNum.setText(String.valueOf(drillerIndexList.get(i).LicenseNumber));
            licNum.setTextSize(20);


            row.addView(name);
            row.addView(companyName);
            row.addView(licNum);
            if(i%2 ==0)
            {
                row.setBackgroundColor(getResources().getColor(R.color.evenRowBackground));
            }
            table.addView(row, i);
        }
        scrollView.addView(table);

    }
}


//Driller class creates an object that stores the details of a driller such as
//name, company name, license number, and license expiration date
class Driller
{
    public String Name;
    public String CompanyName;
    public int LicenseNumber = 0;
    public String LicenseExpirationDate;

    Driller(){}

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

