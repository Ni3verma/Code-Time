package com.nitin.codetime.ui.contest.present

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nitin.codetime.data.network.repository.ContestRepository

class PresentContestsViewModelFactory(
    private val contestRepository: ContestRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PresentContestsViewModel(contestRepository) as T
    }
}