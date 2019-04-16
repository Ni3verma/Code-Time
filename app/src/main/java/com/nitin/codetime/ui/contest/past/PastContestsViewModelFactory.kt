package com.nitin.codetime.ui.contest.past

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nitin.codetime.data.repository.ContestRepository

class PastContestsViewModelFactory(
    private val contestRepository: ContestRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PastContestsViewModel(contestRepository) as T
    }
}