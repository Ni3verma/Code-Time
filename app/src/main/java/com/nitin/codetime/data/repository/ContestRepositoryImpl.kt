package com.nitin.codetime.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nitin.codetime.data.db.ContestDao
import com.nitin.codetime.data.db.ContestShortInfoModel
import com.nitin.codetime.data.db.entity.ContestEntry
import com.nitin.codetime.data.network.ContestListNetworkDataSource
import com.nitin.codetime.data.network.response.ContestResponse
import com.nitin.codetime.data.provider.PreferenceProvider
import com.nitin.codetime.internal.InvalidContestTypeCodeException
import com.nitin.codetime.ui.settings.SettingsFragment.Companion.RELOAD_FUTURE_CONTESTS
import com.nitin.codetime.ui.settings.SettingsFragment.Companion.RELOAD_LIVE_CONTESTS
import com.nitin.codetime.ui.settings.SettingsFragment.Companion.RELOAD_PAST_CONTESTS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val PAST_CONTEST_TYPE_CODE = -1
const val LIVE_CONTEST_TYPE_CODE = 0
const val FUTURE_CONTEST_TYPE_CODE = 1

class ContestRepositoryImpl(
    private val contestDao: ContestDao,
    private val contestListNetworkDataSource: ContestListNetworkDataSource,
    private val preferenceProvider: PreferenceProvider
) : ContestRepository {

    private val _networkState = MutableLiveData<Int>()
    override val networkState: LiveData<Int>
        get() = _networkState

    init {
        contestListNetworkDataSource.downloadedContestList.observeForever { newData ->
            persistFetchedData(newData)
        }
        contestListNetworkDataSource.networkState.observeForever { state ->
            _networkState.postValue(state)
        }
    }

    private fun persistFetchedData(newData: ContestResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            contestDao.upsertContests(newData.contestEntries)
        }
    }

    override suspend fun getPastContests(
        dateTime: String,
        resIds: String,
        forceRefresh: Boolean
    ): LiveData<List<ContestShortInfoModel>> {
        return withContext(Dispatchers.IO) {
            if (forceRefresh || isFetchFromNetworkNeeded(PAST_CONTEST_TYPE_CODE)) {
                deletePastContests(dateTime)
                fetchPastContests(dateTime, resIds)
                preferenceProvider.editBooleanPref(RELOAD_PAST_CONTESTS, false)
            }
            return@withContext contestDao.getPastContests(dateTime)
        }
    }

    override suspend fun getLiveContests(
        dateTime: String,
        resIds: String,
        forceRefresh: Boolean
    ): LiveData<List<ContestShortInfoModel>> {
        return withContext(Dispatchers.IO) {
            if (forceRefresh || isFetchFromNetworkNeeded(LIVE_CONTEST_TYPE_CODE)) {
                deleteLiveContests(dateTime)
                fetchLiveContests(dateTime, resIds)
                preferenceProvider.editBooleanPref(RELOAD_LIVE_CONTESTS, false)
            }
            return@withContext contestDao.getLiveContests(dateTime)
        }
    }

    override suspend fun getFutureContests(
        dateTime: String,
        resIds: String,
        forceRefresh: Boolean
    ): LiveData<List<ContestShortInfoModel>> {
        return withContext(Dispatchers.IO) {
            if (forceRefresh || isFetchFromNetworkNeeded(FUTURE_CONTEST_TYPE_CODE)) {
                deleteFutureContests(dateTime)
                fetchFutureContests(dateTime, resIds)
                preferenceProvider.editBooleanPref(RELOAD_FUTURE_CONTESTS, false)
            }
            return@withContext contestDao.getFutureContests(dateTime)
        }
    }

    override suspend fun deletePastContests(dateTime: String) {
        withContext(Dispatchers.IO) {
            contestDao.deletePastContests(dateTime)
        }
    }

    override suspend fun deleteLiveContests(dateTime: String) {
        withContext(Dispatchers.IO) {
            contestDao.deleteLiveContests(dateTime)
        }
    }

    override suspend fun deleteFutureContests(dateTime: String) {
        withContext(Dispatchers.IO) {
            contestDao.deleteFutureContests(dateTime)
        }
    }

    override suspend fun getContestDetailById(id: Int): LiveData<ContestEntry> {
        return withContext(Dispatchers.IO) {
            return@withContext contestDao.getContestById(id)
        }
    }

    private fun isFetchFromNetworkNeeded(contestTypeCode: Int): Boolean {
        return when (contestTypeCode) {
            PAST_CONTEST_TYPE_CODE -> preferenceProvider.getBooleanPref(RELOAD_PAST_CONTESTS, true)
            LIVE_CONTEST_TYPE_CODE -> preferenceProvider.getBooleanPref(RELOAD_LIVE_CONTESTS, true)
            FUTURE_CONTEST_TYPE_CODE -> preferenceProvider.getBooleanPref(RELOAD_FUTURE_CONTESTS, true)
            else -> throw InvalidContestTypeCodeException()
        }
    }

    private suspend fun fetchPastContests(dateTime: String, resIds: String) {
        contestListNetworkDataSource.fetchPastContests(
            resIds,
            dateTime,
            "-end"
        )
    }

    private suspend fun fetchLiveContests(dateTime: String, resIds: String) {
        contestListNetworkDataSource.fetchLiveContests(
            resIds,
            dateTime,
            dateTime,
            "end"
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