package com.nitin.codetime.data.provider

interface PreferenceProvider {
    fun editBooleanPref(key: String, value: Boolean)
    fun getBooleanPref(key: String, defValue: Boolean): Boolean
}