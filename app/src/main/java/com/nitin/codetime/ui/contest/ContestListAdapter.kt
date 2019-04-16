package com.nitin.codetime.ui.contest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nitin.codetime.R
import com.nitin.codetime.data.db.ContestShortInfoModel
import kotlinx.android.synthetic.main.row_contest_list.view.*

class ContestListAdapter :
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
        holder.bind(getItem(position))
    }

    class ContestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(contestInfo: ContestShortInfoModel) {
            itemView.contest_logo.setImageResource(getLogo(contestInfo.resName))
            itemView.contest_res.text = getResourceName(contestInfo.resName)
            itemView.contest_name.text = contestInfo.name
        }

        private fun getResourceName(name: String): String {
            return name.split(".com")[0].capitalize()
        }

        private fun getLogo(res: String): Int {
            //TODO: add proper icons
            return when (res) {
                "codechef.com" -> R.drawable.ic_launcher_foreground
                "topcoder.com" -> R.drawable.ic_launcher_background
                else -> R.drawable.ic_live
            }
        }
    }
}