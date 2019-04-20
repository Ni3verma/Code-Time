package com.nitin.codetime.ui.contest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nitin.codetime.R
import com.nitin.codetime.data.db.ContestShortInfoModel
import kotlinx.android.synthetic.main.row_contest_list.view.*

class ContestListAdapter(private val clickListener: (View, Int) -> Unit) :
    ListAdapter<ContestShortInfoModel, ContestListAdapter.ContestViewHolder>(ContestDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ContestViewHolder(
            inflater.inflate(
                R.layout.row_contest_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ContestViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ContestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(contestInfo: ContestShortInfoModel, clickListener: (View, Int) -> Unit) {
            itemView.contest_logo.setImageResource(getLogo(contestInfo.resName))
            itemView.contest_res.text = getResourceName(contestInfo.resName)
            itemView.contest_name.text = contestInfo.name
            itemView.setOnClickListener { clickListener(itemView, contestInfo.id) }
        }

        private fun getResourceName(name: String): String {
            return name.split(".com")[0].capitalize()
        }

        private fun getLogo(res: String): Int {
            return when (res) {
                "codechef.com" -> R.drawable.codechef_logo
                "topcoder.com" -> R.drawable.topcoder_logo
                "hackerrank.com" -> R.drawable.hackerrank_logo
                "hackerearth.com" -> R.drawable.hackerearth_logo
                "codeforces.com" -> R.drawable.codeforces_logo
                // TODO: change with app icon
                else -> R.drawable.ic_live
            }
        }
    }
}