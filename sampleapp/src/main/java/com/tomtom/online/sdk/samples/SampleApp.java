/**
 * Copyright (c) 2015-2019 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.tomtom.online.sdk.common.util.LogUtils;

import java.io.File;

public class SampleApp extends MultiDexApplication {

    private static final String ROBO_ELECTRIC_FINGERPRINT = "robolectric";
    public static final String LOGCAT_FILE_NAME = "logcat.txt";
    public static final String LOGCAT_PATH = Environment.getExternalStorageDirectory() + File.separator + LOGCAT_FILE_NAME;

    //tag::doc_log[]
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.enableLogs(Log.VERBOSE);
        //end::doc_log[]
        //initStrictMode();
        CrashSupporter.create(this);
        //tag::register_crash_observer[]
        LogUtils.registerCrashObserver(getApplicationContext(), Uri.parse("file://" + LOGCAT_PATH));
        //end::register_crash_observer[]
    }

    public boolean isRoboElectricUnitTest() {
        return ROBO_ELECTRIC_FINGERPRINT.equals(Build.FINGERPRINT);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    private void initStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    //.penaltyDeath() TODO RoomDatabase closable
                    .build());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        try {
            super.attachBaseContext(base);
        } catch (Exception e) {
            String vmName = System.getProperty("java.vm.name");
            if (!vmName.startsWith("Java")) {
                throw e;
            }
        }
    }

}
