package com.nitin.codetime.data.repository

import androidx.lifecycle.LiveData
import com.nitin.codetime.data.db.ContestShortInfoModel

interface ContestRepository {
    suspend fun getLiveContests(dateTime: String): LiveData<List<ContestShortInfoModel>>
    suspend fun getPastContests(dateTime: String): LiveData<List<ContestShortInfoModel>>
    suspend fun getFutureContests(dateTime: String): LiveData<List<ContestShortInfoModel>>
}