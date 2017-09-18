package com.rafaltrzcinski.dribshots.shots

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rafaltrzcinski.dribshots.R
import com.rafaltrzcinski.dribshots.databinding.ItemShotBinding
import com.rafaltrzcinski.dribshots.rest.model.Shot
import java.util.*

class ShotsAdapter : RecyclerView.Adapter<ShotsAdapter.ShotViewHolder>() {

    private var items: ArrayList<Shot> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShotViewHolder {
        val itemShotBinding = DataBindingUtil.inflate<ItemShotBinding>(
                LayoutInflater.from(parent.context), R.layout.item_shot, parent, false
        )
        return ShotViewHolder(itemShotBinding)
    }

    override fun onBindViewHolder(holder: ShotViewHolder, position: Int) {
        val itemBinding = holder.itemShotBinding

        val layoutParams = itemBinding?.shotCard?.layoutParams

//        val randomWidth = Random().nextInt(400 + 1 - 300) + 300


        if (layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            layoutParams.apply {
                height = if (position != 0) 350 else 400
//                width = randomWidth
//                height = 350
            }
        }

        itemBinding?.viewModel = ShotViewModel(items[position])



    }

    override fun getItemCount() = items.size

    fun addItems(shots: List<Shot>) {
        items.clear()
        items.addAll(shots)
        notifyDataSetChanged()
    }


    class ShotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemShotBinding: ItemShotBinding? = null

        constructor(itemView: ItemShotBinding) : this(itemView.root) {
            this.itemShotBinding = itemView
        }
    }

}