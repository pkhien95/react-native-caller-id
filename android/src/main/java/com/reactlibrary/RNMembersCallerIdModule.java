
package com.reactlibrary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.utils.Constants;

public class RNMembersCallerIdModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public RNMembersCallerIdModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNCallerId";
    }

    @ReactMethod
    public void setCallerList(ReadableArray callerList) {
        try {
            SharedPreferences sharedPreferences = getReactApplicationContext().getSharedPreferences(Constants.CALLER_PREF_KEY, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            for (int i = 0; i < callerList.size(); i++) {
                ReadableMap caller = callerList.getMap(i);
                Log.d("CALLER_ID", "Caller: " + String.valueOf(caller));
                if (caller.hasKey("name") && caller.hasKey("numbers")) {
                    String callerName = caller.getString("name");
                    ReadableArray callerNumbers = caller.getArray("numbers");
                    for (int j = 0; j < callerNumbers.size(); j++) {
                        String phoneNumber = callerNumbers.getString(j);
                        editor.putString("+" + phoneNumber, callerName);
                    }
                }
            }
            editor.apply();
        } catch (Exception e) {
            Log.e("CALLER_ID", e.getLocalizedMessage());
        }

    }

    @ReactMethod
    public void setAppName(String appName) {
        try {
            SharedPreferences sharedPreferences = getReactApplicationContext().getSharedPreferences(Constants.APP_NAME_PREF_KEY, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.putString(Constants.APP_NAME_PREF_KEY, appName);
            editor.apply();
        } catch (Exception e) {
            Log.e("CALLER_ID", e.getLocalizedMessage());
        }
    }

    private boolean isSystemAlertPermissionGranted(Context context) {
        return Settings.canDrawOverlays(context);
    }

    @ReactMethod
    public void requestOverlayPermission() {
        if (!isSystemAlertPermissionGranted(getReactApplicationContext())) {
            final String packageName = getReactApplicationContext() == null ? getReactApplicationContext().getPackageName() : getReactApplicationContext().getPackageName();
            final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + packageName));
            getReactApplicationContext().startActivityForResult(intent, 1000, null);
        }
    }

}