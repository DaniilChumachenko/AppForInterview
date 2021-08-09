package com.chumachenko.appforinterview.presentation.support

import androidx.recyclerview.widget.DiffUtil
import com.chumachenko.appforinterview.data.repository.model.StayPlusItem


class DiffUtilCallback(
    private val oldList: List<StayPlusItem>,
    private val newList: List<StayPlusItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].image == newList[newItemPosition].image
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.image == new.image
    }
}