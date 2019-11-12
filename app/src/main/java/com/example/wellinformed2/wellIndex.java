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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        setContentView(R.layout.activity_well_index);

        scrollView = findViewById(R.id.well_scroll_view);
        displayWellTable();
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

        /*myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Well newWell = dataSnapshot.getValue(Well.class);
                System.out.println("Name: " + newWell.Name);
                System.out.println("ID: " + prevChildKey);
                wellList.add(newWell);
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

        wellList.add(new Well("Well 2", "Active", "..."));
        wellList.add(new Well("Well 3", "Active", "..."));
        wellList.add(new Well("Well 4", "Active", "..."));
        wellList.add(new Well("Well 5", "Active", "..."));
        wellList.add(new Well("Well 6", "Active", "..."));
        wellList.add(new Well("Well 7", "Active", "..."));
        wellList.add(new Well("Well 8", "Active", "..."));
        wellList.add(new Well("Well 9", "Active", "..."));
        wellList.add(new Well("Well 10", "Active", "..."));

        for (int i = 11; i < 21; i++)
        {
            Well well = new Well("Well " + i, "Active", "...");
            myRef.child("Well").child(Integer.toString(i)).setValue(well);
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
    public String More;

    Well(String name, String status, String more)
    {
        Name = name;
        Status = status;
        More = more;
        //CreateButton();
    }



    /*private void CreateButton()
      {
          Button moreButton = new Button();
      }*/
}
