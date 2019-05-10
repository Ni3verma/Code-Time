package com.nitin.codetime.ui.contest.past

import androidx.lifecycle.ViewModel
import com.nitin.codetime.data.provider.ResourceIdProvider
import com.nitin.codetime.data.repository.ContestRepository
import com.nitin.codetime.internal.lazyDeferred
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class PastContestsViewModel(
    private val contestRepository: ContestRepository,
    private val resourceIdProvider: ResourceIdProvider
) : ViewModel() {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    val contests by lazyDeferred {
        val dateTime = formatter.format(LocalDateTime.now())
        val resIds = resourceIdProvider.getResourceIds()
        contestRepository.getPastContests(dateTime, resIds)
    }
}
