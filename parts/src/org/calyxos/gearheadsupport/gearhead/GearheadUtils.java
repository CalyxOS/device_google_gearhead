/*
 * SPDX-FileCopyrightText: 2023-2024 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.calyxos.gearheadsupport.gearhead;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.util.Log;

import androidx.preference.PreferenceManager;

public class GearheadUtils {
    private static final String TAG = "GearheadUtils";
    private static final String PREF_FIRST_BOOT = "first_boot";
    private static final String GEARHEAD_PACKAGE = "com.google.android.projection.gearhead";

    public static boolean getApplicationHiddenSetting(Context context) {
        PackageManager pm = context.getPackageManager();
        UserHandle userHandle = UserHandle.of(context.getUserId());
        return pm.getApplicationHiddenSettingAsUser(GEARHEAD_PACKAGE, userHandle);
    }

    public static boolean setApplicationHiddenSetting(Context context, boolean hide) {
        PackageManager pm = context.getPackageManager();
        UserHandle userHandle = UserHandle.of(context.getUserId());
        return pm.setApplicationHiddenSettingAsUser(GEARHEAD_PACKAGE, hide, userHandle);
    }

    public static void startService(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        if (isFirstBoot(prefs)) {
            boolean isGearheadHidden = setApplicationHiddenSetting(context, true);
            if (isGearheadHidden) {
                prefs.edit().putBoolean(PREF_FIRST_BOOT, false).apply();
            }
        }
    }

    private static boolean isFirstBoot(SharedPreferences prefs) {
        return prefs.getBoolean(PREF_FIRST_BOOT, true);
    }
}
