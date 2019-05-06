package com.nitin.codetime.ui.settings

import android.os.Bundle
import android.util.Log
import androidx.preference.MultiSelectListPreference
import androidx.preference.PreferenceFragmentCompat
import com.nitin.codetime.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_pref, rootKey)
        val pref = findPreference("selected_res") as MultiSelectListPreference
        //TODO: just experimenting, remove in next commit
        pref.setOnPreferenceChangeListener { preference, newValue ->
            Log.d("Nitin", preference.key)
            Log.d("Nitin", "new value = $newValue")
            true
        }
    }
}