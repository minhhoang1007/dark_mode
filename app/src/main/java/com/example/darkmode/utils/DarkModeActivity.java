package com.example.darkmode.utils;

import android.annotation.TargetApi;
import android.app.UiModeManager;
import android.content.Context;
import android.os.Build;

public class DarkModeActivity {

    @TargetApi(Build.VERSION_CODES.FROYO)
    public static void changeUIMode(Context context, int mode) {
        Object systemService = context.getSystemService(Context.UI_MODE_SERVICE);
        if (systemService != null) {
            UiModeManager uiModeManager = (UiModeManager) systemService;
            uiModeManager.setNightMode(mode);
        }
    }
    public static int curentUiMode(Context context) {
        Object systemService = context.getSystemService(Context.UI_MODE_SERVICE);
        if (systemService != null) {
            UiModeManager uiModeManager = (UiModeManager) systemService;
            return  uiModeManager.getCurrentModeType();
        }
        return 1;
    }
}
