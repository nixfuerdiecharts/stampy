package com.example.stampy.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.preference.PreferenceManager;

import android.app.ActivityManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.stampy.R;
import com.example.stampy.broadcast.StampTimerBroadcastListener;
import com.example.stampy.broadcast.StampTimerBroadcastReceiver;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, StampTimerBroadcastListener {

    private SharedPreferences sp;
    private AppCompatButton btn_stamp;
    private AppCompatImageButton btn_edit_clock_in;

    private StampTimerBroadcastReceiver broadcastReceiver;

    private boolean stampedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        stampedIn = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //TODO: unregister BroadcastReceiver without crashing app
        //unregisterBroadcastReceiver();
    }

    private void registerBroadcastReceiver() {
        unregisterBroadcastReceiver();
        broadcastReceiver = new StampTimerBroadcastReceiver(this);
        //TODO: implement getIntentFilter()
        //this.registerReceiver(broadcastReceiver, StampTimerBroadcastReceiver.getIntentFilter());
    }

    private void unregisterBroadcastReceiver() {
        if (broadcastReceiver != null) {
            this.unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

    private void init() {
        setContentView(R.layout.activity_main);
        //Set Default Values of Shared Preferences
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);

        // Button OnCLickListener
        btn_stamp = findViewById(R.id.btn_stamp);
        btn_stamp.setOnClickListener(this);
        btn_edit_clock_in = findViewById(R.id.btn_edit_clock_in);
        btn_edit_clock_in.setOnClickListener(this);

        //Init timer
        TextView tv_time_min = findViewById(R.id.tv_time_min);
        TextView tv_time_max = findViewById(R.id.tv_time_max);
        updateTimerValue(0, tv_time_min);
        updateTimerValue(0, tv_time_max);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_stamp:
                stampedPressed();
                break;
            case R.id.btn_edit_clock_in:
                editStampTimePressed();
            default://TODO: unhandled
        }
    }

    private void editStampTimePressed() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                //TODO: Handle
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void stampedPressed() {
        //TODO: Implement TimerService
        //Intent intent = new Intent(this, EggTimerService.class);
        //intent.putExtra(EggTimerService.EGG_ORDER_EXTRA_KEY, order);
        //startService(intent);

        // TODO: Make Following situational, only execute if currently clocked out
        // isMyServiceRunning(ServiceName.class) in IF-Statement
        if(stampedIn) {
            stampedIn = !stampedIn;
            // Swap stamp Button
            btn_stamp.setText(R.string.btn_clock_in);
            // Hide Edit Button
            btn_edit_clock_in.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0
            ));
            btn_edit_clock_in.setVisibility(View.GONE);

        } else {
            stampedIn = !stampedIn;
            // Swap stamp Button
            btn_stamp.setText(R.string.btn_clock_out);
            // Show Edit Button
            btn_edit_clock_in.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1
            ));
            btn_edit_clock_in.setVisibility(View.VISIBLE);
        }


    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void updateTimerValue(int remainingSeconds, TextView tv) {
        DecimalFormat df = new DecimalFormat("00");
        int hours = remainingSeconds / 3600;
        int minutes = remainingSeconds % 3600;
        tv.setText(df.format(hours) + "h" + ":" + df.format(minutes) + "min");
    }

    @Override
    public void onTimerUpdate(int remainingTimeInMinutes) {

    }

    @Override
    public void onTimerFinished() {

    }
}