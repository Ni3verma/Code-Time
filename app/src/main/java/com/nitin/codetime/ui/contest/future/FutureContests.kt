package com.nitin.codetime.ui.contest.future

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.nitin.codetime.BuildConfig
import com.nitin.codetime.R
import com.nitin.codetime.data.network.ContestListApiService
import com.nitin.codetime.ui.base.ScopedFragment
import com.nitin.codetime.ui.contest.ContestListAdapter
import kotlinx.android.synthetic.main.present_contests_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class FutureContests : ScopedFragment(), KodeinAware {

    override val kodein by kodein()
    private val viewModelFactory: FutureContestsViewModelFactory by instance()
    private lateinit var viewModel: FutureContestsViewModel
    private lateinit var adapter: ContestListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_contests_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FutureContestsViewModel::class.java)
        adapter = ContestListAdapter()
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.adapter = adapter

        ContestListApiService.initApi(BuildConfig.ApiKey, BuildConfig.UserName)
        bindUI()
    }

    private fun bindUI() = launch {
        val contests = viewModel.contests.await()
        contests.observe(this@FutureContests, Observer { list ->
            list?.let {
                adapter.submitList(list)
            }
        })
    }

}
