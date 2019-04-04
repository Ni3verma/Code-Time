package com.nitin.codetime.ui.contest.future

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.nitin.codetime.R

class FutureContests : Fragment() {

    companion object {
        fun newInstance() = FutureContests()
    }

    private lateinit var viewModel: FutureContestsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_contests_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FutureContestsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
