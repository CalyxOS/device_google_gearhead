/*
 * SPDX-FileCopyrightText: 2023-2024 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.calyxos.gearheadsupport.gearhead;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CompoundButton;

import androidx.preference.PreferenceFragment;

import com.android.settingslib.HelpUtils;
import com.android.settingslib.widget.MainSwitchPreference;
import com.android.settingslib.widget.FooterPreference;

import org.calyxos.gearheadsupport.R;

public class GearheadFragment extends PreferenceFragment
        implements CompoundButton.OnCheckedChangeListener {
    private static final String PREF_GEARHEAD = "gearhead_pref";
    private static final String PREF_FOOTER = "footer_preference";

    private MainSwitchPreference mSwitchBar;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.gearhead_settings, rootKey);

        mSwitchBar = findPreference(PREF_GEARHEAD);
        mSwitchBar.addOnSwitchChangeListener(this);

        FooterPreference footerPreference = findPreference(PREF_FOOTER);
        String helpUrl = getString(R.string.android_auto_help_url);
        if (footerPreference != null && !TextUtils.isEmpty(helpUrl)) {
            footerPreference.setLearnMoreAction(v -> startActivity(
                    HelpUtils.getHelpIntent(getActivity(), helpUrl, /* backupContext= */ "")));
            footerPreference.setLearnMoreText(getString(R.string.android_auto_learn_more));
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        GearheadUtils.setApplicationHiddenSetting(getActivity(), !isChecked);
    }
}
