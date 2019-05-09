package com.nitin.codetime.ui.contest.present

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nitin.codetime.data.db.ContestShortInfoModel
import com.nitin.codetime.data.provider.ResourceIdProvider
import com.nitin.codetime.data.repository.ContestRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class PresentContestsViewModel(
    private val contestRepository: ContestRepository,
    private val resourceIdProvider: ResourceIdProvider
) : ViewModel() {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    suspend fun fetchLiveContests(): LiveData<List<ContestShortInfoModel>> {
        val dateTime = formatter.format(LocalDateTime.now())
        val resIds = resourceIdProvider.getResourceIds()
        return contestRepository.getLiveContests(dateTime, resIds)
    }


    suspend fun deleteContests() {
        GlobalScope.launch {
            val dateTime = formatter.format(LocalDateTime.now())
            contestRepository.deleteLiveContests(dateTime)
        }
    }

    suspend fun getLocalContestsAsync(): Deferred<LiveData<List<ContestShortInfoModel>>> {
        return GlobalScope.async {
            val dateTime = formatter.format(LocalDateTime.now())
            contestRepository.getLocalLiveContests(dateTime)
        }
    }
}
