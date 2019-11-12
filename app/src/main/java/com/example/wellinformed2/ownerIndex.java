package com.example.wellinformed2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ownerIndex extends AppCompatActivity {

    TableLayout table;
    ScrollView scrollView;
    FirebaseDatabase database;
    DatabaseReference myOwnerRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_index);
        database = FirebaseDatabase.getInstance();
        myOwnerRef = database.getReference().child("Owner");

        scrollView = findViewById(R.id.owner_scroll_view);
        displayOwnerTable();
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

    public void displayOwnerTable() {
        table = new TableLayout(this);
        table.setStretchAllColumns(true);

        List<Owner> ownerList = new ArrayList<>();

        myOwnerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Owner owner = postSnapshot.getValue(Owner.class);
                    ownerList.add(owner);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

        Owner(Map<String, Object> map)
        {
            Name = (String)map.get("Name");
            City = (String)map.get("City");
            State = (String)map.get("State");
        }

        public Map<String, Object> ToMap()
        {
            Map<String, Object> map = new HashMap<>();
            map.put("Name", Name);
            map.put("City", City);
            map.put("State", State);
            return map;
        }
    }
}
