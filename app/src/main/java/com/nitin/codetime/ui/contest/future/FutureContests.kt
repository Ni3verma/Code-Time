package com.nitin.codetime.ui.contest.future

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.nitin.codetime.BuildConfig
import com.nitin.codetime.R
import com.nitin.codetime.data.db.ContestShortInfoModel
import com.nitin.codetime.data.network.ContestListApiService
import com.nitin.codetime.data.provider.PreferenceProvider
import com.nitin.codetime.ui.base.ScopedFragment
import com.nitin.codetime.ui.contest.ContestListAdapter
import com.nitin.codetime.ui.settings.SettingsFragment
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
    private val preferenceProvider: PreferenceProvider by instance()

    private var contests: LiveData<List<ContestShortInfoModel>> = MutableLiveData<List<ContestShortInfoModel>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_contests_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FutureContestsViewModel::class.java)
        adapter = ContestListAdapter { view: View, contestId: Int -> contestClicked(view, contestId) }
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.adapter = adapter

        ContestListApiService.initApi(BuildConfig.ApiKey, BuildConfig.UserName)
        getContests()
    }

    private fun getContests() {
        if (isResourcePrefChanged()) {
            invalidateAndFetchNewData()
        } else {
            getLocalData()
        }
    }

    private fun invalidateAndFetchNewData() {
        launch {
            viewModel.deleteContests()
            contests = viewModel.fetchFutureContests()
            preferenceProvider.editBooleanPref(SettingsFragment.RELOAD_FUTURE_CONTESTS, false)
            bindUI()
        }
    }

    private fun getLocalData() {
        launch {
            contests = viewModel.getLocalContestsAsync().await()
            bindUI()
        }
    }

    private fun bindUI() {
        contests.observe(this@FutureContests, Observer { list ->
            list?.let {
                adapter.submitList(list)
            }
        })
    }


    private fun contestClicked(view: View, id: Int) {
        val actionOpenContestDetail = FutureContestsDirections.actionContestDetail(id)
        Navigation.findNavController(view).navigate(actionOpenContestDetail)
    }

    private fun isResourcePrefChanged(): Boolean {
        return preferenceProvider.getBooleanPref(SettingsFragment.RELOAD_FUTURE_CONTESTS, true)
    }
}
