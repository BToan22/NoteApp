package com.example.noteapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
public class LoginBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        CharSequence iData = intent.getCharSequenceExtra("msg");
        Toast.makeText(context,"Log in Successful Message: Welcome "+iData,Toast.LENGTH_LONG).show();
    }
}