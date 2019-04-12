package com.nitin.codetime.ui.contest.present

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nitin.codetime.BuildConfig
import com.nitin.codetime.R
import com.nitin.codetime.data.network.ConnectivityInterceptorImpl
import com.nitin.codetime.data.network.ContestListApiService
import com.nitin.codetime.data.network.ContestListNetworkDataSourceImpl
import kotlinx.android.synthetic.main.present_contests_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class PresentContests : Fragment() {
    private lateinit var viewModel: PresentContestsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.present_contests_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PresentContestsViewModel::class.java)
        // TODO: Use the ViewModel


        ContestListApiService.initApi(BuildConfig.ApiKey, BuildConfig.UserName)

        val apiService = ContestListApiService(ConnectivityInterceptorImpl(this.context!!))
        val contestListNetworkDataSource = ContestListNetworkDataSourceImpl(apiService)

        contestListNetworkDataSource.downloadedContestList.observe(this,
            Observer { text_view.text = it?.toString() ?: "No internet connection" })


        //just for testing purpose
        GlobalScope.launch(Dispatchers.Main) {
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
            val dateTime = formatter.format(LocalDateTime.now())
            contestListNetworkDataSource.fetchLiveContests("1,2,12", dateTime, dateTime)
        }
    }

}
