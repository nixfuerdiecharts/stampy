package com.example.stampy.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StampTimerBroadcastReceiver extends BroadcastReceiver {

    private StampTimerBroadcastListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public StampTimerBroadcastReceiver(StampTimerBroadcastListener listener) {
        this.listener = listener;
    }
}
