package com.sts.o6uAttendance.ui.home

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.simple.sample.data.model.Food
import com.simple.sample.databinding.FoodItemRowBinding
import com.simple.sample.ui.util.DataBindingViewHolder
import com.simple.sample.BR.item

class HomeAdapter(
    private var items: MutableList<Food> = arrayListOf<Food>()
) : RecyclerView.Adapter<HomeAdapter.SimpleHolder>() {
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SimpleHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleHolder {
        val binding  = FoodItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SimpleHolder(binding)
    }

    inner class SimpleHolder(dataBinding: ViewDataBinding)
        : DataBindingViewHolder<Food>(dataBinding)  {
        override fun onBind(t: Food): Unit = with(t) {
            dataBinding.setVariable(item,t)
        }
    }

    fun add(list: MutableList<Food>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }
}