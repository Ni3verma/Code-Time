package com.nitin.codetime.data.network.response

import com.google.gson.annotations.SerializedName

data class ContestResponse(
    val meta: Meta,
    @SerializedName("objects")
    val contestEntries: List<ContestEntry>
)