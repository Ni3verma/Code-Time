package com.nitin.codetime.ui.contest.present

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nitin.codetime.data.db.ContestShortInfoModel
import com.nitin.codetime.data.provider.ResourceIdProvider
import com.nitin.codetime.data.repository.ContestRepository
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class PresentContestsViewModel(
    private val contestRepository: ContestRepository,
    private val resourceIdProvider: ResourceIdProvider
) : ViewModel() {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    private val _contests = MutableLiveData<List<ContestShortInfoModel>>()
    val contests: LiveData<List<ContestShortInfoModel>>
        get() = _contests

    private val _networkState = MutableLiveData<Int>()
    val networkState: LiveData<Int>
        get() = _networkState

    init {
        contestRepository.networkState.observeForever { state ->
            _networkState.postValue(state)
        }
    }

    fun getContests(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            val dateTime = formatter.format(LocalDateTime.now())
            val resIds = resourceIdProvider.getResourceIds()
            val contestsLiveData = contestRepository.getLiveContests(dateTime, resIds, forceRefresh)

            contestsLiveData.observeForever {
                _contests.value = it
            }
        }
    }
}
