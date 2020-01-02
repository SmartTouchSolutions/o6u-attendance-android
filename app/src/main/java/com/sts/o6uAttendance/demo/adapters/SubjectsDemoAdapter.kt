package com.sts.o6uAttendance.demo.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sts.o6uAttendance.R
import com.sts.o6uAttendance.core.App
import com.sts.o6uAttendance.data.model.Subject
import com.sts.o6uAttendance.ui.util.CallBack
import kotlinx.android.synthetic.main.subject_item_row.view.*


class SubjectsDemoAdapter(
    private val callBack: CallBack<Subject>,
    private var subjects: List<Subject>?
) : RecyclerView.Adapter<SubjectsDemoAdapter.ViewHolder>() {
    @SuppressLint("DefaultLocale")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.subject_item_row, parent, false)
        subjects = subjects?.sortedBy { it.subjectName?.name?.toLowerCase() }
        return ViewHolder(view)
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (position % 2 == 1) {
            holder.itemView.subject_cell.backgroundTintList =
                ContextCompat.getColorStateList(App.instance, R.color.colorPrimaryDark)
            holder.itemView.subject_name_text.setTextColor(
                ContextCompat.getColorStateList(
                    App.instance,
                    R.color.white
                )
            )
        }
        val subject: Subject = subjects!![position]
        holder.itemView.subject_name_text.text = subject.subjectName?.name?.capitalize()
        holder.itemView.subject_lectures_count_text.text = subject.lecturesCount.toString()
        holder.itemView.subject_cell.setOnClickListener { callBack.notifier(subject) }

    }

    override fun getItemCount(): Int {
        return subjects!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

//    init {
//        subjects?.toMutableList()?.sort()
//    }
}