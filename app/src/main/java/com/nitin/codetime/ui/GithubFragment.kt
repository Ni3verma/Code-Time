package com.nitin.codetime.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nitin.codetime.R

class GithubFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).setBottomNavVisibility(View.GONE)
        return inflater.inflate(R.layout.fragment_github, container, false)
    }


}
