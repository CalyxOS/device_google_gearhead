/*
 * SPDX-FileCopyrightText: 2023-2024 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.calyxos.gearheadsupport.gearhead;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settingslib.HelpUtils;
import com.android.settingslib.widget.FooterPreference;

import org.calyxos.gearheadsupport.R;

public class GearheadFragment extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    private static final String PREF_GEARHEAD = "gearhead_pref";
    private static final String PREF_FOOTER = "footer_preference";

    private SwitchPreferenceCompat mGearheadPref;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.gearhead_settings, rootKey);

        mGearheadPref = findPreference(PREF_GEARHEAD);
        mGearheadPref.setOnPreferenceChangeListener(this);

        FooterPreference footerPreference = findPreference(PREF_FOOTER);
        String helpUrl = getString(R.string.android_auto_help_url);
        if (footerPreference != null && !TextUtils.isEmpty(helpUrl)) {
            footerPreference.setLearnMoreAction(v -> startActivity(
                    HelpUtils.getHelpIntent(getActivity(), helpUrl, /* backupContext= */ "")));
            footerPreference.setLearnMoreText(getString(R.string.android_auto_learn_more));
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mGearheadPref) {
            boolean value = (Boolean) newValue;
            return GearheadUtils.setApplicationHiddenSetting(getActivity(), !value);
        }
        return false;
    }
}
