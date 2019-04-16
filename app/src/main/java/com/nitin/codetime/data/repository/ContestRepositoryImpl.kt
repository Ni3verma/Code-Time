package com.nitin.codetime.data.repository

import androidx.lifecycle.LiveData
import com.nitin.codetime.data.db.ContestDao
import com.nitin.codetime.data.db.ContestShortInfoModel
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

    override suspend fun getLiveContests(dateTime: String): LiveData<List<ContestShortInfoModel>> {
        return withContext(Dispatchers.IO) {
            if (isFetchFromNetworkNeeded()) {
                fetchLiveContests(dateTime)
            }
            return@withContext contestDao.getLiveContests(dateTime)
        }
    }

    override suspend fun getPastContests(dateTime: String): LiveData<List<ContestShortInfoModel>> {
        return withContext(Dispatchers.IO) {
            if (isFetchFromNetworkNeeded()) {
                fetchPastContests(dateTime)
            }
            return@withContext contestDao.getPastContests(dateTime)
        }
    }

    override suspend fun getFutureContests(dateTime: String): LiveData<List<ContestShortInfoModel>> {
        return withContext(Dispatchers.IO) {
            if (isFetchFromNetworkNeeded()) {
                fetchFutureContests(dateTime)
            }
            return@withContext contestDao.getFutureContests(dateTime)
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

    private suspend fun fetchPastContests(dateTime: String) {
        contestListNetworkDataSource.fetchPastContests(
            "1,2,12",
            dateTime,
            "-end"
        )
    }

    private suspend fun fetchFutureContests(dateTime: String) {
        contestListNetworkDataSource.fetchFutureContests(
            "1,2,12",
            dateTime,
            "start"
        )
    }

}