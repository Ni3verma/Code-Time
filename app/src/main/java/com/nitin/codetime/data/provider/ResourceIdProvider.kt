package com.nitin.codetime.data.provider

interface ResourceIdProvider {
    /**
     * return a list of comma separated res id's
     */
    fun getResourceIds(): String
}