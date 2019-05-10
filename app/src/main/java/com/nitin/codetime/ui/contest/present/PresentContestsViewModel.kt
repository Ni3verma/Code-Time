package com.nitin.codetime.ui.contest.present

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.nitin.codetime.data.db.ContestShortInfoModel
import com.nitin.codetime.data.provider.ResourceIdProvider
import com.nitin.codetime.data.repository.ContestRepository
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class PresentContestsViewModel(
    private val contestRepository: ContestRepository,
    private val resourceIdProvider: ResourceIdProvider
) : ViewModel() {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    //TODO: still need to do it the  better way

    //    val contests by lazyDeferred {
//        val dateTime = formatter.format(LocalDateTime.now())
//        val resIds = resourceIdProvider.getResourceIds()
//        contestRepository.getLiveContests(dateTime, resIds)
//    }
//    lateinit var contests: LiveData<List<ContestShortInfoModel>>
    val contests = MediatorLiveData<List<ContestShortInfoModel>>()

    suspend fun getContests() {
        val dateTime = formatter.format(LocalDateTime.now())
        val resIds = resourceIdProvider.getResourceIds()
        val c = contestRepository.getLiveContests(dateTime, resIds)
        contests.addSource(c) {
            contests.value = it
        }
    }
}
