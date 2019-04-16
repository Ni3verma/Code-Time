package com.nitin.codetime.data.network

import androidx.lifecycle.LiveData
import com.nitin.codetime.data.network.response.ContestResponse

interface ContestListNetworkDataSource {
    val downloadedContestList: LiveData<ContestResponse>

    suspend fun fetchLiveContests(
        resourceIds: String,
        startDate: String,
        endDate: String
    )

    suspend fun fetchPastContests(
        resourceIds: String,
        endDate: String,
        orderBy: String
    )

    suspend fun fetchFutureContests(
        resourceIds: String,
        startDate: String,
        orderBy: String
    )
}