package com.nitin.codetime.ui.contest.future

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nitin.codetime.data.repository.ContestRepository

class FutureContestsViewModelFactory(
    private val contestRepository: ContestRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureContestsViewModel(contestRepository) as T
    }
}