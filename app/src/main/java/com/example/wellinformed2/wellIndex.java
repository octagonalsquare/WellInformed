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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_index);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        scrollView = findViewById(R.id.well_scroll_view);
        //displayWellTable();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_well:
                startActivity(new Intent(this,AddWellActivity.class));
                return true;
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
            case R.id.nav_well_details:
                startActivity(new Intent(this,wellDetails.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void displayWellTable() {
        table = new TableLayout(this);
        table.setStretchAllColumns(true);

        List<Well> wellList = new ArrayList<>();

        for (int i = 1; i < 21; i++)
        {
            myRef.child("Well").child(Integer.toString(i)).setValue(wellList.get(i).toMap());
        }

        for (int i = 0; i < wellList.size(); i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(FILL_PARENT, FILL_PARENT);
            row.setLayoutParams(lp);
            /*TextView id = new TextView(this);
            id.setText(wellList.get(i).ID);
            id.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);*/

            TextView name = new TextView(this);
            name.setText(wellList.get(i).Name);
            name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            TextView status = new TextView(this);
            status.setText(wellList.get(i).Status);
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
    public void onClick(View view) {
            startActivity(new Intent(this,wellDetails.class));
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

    Well(String name, String latitude, String longitude,String status, String address, String type, String owner, String date)
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

    @Exclude
    public Map<String, Object> toMap()
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
