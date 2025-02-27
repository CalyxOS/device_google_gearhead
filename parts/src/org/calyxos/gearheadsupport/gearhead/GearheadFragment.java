/*
 * SPDX-FileCopyrightText: 2023-2024 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.calyxos.gearheadsupport.gearhead;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.android.settingslib.HelpUtils;
import com.android.settingslib.widget.FooterPreference;
import com.android.settingslib.widget.MainSwitchPreference;

import org.calyxos.gearheadsupport.R;

public class GearheadFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener {
    private static final String PREF_GEARHEAD = "gearhead_pref";
    private static final String PREF_FOOTER = "footer_preference";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.gearhead_settings, rootKey);

        MainSwitchPreference switchBar = findPreference(PREF_GEARHEAD);
        switchBar.setOnPreferenceChangeListener(this);
        switchBar.setChecked(!GearheadUtils.getApplicationHiddenSetting(getContext()));

        FooterPreference footerPreference = findPreference(PREF_FOOTER);
        String helpUrl = getString(R.string.android_auto_help_url);
        if (footerPreference != null && !TextUtils.isEmpty(helpUrl)) {
            footerPreference.setLearnMoreAction(v -> startActivity(
                    HelpUtils.getHelpIntent(getContext(), helpUrl, /* backupContext= */ "")));
            footerPreference.setLearnMoreText(getString(R.string.android_auto_learn_more));
        }
    }

    @Override
    public boolean onPreferenceChange(@NonNull Preference preference, Object isChecked) {
        return GearheadUtils.setApplicationHiddenSetting(getContext(), !(Boolean) isChecked);
    }
}
