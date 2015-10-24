package me.vinhdo.androidsuppordesign;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

import me.vinhdo.androidsuppordesign.models.HDVConfig;

/**
 * Created by vinh on 10/8/15.
 */
public class AppApplication extends Application{
    private static HDVConfig hdvConfig;
    public static HDVConfig getHdvConfig(){
        return hdvConfig;
    }
    public static void setHdvConfig(HDVConfig config){
        hdvConfig = config;
    }

    private static AppApplication instance;
    public static AppApplication get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static void setToken(String token){
        PreferenceManager.getDefaultSharedPreferences(instance).edit().putString("token",token).commit();
    }

    public static String getToken(){
        return PreferenceManager.getDefaultSharedPreferences(instance).getString("token", "");
    }
}
