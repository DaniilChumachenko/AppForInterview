package com.chumachenko.appforinterview.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chumachenko.appforinterview.R
import com.chumachenko.appforinterview.data.repository.model.StayPlusItem
import com.chumachenko.appforinterview.presentation.support.DiffUtilCallback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_stay_plus.view.*
import java.util.*


class StayPlusAdapter(
    private var list: ArrayList<StayPlusItem>
) : RecyclerView.Adapter<StayPlusAdapter.ItemTagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTagViewHolder =
        ItemTagViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_stay_plus, parent, false)
        )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemTagViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun updateList(newList: List<StayPlusItem>)= DiffUtil.calculateDiff(DiffUtilCallback(this.list, newList)).let {
        list.clear()
        list.addAll(newList)
        it.dispatchUpdatesTo(this)
    }

    inner class ItemTagViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(stayPlusItem: StayPlusItem) = itemView.apply {
            Picasso.get().load(stayPlusItem.image).into(ivStayPlus)
        }

    }
}