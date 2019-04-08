package com.nitin.codetime.data.response

import com.google.gson.annotations.SerializedName

data class Meta(
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: Any,
    @SerializedName("total_count")
    val totalCount: Int
)