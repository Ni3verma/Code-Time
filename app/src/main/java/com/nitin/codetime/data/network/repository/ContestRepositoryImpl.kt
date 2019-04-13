package com.nitin.codetime.data.network.repository

import androidx.lifecycle.LiveData
import com.nitin.codetime.data.db.ContestDao
import com.nitin.codetime.data.db.entity.ContestEntry
import com.nitin.codetime.data.network.ContestListNetworkDataSource
import com.nitin.codetime.data.network.response.ContestResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContestRepositoryImpl(
    private val contestDao: ContestDao,
    private val contestListNetworkDataSource: ContestListNetworkDataSource
) : ContestRepository {

    init {
        contestListNetworkDataSource.downloadedContestList.observeForever { newData ->
            persistFetchedData(newData)
        }
    }

    private fun persistFetchedData(newData: ContestResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            contestDao.upsertContests(newData.contestEntries)
        }
    }

    override suspend fun getLiveContests(dateTime: String): LiveData<List<ContestEntry>> {
        return withContext(Dispatchers.IO) {
            initContests(dateTime)
            return@withContext contestDao.getLiveContests(dateTime)
        }
    }

    private suspend fun initContests(dateTime: String) {
        if (isFetchFromNetworkNeeded()) {
            fetchLiveContests(dateTime)
        }
    }

    private fun isFetchFromNetworkNeeded(): Boolean {
        // for now it will always return true
        //TODO: implement it
        return true
    }

    private suspend fun fetchLiveContests(dateTime: String) {
        contestListNetworkDataSource.fetchLiveContests(
            "1,2,12",
            dateTime,
            dateTime
        )
    }

}