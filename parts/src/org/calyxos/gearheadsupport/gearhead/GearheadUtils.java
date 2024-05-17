/*
 * SPDX-FileCopyrightText: 2023-2024 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.calyxos.gearheadsupport.gearhead;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.preference.PreferenceManager;

public class GearheadUtils {
    private static final String TAG = "GearheadUtils";
    private static final String PREF_FIRST_BOOT = "first_boot";
    private static final String GEARHEAD_PACKAGE = "com.google.android.projection.gearhead";

    public static boolean setApplicationEnabledSetting(Context context, boolean enable) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.setApplicationEnabledSetting(GEARHEAD_PACKAGE, enable ?
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED :
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Failed to " + (enable ? "enable" : "disable") + " " + GEARHEAD_PACKAGE, e);
            return false;
        }
    }

    public static void startService(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        if (isFirstBoot(prefs)) {
            boolean isGearheadDisabled = setApplicationEnabledSetting(context, false);
            if (isGearheadDisabled) {
                prefs.edit().putBoolean(PREF_FIRST_BOOT, false).apply();
            }
        }
    }

    private static boolean isFirstBoot(SharedPreferences prefs) {
        return prefs.getBoolean(PREF_FIRST_BOOT, true);
    }
}
