package com.example.mihaelasolomon.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class rectest extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("broadcast")) {
            Toast.makeText(context, intent.getStringExtra("user"), Toast.LENGTH_SHORT).show();
        }
    }
}
