package com.reactlibrary.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reactlibrary.R;
import com.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.WINDOW_SERVICE;

public class CallReceiver extends BroadcastReceiver {
    private static boolean isShowingOverlay = false;
    private static LinearLayout overlay;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        Log.d("CALLER_ID", state);
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            if (!isShowingOverlay) {
                isShowingOverlay = true;
                String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                String callerName = getCallerName(context, phoneNumber);
                if (callerName != null) {
                    showCallerInfo(context, callerName);
                }
            }
        } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK) || state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            if (isShowingOverlay) {
                isShowingOverlay = false;
                dismissCallerInfo(context);
            }
        }
    }

    private void showCallerInfo(final Context context, final String callerName) {
        Log.d("CALLER_ID", "Show Caller info of: " + callerName);
        String appName = "";
        SharedPreferences pref = context.getSharedPreferences(Constants.APP_NAME_PREF_KEY, 0);
        if (pref.contains(Constants.APP_NAME_PREF_KEY)) {
            appName = pref.getString(Constants.APP_NAME_PREF_KEY, "");
        }
        final String finalAppName = appName;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
                if (overlay == null) {
                    LayoutInflater inflater = LayoutInflater.from(context);
                    overlay = (LinearLayout) inflater.inflate(R.layout.caller_info_dialog, null);
                    Button closeButton = overlay.findViewById(R.id.close_btn);
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isShowingOverlay = false;
                            dismissCallerInfo(context);
                        }
                    });

                    // Set app name
                    TextView tvAppName = overlay.findViewById(R.id.appName);
                    tvAppName.setText(finalAppName);

                    // Set caller name
                    TextView tvCallerName = overlay.findViewById(R.id.callerName);
                    tvCallerName.setText(callerName);
                }

                int typeParam = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE;

                WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        typeParam,
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT);
                windowManager.addView(overlay, params);
            }
        }, 1000);
    }

    private void dismissCallerInfo(final Context context) {
        if (overlay != null) {
            WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
            if (windowManager != null) {
                windowManager.removeView(overlay);
                overlay = null;
            }
        }
    }

    private String getCallerName(final Context context, String phoneNumber) {
        Log.d("CALLER_ID", "Get Caller name of  phoneNumber: " + phoneNumber);
        SharedPreferences pref = context.getSharedPreferences(Constants.CALLER_PREF_KEY, 0);
        if (pref.contains(phoneNumber)) {
            return pref.getString(phoneNumber, null);
        } else {
            List<String> phoneNumberList = new ArrayList<>(pref.getAll().keySet());
            for (String n : phoneNumberList) {
                if (PhoneNumberUtils.compare(context, phoneNumber, n)) {
                    return pref.getString(n, null);
                }
            }
        }
        return null;
    }

}