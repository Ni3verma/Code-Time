package com.nitin.codetime.ui.contest.past

import android.util.Log
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

class PastContestsViewModel(
    private val contestRepository: ContestRepository,
    private val resourceIdProvider: ResourceIdProvider
) : ViewModel() {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    private val _contests = MutableLiveData<List<ContestShortInfoModel>>()
    val contests: LiveData<List<ContestShortInfoModel>>
        get() = _contests

    fun getContests() {
        viewModelScope.launch {
            val dateTime = formatter.format(LocalDateTime.now())
            val resIds = resourceIdProvider.getResourceIds()
            val contestsLiveData = contestRepository.getPastContests(dateTime, resIds)

            contestsLiveData.observeForever {
                Log.d("Nitin", "list size in vm=${contestsLiveData.value?.size}")
                _contests.value = it
            }
        }
    }
}
