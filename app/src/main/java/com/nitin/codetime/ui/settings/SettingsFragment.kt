package com.nitin.codetime.ui.settings

import android.os.Bundle
import androidx.core.content.edit
import androidx.preference.MultiSelectListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.nitin.codetime.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_pref, rootKey)
        val pref = findPreference("selected_res") as MultiSelectListPreference
        pref.setOnPreferenceChangeListener { preference, newValue ->
            if (preference?.key == getString(R.string.pref_res_key)) {
                PreferenceManager.getDefaultSharedPreferences(this.context).edit {
                    this.putBoolean(RELOAD_PAST_CONTESTS, true)
                    this.putBoolean(RELOAD_LIVE_CONTESTS, true)
                    this.putBoolean(RELOAD_FUTURE_CONTESTS, true)
                    this.commit()
                }
            }
            true
        }
    }

    companion object {
        const val RELOAD_PAST_CONTESTS = "invalidate_past_contests"
        const val RELOAD_LIVE_CONTESTS = "invalidate_present_contests"
        const val RELOAD_FUTURE_CONTESTS = "invalidate_future_contests"
    }
}