package com.nitin.codetime.data.provider

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class PreferenceProviderImpl(context: Context) : PreferenceProvider {
    private val appContext = context.applicationContext

    override fun editBooleanPref(key: String, value: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(appContext)
            .edit {
                this.putBoolean(key, value)
                this.commit()
            }
    }

    override fun getBooleanPref(key: String, defValue: Boolean): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(appContext)
            .getBoolean(key, defValue)
    }
}