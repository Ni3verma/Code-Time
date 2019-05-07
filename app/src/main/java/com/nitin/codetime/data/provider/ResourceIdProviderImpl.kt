package com.nitin.codetime.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.nitin.codetime.R

class ResourceIdProviderImpl(context: Context) : ResourceIdProvider {
    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun getResourceIds(): String {
        val res = preferences.getStringSet(
            appContext.resources.getString(R.string.pref_res_key),
            setOf("2", "63")
        )

        return res!!.joinToString(",")
    }
}