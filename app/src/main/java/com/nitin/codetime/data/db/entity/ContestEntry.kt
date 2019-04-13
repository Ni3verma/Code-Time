package com.nitin.codetime.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "contests")
data class ContestEntry(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val cid: Int,   //contest ID
    val duration: Int,
    @SerializedName("end")
    val endDate: String,
    @SerializedName("event")
    val name: String,
    @SerializedName("href")
    val url: String,
    @SerializedName("resource")
    @Embedded(prefix = "res_")
    val resourceEntry: ResourceEntry,
    @SerializedName("start")
    val startDate: String
)