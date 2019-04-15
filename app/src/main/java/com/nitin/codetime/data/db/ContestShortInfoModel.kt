package com.nitin.codetime.data.db

import androidx.room.ColumnInfo

data class ContestShortInfoModel(
    @ColumnInfo(name = "cid")
    val id: Int,
    val name: String,
    @ColumnInfo(name = "res_name")
    val resName: String
)