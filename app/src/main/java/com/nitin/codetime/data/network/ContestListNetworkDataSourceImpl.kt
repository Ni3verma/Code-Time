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

    private val _networkState = MutableLiveData<Int>()
    override val networkState: LiveData<Int>
        get() = _networkState

    override suspend fun fetchLiveContests(resourceIds: String, startDate: String, endDate: String, orderBy: String) {
        try {
            val fetchedContestList = apiService.getLiveContests(resourceIds, startDate, endDate, orderBy).await()
            _downloadedContestList.postValue(fetchedContestList)
        } catch (e: NoConnectivityException) {
            onInternetConnectionError(e)
        }
    }

    override suspend fun fetchPastContests(resourceIds: String, endDate: String, orderBy: String) {
        try {
            val list = apiService.getPastContests(resourceIds, endDate, orderBy).await()
            _downloadedContestList.postValue(list)
        } catch (e: NoConnectivityException) {
            onInternetConnectionError(e)
        }
    }

    override suspend fun fetchFutureContests(resourceIds: String, startDate: String, orderBy: String) {
        try {
            val list = apiService.getFutureContests(resourceIds, startDate, orderBy).await()
            _downloadedContestList.postValue(list)
        } catch (e: NoConnectivityException) {
            onInternetConnectionError(e)
        }
    }

    override fun onInternetConnectionError(error: NoConnectivityException) {
        Log.e("Nitin", "No internet connection", error)
        _networkState.postValue(0)
    }
}