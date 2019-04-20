package com.nitin.codetime.ui.contestDetail

import android.os.Bundle
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

            text_view.text = contest.toString()
        })
    }
}
