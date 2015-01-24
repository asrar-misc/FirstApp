package com.example.asrar.firstapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.lang.reflect.Method;

public class MyReceiver extends BroadcastReceiver {
    public static Context context_inner;

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        context_inner = context;
        try {
        // TELEPHONY MANAGER class object to register one listner
        TelephonyManager tmgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        //Create Listner
        MyPhoneStateListener PhoneListener = new MyPhoneStateListener();

        // Register listener for LISTEN_CALL_STATE
        tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        } catch (Exception e) {
        }
    }
}

class MyPhoneStateListener extends PhoneStateListener {

    public void onCallStateChanged(int state, String incomingNumber) {
        if (state == TelephonyManager.CALL_STATE_RINGING) {
            endCallIfBlocked(incomingNumber);
        }
    }

    private void endCallIfBlocked(String callingNumber) {
        Context context = MyReceiver.context_inner;
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(context);

        String block_number = prefs.getString("block_number", null);
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        //Turn ON the mute
        audioManager.setStreamMute(AudioManager.STREAM_RING, true);
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Class ClassTelephonyManager;
        try {
            ClassTelephonyManager = Class.forName(telephonyManager.getClass().getName());
            Method methodGetITelephony;
            methodGetITelephony = ClassTelephonyManager.getDeclaredMethod("getITelephony");
            methodGetITelephony.setAccessible(true);
            Class ClassITelephony;
            ClassITelephony = Class.forName(methodGetITelephony.invoke(telephonyManager).getClass().getName());

            Method methodEndCall;
            methodEndCall = ClassITelephony.getDeclaredMethod("endCall");
            methodEndCall.setAccessible(true);

            Object ended = methodEndCall.invoke(methodGetITelephony.invoke(telephonyManager));

            Toast.makeText(context, callingNumber+" is trying to call. . .", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            String execptMsg = e.toString();
            e.printStackTrace();
            Toast.makeText(context, execptMsg, Toast.LENGTH_LONG).show();
        }
        //Turn OFF the mute
        audioManager.setStreamMute(AudioManager.STREAM_RING, false);

    }
}
