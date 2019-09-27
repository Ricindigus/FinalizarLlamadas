package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.android.internal.telephony.ITelephony;


import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class IncomingCallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        ITelephony telephonyService;
        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)){
                Toast.makeText(context, "Ring " + number, Toast.LENGTH_SHORT).show();
                Constraints constraints = new Constraints.Builder()
                        .build();

                OneTimeWorkRequest compressionWork1 =
                        new OneTimeWorkRequest.Builder(FinalizeWorker.class)
                                .setConstraints(constraints)
                                .setInitialDelay(15, TimeUnit.SECONDS)
                                .build();

                WorkManager.getInstance(context).enqueue(compressionWork1);
//                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//                try {
//                    Method m = tm.getClass().getDeclaredMethod("getITelephony");
//
//                    m.setAccessible(true);
//                    telephonyService = (ITelephony) m.invoke(tm);
//
//                    if ((number != null)) {
//                        telephonyService.endCall();
//                        Toast.makeText(context, "Ending the call from: " + number, Toast.LENGTH_SHORT).show();
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//


            }
            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                Toast.makeText(context, "Answered " + number, Toast.LENGTH_SHORT).show();

            }
            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)){
                Toast.makeText(context, "Idle "+ number, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
