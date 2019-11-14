package com.example.wellinformed2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.esri.arcgisruntime.symbology.TextSymbol;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;

public class wellIndex extends AppCompatActivity implements View.OnClickListener {

    TableLayout table;
    ScrollView scrollView;
    TextView more;
    FirebaseDatabase database;
    DatabaseReference myRef;
    List<Well> wellIndexList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_index);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        scrollView = findViewById(R.id.well_scroll_view);
        displayWellTable(myRef);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.well_index_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_well:
                startActivity(new Intent(this,AddWellActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void displayWellTable(DatabaseReference mWellRef) {
        table = new TableLayout(this);
        table.setStretchAllColumns(true);

        mWellRef = mWellRef.child("Well");
        wellIndexList = new ArrayList<>();
        wellIndexList.clear();

        mWellRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()) {
                    Well well = postSnapshot.getValue(Well.class);
                    well.AddID(postSnapshot.getKey());
                    wellIndexList.add(well);
                }

                addWellToTable(wellIndexList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + FirebaseError.ERROR_INTERNAL_ERROR);
            }
        });


    }

    private void addWellToTable(List<Well> wellIndexList) {

        for (int i = 0; i < wellIndexList.size(); i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(FILL_PARENT, FILL_PARENT);
            row.setLayoutParams(lp);

            TextView id = new TextView(this);
            String ID = wellIndexList.get(i).ID;
            ID = ID.substring(0, Math.min(ID.length(), 3));
            id.setText(ID + "...");
            id.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            TextView name = new TextView(this);
            name.setText(wellIndexList.get(i).Name);
            name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            TextView status = new TextView(this);
            status.setText(wellIndexList.get(i).Status);
            status.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


            more = new TextView(this);
            //more.setText(wellList.get(i).More);
            //more.getCompoundDrawables(getResources().getDrawable(R.drawable.));
            more.setBackground(getResources().getDrawable(R.drawable.ic_more_horiz_black_24dp));
            //more.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            //more.setGravity(Gravity.CENTER_VERTICAL);
            lp.gravity = Gravity.CENTER;
            //lp.width=2;
            //lp.height=70;
            //lp.setMargins(3,10,3,1);
            more.setLayoutParams(lp);
            more.setId(i);

            more.setOnClickListener(this);

            row.addView(name);
            row.addView(status);
            row.addView(more);
            if(i%2==0)
            {
                row.setBackgroundColor(getResources().getColor(R.color.evenRowBackground));
            }
            table.addView(row, i);
        }
        scrollView.addView(table);


    }


    @Override
    public void onClick(View view)
    {
        Well selectedWell = wellIndexList.get(view.getId());
        Intent i = new Intent(this, wellDetails.class);
        i.putExtra("Selected", selectedWell);
        i.putExtra("Selected ID", "ID: " + selectedWell.ID);
        startActivity(i);
    }
}

class Well implements Serializable
{
    public String Name;
    public String Status;
    public String Address;
    public String Latitude;
    public String Longitude;
    public String Type;
    public String Owner;
    public String Date;
    public String ID;

    Well(){ }

    Well(String name, String latitude, String longitude, String status, String address, String type, String owner, String date)
    {
        Name = name;
        Latitude = latitude;
        Longitude = longitude;
        Status = status;
        Address = address;
        Type = type;
        Owner = owner;
        Date = date;
    }

    Well(Map<String, Object> map)
    {
        Name = (String)map.get("Name");
        Status = (String)map.get("Statu");
        Address = (String)map.get("Address");
        Latitude = (String)map.get("Latitude");
        Longitude = (String)map.get("Longitude");
        Type = (String)map.get("Type");
        Owner = (String)map.get("Owner");
        Date = (String)map.get("Date");
    }

    public void AddID(String Id)
    {
        ID = Id;
    }

    @Exclude
    public Map<String, Object> ToMap()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("Name", Name);
        map.put("Status", Status);
        map.put("Address", Address);
        map.put("Latitude", Latitude);
        map.put("Longitude", Longitude);
        map.put("Type", Type);
        map.put("Owner", Owner);
        map.put("Date", Date);
        return map;
    }
}
