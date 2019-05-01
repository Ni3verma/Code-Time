package com.nitin.codetime.ui.contestDetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nitin.codetime.R
import com.nitin.codetime.data.db.entity.ContestEntry
import com.nitin.codetime.internal.NoIdPassedForDetailException
import com.nitin.codetime.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.contest_detail_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.factory

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
            poster.setImageResource(viewModel.getLogo(contest.resourceEntry.name))
            name.text = contest.name
            updateDates(contest.startDate, contest.endDate)

            setShareFabListener(contest)
            setWebsiteFabListener(contest)
            setAddToCalendarButtonListener(contest)
        })
    }

    private fun updateDates(startDateTime: String, endDateTime: String) {
        val start = viewModel.getDateAndTime(startDateTime)
        val end = viewModel.getDateAndTime(endDateTime)

        start_date.text = "${start[0]}\n${start[1]} ${start[2]}"
        end_date.text = "${end[0]}\n${end[1]} ${end[2]}"
    }

    private fun setShareFabListener(contest: ContestEntry) {
        share_fab.setOnClickListener {
            val intent = viewModel.shareContest(contest.name, contest.url)
            startActivity(Intent.createChooser(intent, "Sharing is caring"))
        }
    }

    private fun setWebsiteFabListener(contest: ContestEntry) {
        website_fab.setOnClickListener {
            val intent = viewModel.openInBrower(contest.url)
            startActivity(intent)
        }
    }

    private fun setAddToCalendarButtonListener(contest: ContestEntry) {
        add_to_calender.setOnClickListener {
            val intent = viewModel.addToCalendar(contest)
            startActivity(intent)
        }
    }
}
