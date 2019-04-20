package com.nitin.codetime.ui.contestDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nitin.codetime.data.repository.ContestRepository

class ContestDetailViewModelFactory(
    private val contestId: Int,
    private val contestRepository: ContestRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContestDetailViewModel(contestId, contestRepository) as T
    }
}