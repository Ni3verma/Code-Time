package com.nitin.codetime.ui.contest.future

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nitin.codetime.BuildConfig
import com.nitin.codetime.R
import com.nitin.codetime.data.network.ContestListApiService
import com.nitin.codetime.ui.MainActivity
import com.nitin.codetime.ui.base.ScopedFragment
import com.nitin.codetime.ui.contest.ContestListAdapter
import kotlinx.android.synthetic.main.present_contests_fragment.*
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
        (activity as MainActivity).setBottomNavVisibility(View.VISIBLE)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FutureContestsViewModel::class.java)
        adapter = ContestListAdapter { view: View, contestId: Int -> contestClicked(view, contestId) }
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.adapter = adapter
        ContestListApiService.initApi(BuildConfig.ApiKey, BuildConfig.UserName)

        viewModel.getContests()
        bindUI()
        fab_refresh.setOnClickListener {
            viewModel.getContests(true)
        }
    }

    private fun bindUI() {
        viewModel.contests.observe(this@FutureContests, Observer { list ->
            list?.let {
                observeNetworkState()
                adapter.submitList(list)
            }
        })
    }

    private fun observeNetworkState() {
        viewModel.networkState.observe(this@FutureContests, Observer { state ->
            if (state == 0) {
                Log.d("Nitin", "No internet connection")
                Snackbar.make(recycler_view, "No internet connection", Snackbar.LENGTH_LONG)
                    .setAction("retry") {
                        viewModel.getContests(true)
                    }
                    .setActionTextColor(ContextCompat.getColor(context!!, android.R.color.white))
                    .show()
            }
        })
    }

    private fun contestClicked(view: View, id: Int) {
        val actionOpenContestDetail = FutureContestsDirections.actionContestDetail(id)
        Navigation.findNavController(view).navigate(actionOpenContestDetail)
    }
}
