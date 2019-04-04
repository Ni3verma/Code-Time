package com.nitin.codetime.ui.contest.past

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.nitin.codetime.R

class PastContests : Fragment() {

    companion object {
        fun newInstance() = PastContests()
    }

    private lateinit var viewModel: PastContestsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.past_contests_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PastContestsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
