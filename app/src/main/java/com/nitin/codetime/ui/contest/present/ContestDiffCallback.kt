package com.nitin.codetime.ui.contest.present

import androidx.recyclerview.widget.DiffUtil
import com.nitin.codetime.data.db.ContestShortInfoModel

class ContestDiffCallback : DiffUtil.ItemCallback<ContestShortInfoModel>() {
    override fun areItemsTheSame(oldItem: ContestShortInfoModel, newItem: ContestShortInfoModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ContestShortInfoModel, newItem: ContestShortInfoModel): Boolean {
        return oldItem == newItem
    }

}