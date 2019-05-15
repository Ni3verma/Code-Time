package com.nitin.codetime.ui


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.nitin.codetime.R
import kotlinx.android.synthetic.main.fragment_credits.*

class CreditsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).setBottomNavVisibility(View.GONE)
        return inflater.inflate(R.layout.fragment_credits, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        clist.setOnClickListener {
            val url = "https://clist.by/api/v1/doc"
            openURL(url)
        }

        hdodenhof.setOnClickListener {
            val url = "https://github.com/hdodenhof/CircleImageView"
            openURL(url)
        }
    }

    private fun openURL(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }


}
