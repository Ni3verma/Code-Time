package com.nitin.codetime.ui.contestDetail

import androidx.lifecycle.ViewModel;
import com.nitin.codetime.data.repository.ContestRepository
import com.nitin.codetime.internal.lazyDeferred

class ContestDetailViewModel(
    private val contestId: Int,
    private val contestRepository: ContestRepository
) : ViewModel() {
    val contestDetail by lazyDeferred {
        contestRepository.getContestDetailById(contestId)
    }
}
