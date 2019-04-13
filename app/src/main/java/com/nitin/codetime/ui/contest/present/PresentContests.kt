package com.nitin.codetime.ui.contest.present

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nitin.codetime.BuildConfig
import com.nitin.codetime.R
import com.nitin.codetime.data.network.ContestListApiService
import com.nitin.codetime.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.present_contests_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class PresentContests : ScopedFragment(), KodeinAware {

    override val kodein by kodein()
    private val viewModelFactory: PresentContestsViewModelFactory by instance()
    private lateinit var viewModel: PresentContestsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.present_contests_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PresentContestsViewModel::class.java)

        ContestListApiService.initApi(BuildConfig.ApiKey, BuildConfig.UserName)
        bindUI()
    }

    private fun bindUI() = launch {
        val contests = viewModel.contests.await()
        contests.observe(this@PresentContests, Observer {
            text_view.text = it?.toString()
        })
    }

}
