package com.example.stampy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setContentView(R.layout.activity_main);
        //Set Default Values of Shared Preferences
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        //initSharedPreferences();
    }

    private void initSharedPreferences() {
        sp = getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (!sp.contains(getString(R.string.sp_hours_to_work))) {
            editor.putFloat(getString(R.string.sp_hours_to_work), (float) 7.8);
        }

        if (!sp.contains(getString(R.string.sp_max_work_hours))) {
            editor.putFloat(getString(R.string.sp_max_work_hours), (float) 10.45);
        }

        if (!sp.contains(getString(R.string.sp_break_time))) {
            editor.putFloat(getString(R.string.sp_break_time), (float) 0.5);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_settings:
                showSettings();
                break;
            default://TODO: Unhandled Menu Item
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSettings() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}