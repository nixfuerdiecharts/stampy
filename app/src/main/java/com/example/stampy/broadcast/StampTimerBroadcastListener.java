package com.example.stampy.broadcast;

public interface StampTimerBroadcastListener {

    void onTimerUpdate(int remainingTimeInMinutes);

    void onTimerFinished();

}
