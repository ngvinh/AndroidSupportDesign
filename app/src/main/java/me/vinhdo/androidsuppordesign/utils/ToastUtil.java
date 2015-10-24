package me.vinhdo.androidsuppordesign.utils;

import android.widget.Toast;

import me.vinhdo.androidsuppordesign.AppApplication;

/**
 * Created by vinh on 10/8/15.
 */
public class ToastUtil {
    private static Toast toast;

    public static void show(String text) {
        toast = Toast.makeText(AppApplication.get(), text, Toast.LENGTH_LONG);
        toast.show();
    }

    public static void show(int textId) {
        toast = Toast.makeText(AppApplication.get(), AppApplication.get().getString(textId), Toast.LENGTH_LONG);
        toast.show();
    }

    public static void dismiss() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
