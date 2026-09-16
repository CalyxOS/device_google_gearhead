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
    private static final boolean DEBUG = false;
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
            footerPreference.setLearnMoreAction(v -> {
                Intent helpIntent = HelpUtils.getHelpIntent(getContext(), helpUrl, /* backupContext= */ "");
                if (intent != null) {
                    helpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    try {
                        startActivity(helpIntent);
                    } catch (ActivityNotFoundException e) {
                        if (DEBUG) Log.e("GearheadSupport", "Using fallback to launch help url", e);
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(helpUrl));
                        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(browserIntent);
                    }
                }
            });
            footerPreference.setLearnMoreText(getString(R.string.android_auto_learn_more));
        }
    }

    @Override
    public boolean onPreferenceChange(@NonNull Preference preference, Object isChecked) {
        return GearheadUtils.setApplicationHiddenSetting(getContext(), !(Boolean) isChecked);
    }
}
