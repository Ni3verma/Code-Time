package com.nitin.codetime.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nitin.codetime.data.network.response.ContestResponse
import com.nitin.codetime.internal.NoConnectivityException

class ContestListNetworkDataSourceImpl(
    private val apiService: ContestListApiService
) : ContestListNetworkDataSource {
    private val _downloadedContestList = MutableLiveData<ContestResponse>()

    override val downloadedContestList: LiveData<ContestResponse>
        get() = _downloadedContestList

    override suspend fun fetchLiveContests(resourceIds: String, startDate: String, endDate: String) {
        try {
            val fetchedContestList = apiService.getLiveContests(resourceIds, startDate, endDate).await()
            _downloadedContestList.postValue(fetchedContestList)
        } catch (e: NoConnectivityException) {
            Log.e("Nitin", "No internet connection", e)
        }
    }
}