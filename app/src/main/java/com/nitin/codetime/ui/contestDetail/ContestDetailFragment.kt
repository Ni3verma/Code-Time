package com.nitin.codetime.ui.contestDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nitin.codetime.R
import com.nitin.codetime.internal.NoIdPassedForDetailException
import com.nitin.codetime.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.contest_detail_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.factory
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class ContestDetailFragment : ScopedFragment(), KodeinAware {

    override val kodein by kodein()
    private val viewModelFactoryInstanceFactory: ((Int) -> ContestDetailViewModelFactory) by factory()

    private lateinit var viewModel: ContestDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contest_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val args = arguments?.let {
            ContestDetailFragmentArgs.fromBundle(it)
        }
        val id = args?.contestId
            ?: throw NoIdPassedForDetailException()

        viewModel = ViewModelProviders.of(
            this,
            viewModelFactoryInstanceFactory(id)
        ).get(ContestDetailViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val contestDetail = viewModel.contestDetail.await()
        contestDetail.observe(this@ContestDetailFragment, Observer { contest ->
            if (contest == null) return@Observer

            Log.d("Nitin", contest.toString())
            poster.setImageResource(getLogo(contest.resourceEntry.name))
            name.text = contest.name
            updateDates(contest.startDate, contest.endDate)
        })
    }

    private fun updateDates(startDateTime: String, endDateTime: String) {
        val start = LocalDateTime
            .parse(startDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
            .split(" ")

        val end = LocalDateTime
            .parse(endDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
            .split(" ")

        start_date.text = "${start[0]}\n${start[1]}"
        end_date.text = "${end[0]}\n${end[1]}"
    }

    private fun getLogo(res: String): Int {
        return when (res) {
            "codechef.com" -> R.drawable.codechef_poster
            "topcoder.com" -> R.drawable.topcoder_poster
            "hackerrank.com" -> R.drawable.hackerrank_poster
            "hackerearth.com" -> R.drawable.hackerearth_poster
            "codeforces.com" -> R.drawable.codeforces_logo
            // TODO: change with app icon
            else -> R.drawable.ic_launcher_background
        }
    }
}
