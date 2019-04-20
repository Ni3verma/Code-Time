package com.nitin.codetime.ui.contestDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.nitin.codetime.R
import kotlinx.android.synthetic.main.contest_detail_fragment.*

class ContestDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ContestDetailFragment()
    }

    private lateinit var viewModel: ContestDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contest_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ContestDetailViewModel::class.java)
        // TODO: Use the ViewModel

        val args = arguments?.let {
            ContestDetailFragmentArgs.fromBundle(it)
        }
        text_view.text = args?.contestId.toString()
    }

}
