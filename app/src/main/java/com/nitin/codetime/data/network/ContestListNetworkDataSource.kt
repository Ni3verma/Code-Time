package com.nitin.codetime.data.network

import androidx.lifecycle.LiveData
import com.nitin.codetime.data.network.response.ContestResponse
import com.nitin.codetime.internal.NoConnectivityException

interface ContestListNetworkDataSource {
    val downloadedContestList: LiveData<ContestResponse>
    val networkState: LiveData<Int>

    suspend fun fetchLiveContests(
        resourceIds: String,
        startDate: String,
        endDate: String,
        orderBy: String
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

    fun onInternetConnectionError(error: NoConnectivityException)
}