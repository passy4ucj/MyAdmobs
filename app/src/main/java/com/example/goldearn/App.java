package com.example.goldearn;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.MobileAds;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class App extends Application implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = App.class.getSimpleName();
    /**
     * Keeps a reference of the application context
     */
    @SuppressLint("StaticFieldLeak")
    private static Context sContext;
    @SuppressLint("StaticFieldLeak")
    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        sInstance = this;
        registerActivityLifecycleCallbacks(sInstance);

        if (!BuildConfig.DEBUG) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            // Initialize Live Banner Ads immediately SonsHub Mobile is opened
            MobileAds.initialize(sInstance, getResources().getString(R.string.admob_app_id));
            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    Method m = StrictMode.class.getMethod(getResources().getString(R.string.runtimeStrictMode));
                    m.invoke(null);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            // Initialize Sample Banner Ads immediately SonsHub Mobile is opened
            MobileAds.initialize(sInstance, getResources().getString(R.string.sample_admob_app_id));
        }
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(1));
    }

    /**
     * Returns the application context
     *
     * @return application context
     */
    public static Context getContext() {
        return sContext;
    }

    public static App getInstance() {
        return sInstance;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
    }
}
