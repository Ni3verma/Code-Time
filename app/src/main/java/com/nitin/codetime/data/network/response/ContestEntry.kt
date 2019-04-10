package com.nitin.codetime.data.network.response

import com.google.gson.annotations.SerializedName

data class ContestEntry(
    val duration: Int,
    val end: String,
    val event: String,
    val href: String,
    val id: Int,
    @SerializedName("resource")
    val resourceEntry: ResourceEntry,
    val start: String
)