package com.example.wellinformed2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

//Activity displays a table with owner information to display all owners in firebase
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
        myOwnerRef = database.getReference();

        scrollView = findViewById(R.id.owner_scroll_view);
        displayOwnerTable(myOwnerRef);
    }

    //When this activity starts this is initiated to create an options menu in the
    //the activity window in the top right
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    //Gives directions to each button to do if selected
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_dashboard:
                startActivity(new Intent(this, wellIndex.class));
                return true;
            case R.id.nav_signout:
                startActivity(new Intent(this, ownerIndex.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Gets owner information from firebase and stores in a array list
    public void displayOwnerTable(DatabaseReference mOwnerRef) {
        table = new TableLayout(this);
        table.setStretchAllColumns(true);

        List<Owner> ownerList = new ArrayList<>();
        ownerList.clear();
        mOwnerRef = mOwnerRef.child("Owner");
        mOwnerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Owner owner = postSnapshot.getValue(Owner.class);
                    ownerList.add(owner);
                }

                addOwnersToTable(ownerList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //adds owner information to table to be displays
    private void addOwnersToTable(List<Owner> ownerList) {

        for (int i = 0; i < ownerList.size(); i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 30);

            row.setLayoutParams(lp);

            TextView name = new TextView(this);
            name.setText(ownerList.get(i).Name);
            name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            name.setTextSize(20);


            TextView city = new TextView(this);
            city.setText(ownerList.get(i).City);
            city.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            city.setTextSize(20);


            TextView state = new TextView(this);
            state.setText(ownerList.get(i).State);
            state.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            state.setTextSize(20);


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
}


//owner object that store data of a owner
class Owner
{
    public String Name;
    public String StreetAddress;
    public String City;
    public String State;

    Owner(){ };

    Owner(String name, String streetAddress, String city, String state)
    {
        Name = name;
        StreetAddress = streetAddress;
        City = city;
        State = state;
    }

    Owner(Map<String, Object> map)
    {
        Name = (String)map.get("Name");
        StreetAddress = (String)map.get("Street Address");
        City = (String)map.get("City");
        State = (String)map.get("State");
    }

    @Exclude
    public Map<String, Object> ToMap()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("Name", Name);
        map.put("Street Address", StreetAddress);
        map.put("City", City);
        map.put("State", State);
        return map;
    }
}