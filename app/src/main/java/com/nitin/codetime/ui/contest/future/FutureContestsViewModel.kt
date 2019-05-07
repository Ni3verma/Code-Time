package com.nitin.codetime.ui.contest.future

import androidx.lifecycle.ViewModel
import com.nitin.codetime.data.provider.ResourceIdProvider
import com.nitin.codetime.data.repository.ContestRepository
import com.nitin.codetime.internal.lazyDeferred
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class FutureContestsViewModel(
    private val contestRepository: ContestRepository,
    private val resourceIdProvider: ResourceIdProvider
) : ViewModel() {
    private val resIds = resourceIdProvider.getResourceIds()

    val contests by lazyDeferred {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val dateTime = formatter.format(LocalDateTime.now())
        contestRepository.getFutureContests(dateTime, resIds)
    }
}
