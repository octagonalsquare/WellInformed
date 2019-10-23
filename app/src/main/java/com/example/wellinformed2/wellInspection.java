package com.example.wellinformed2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class wellInspection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_inspection);
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
}
