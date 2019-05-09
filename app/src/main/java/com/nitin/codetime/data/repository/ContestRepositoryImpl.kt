package com.nitin.codetime.data.repository

import androidx.lifecycle.LiveData
import com.nitin.codetime.data.db.ContestDao
import com.nitin.codetime.data.db.ContestShortInfoModel
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

    override suspend fun getLiveContests(dateTime: String, resIds: String): LiveData<List<ContestShortInfoModel>> {
        return withContext(Dispatchers.IO) {
            fetchLiveContests(dateTime, resIds)
            contestDao.getLiveContests(dateTime)
        }
    }

    override suspend fun getPastContests(dateTime: String, resIds: String): LiveData<List<ContestShortInfoModel>> {
        return withContext(Dispatchers.IO) {
            if (isFetchFromNetworkNeeded()) {
                fetchPastContests(dateTime, resIds)
            }
            return@withContext contestDao.getPastContests(dateTime)
        }
    }

    override suspend fun getFutureContests(dateTime: String, resIds: String): LiveData<List<ContestShortInfoModel>> {
        return withContext(Dispatchers.IO) {
            if (isFetchFromNetworkNeeded()) {
                fetchFutureContests(dateTime, resIds)
            }
            return@withContext contestDao.getFutureContests(dateTime)
        }
    }

    override suspend fun getLocalLiveContests(dateTime: String): LiveData<List<ContestShortInfoModel>> {
        return withContext(Dispatchers.IO) {
            contestDao.getLiveContests(dateTime)
        }
    }

    override suspend fun deleteLiveContests(dateTime: String) {
        withContext(Dispatchers.IO) {
            contestDao.deleteLiveContests(dateTime)
        }
    }

    override suspend fun getContestDetailById(id: Int): LiveData<ContestEntry> {
        return withContext(Dispatchers.IO) {
            return@withContext contestDao.getContestById(id)
        }
    }

    private fun isFetchFromNetworkNeeded(): Boolean {
        // for now it will always return true
        //TODO: implement it
        return true
    }

    private suspend fun fetchLiveContests(dateTime: String, resIds: String) {
        contestListNetworkDataSource.fetchLiveContests(
            resIds,
            dateTime,
            dateTime,
            "end"
        )
    }

    private suspend fun fetchPastContests(dateTime: String, resIds: String) {
        contestListNetworkDataSource.fetchPastContests(
            resIds,
            dateTime,
            "-end"
        )
    }

    private suspend fun fetchFutureContests(dateTime: String, resIds: String) {
        contestListNetworkDataSource.fetchFutureContests(
            resIds,
            dateTime,
            "start"
        )
    }

}