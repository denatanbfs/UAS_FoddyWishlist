package com.denatan.foodywishlist.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.denatan.foodywishlist.R;

public class SettingFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
