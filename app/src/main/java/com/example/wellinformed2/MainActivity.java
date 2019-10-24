package com.example.wellinformed2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //define TextView variable
    TextView txvWellIndex;
    TextView txvWellOwner;
    TextView txvDrillerIndex;
    TextView txvNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize TextView variable to layout
        txvWellIndex=findViewById(R.id.txvWellIndex);
        txvWellOwner=findViewById(R.id.txvWellOwner);
        txvDrillerIndex=findViewById(R.id.txvDrillerIndex);
        txvNavigation=findViewById(R.id.txvNavigation);

        //set click listener
        txvWellIndex.setOnClickListener(this);
        txvWellOwner.setOnClickListener(this);
        txvDrillerIndex.setOnClickListener(this);
        txvNavigation.setOnClickListener(this);
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
                startActivity(new Intent(this, drillerIndex.class));
                return true;
            case R.id.nav_navigation:
                startActivity(new Intent(this, navigation.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View view) {

        if(view==txvWellOwner){
            startActivity(new Intent(this, ownerIndex.class));
        }

        if(view==txvWellIndex){
            startActivity(new Intent(this, wellIndex.class));
        }

        if(view==txvDrillerIndex){
            startActivity(new Intent(this, drillerIndex.class));
        }
        if(view==txvNavigation){
            startActivity(new Intent(this, navigation.class));
        }
    }
}
