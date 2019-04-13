package com.nitin.codetime.data.network.response

import com.google.gson.annotations.SerializedName
import com.nitin.codetime.data.db.entity.ContestEntry

data class ContestResponse(
    //for now we won't use meta
//    val meta: Meta,
    @SerializedName("objects")
    val contestEntries: List<ContestEntry>
)