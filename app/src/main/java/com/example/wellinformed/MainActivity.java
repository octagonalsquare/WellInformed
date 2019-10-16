package com.example.wellinformed;

import android.media.Image;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private AppBarConfiguration mAppBarConfiguration;
    TableLayout table;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        scrollView = (ScrollView) findViewById(R.id.well_scroll_view);

        table = new TableLayout(this);
        displayWellTable();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void displayWellTable()
    {
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

        for (int i = 0; i < wellList.size(); i++)
        {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT);
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
            table.addView(row,i);
        }
        scrollView.addView(table);
    }

}

class Well
{
    public String ID;
    public String Name;
    public String Status;
    public String More;

    Well(String id, String name, String status, String more)
    {
        ID = id;
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

