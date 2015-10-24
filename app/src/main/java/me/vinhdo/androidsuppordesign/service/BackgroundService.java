package me.vinhdo.androidsuppordesign.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by vinh on 8/27/15.
 */
public class BackgroundService extends Service{

    private BGBinder mIntentBinder = new BGBinder();

    private final Handler handler = new Handler();
    private final Runnable refresher = new Runnable() {
        public void run() {
            // some action
            Log.d("Refresh","after 5s");
            handler.postDelayed(this, 5 * 1000);
        }
    };

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(action == null) return;
                if(action.equals(Intent.ACTION_PACKAGE_CHANGED)){
                    Log.d("Package", "Change");
                }
            }
        }, filter);
        listenerOpenApp();
        handler.postDelayed(refresher, 5);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIntentBinder;
    }

    // Music binder to bound activity with service
    public class BGBinder extends Binder {
        public BackgroundService getService() {

            // return service object
            return BackgroundService.this;
        }
    }

    private void listenerOpenApp(){
        try
        {
            Process mLogcatProc = null;
            BufferedReader reader = null;
            mLogcatProc = Runtime.getRuntime().exec(new String[]{"logcat", "-d"});

            reader = new BufferedReader(new InputStreamReader(mLogcatProc.getInputStream()));

            String line;
            final StringBuilder log = new StringBuilder();
            String separator = System.getProperty("line.separator");

            while ((line = reader.readLine()) != null)
            {
                log.append(line);
                log.append(separator);
            }
            String w = log.toString();
            Toast.makeText(getApplicationContext(), w, Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
