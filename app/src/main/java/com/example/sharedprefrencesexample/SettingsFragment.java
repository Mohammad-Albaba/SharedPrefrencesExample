package com.example.sharedprefrencesexample;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);
        Preference preference = findPreference(getString(R.string.key_font_size));
        preference.setSummary(getPreferenceManager().getSharedPreferences().getString(getString(R.string.key_font_size), getString(R.string.font_default_value)));
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.key_font_size))){
            Preference preference = findPreference(key);
            preference.setSummary(sharedPreferences.getString(getString(R.string.key_font_size), getString(R.string.font_default_value)));
            getActivity().setResult(Activity.RESULT_OK);
        }else {
            boolean isNight = sharedPreferences.getBoolean(getString(R.string.key_night_mode),getResources().getBoolean(R.bool.night_mode_default));
            if (isNight){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
    }
}