package com.nitin.codetime.data.repository

import androidx.lifecycle.LiveData
import com.nitin.codetime.data.db.ContestShortInfoModel
import com.nitin.codetime.data.db.entity.ContestEntry

interface ContestRepository {
    suspend fun getLiveContests(dateTime: String, resIds: String): LiveData<List<ContestShortInfoModel>>
    suspend fun getPastContests(dateTime: String, resIds: String): LiveData<List<ContestShortInfoModel>>
    suspend fun getFutureContests(dateTime: String, resIds: String): LiveData<List<ContestShortInfoModel>>

    suspend fun getContestDetailById(id: Int): LiveData<ContestEntry>
}