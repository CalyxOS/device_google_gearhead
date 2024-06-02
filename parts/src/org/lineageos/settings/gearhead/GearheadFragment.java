/*
 * SPDX-FileCopyrightText: 2023-2024 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.settings.gearhead;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreferenceCompat;

import org.lineageos.settings.R;

public class GearheadFragment extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    private static final String PREF_GEARHEAD = "gearhead_pref";

    private SwitchPreferenceCompat mGearheadPref;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.gearhead_settings, rootKey);

        mGearheadPref = findPreference(PREF_GEARHEAD);
        mGearheadPref.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mGearheadPref) {
            boolean value = (Boolean) newValue;
            return GearheadUtils.setApplicationEnabledSetting(getActivity(), value);
        }
        return false;
    }
}
