package com.nitin.codetime.data.network.repository

import androidx.lifecycle.LiveData
import com.nitin.codetime.data.db.entity.ContestEntry

interface ContestRepository {
    suspend fun getLiveContests(dateTime: String): LiveData<List<ContestEntry>>
}