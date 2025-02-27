/*
 * SPDX-FileCopyrightText: 2023-2024 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.calyxos.gearheadsupport.gearhead;

import android.os.Bundle;
import android.view.MenuItem;

import com.android.settingslib.collapsingtoolbar.CollapsingToolbarBaseActivity;

import org.calyxos.gearheadsupport.R;

public class GearheadActivity extends CollapsingToolbarBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                new GearheadFragment(), "gearhead").commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
