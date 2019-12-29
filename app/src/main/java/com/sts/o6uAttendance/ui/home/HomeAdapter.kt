package com.sts.o6uAttendance.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.sts.o6uAttendance.data.model.Subject
import com.sts.o6uAttendance.databinding.SubjectItemRowBinding

import com.sts.o6uAttendance.ui.util.DataBindingViewHolder

class HomeAdapter(
    private var items: MutableList<Subject> = arrayListOf()
) : RecyclerView.Adapter<HomeAdapter.SimpleHolder>() {
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SimpleHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleHolder {
        val binding  =
            SubjectItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SimpleHolder(binding)
    }

    inner class SimpleHolder(dataBinding: ViewDataBinding)
        : DataBindingViewHolder<Subject>(dataBinding)  {
        override fun onBind(t: Subject): Unit = with(t) {
            dataBinding.setVariable(id,t)
        }
    }

    fun add(list: MutableList<Subject>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }
}