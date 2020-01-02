package com.sts.o6uAttendance.demo.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.o6uAttendance.R
import com.sts.o6uAttendance.data.model.Lecture
import com.sts.o6uAttendance.ui.util.CallBack
import kotlinx.android.synthetic.main.lecture_item_row.view.*


class LecturesDemoAdapter(private val callBack: CallBack<Lecture>, private var lectures: List<Lecture>?)
    : RecyclerView.Adapter<LecturesDemoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lecture_item_row, parent, false)
        lectures = lectures?.sortedBy { it.createdAt }
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


//        if (position % 2 == 1) {
//            holder.itemView.lecture_cell.backgroundTintList =
//                ContextCompat.getColorStateList(App.instance, R.color.colorPrimaryDark)
//            holder.itemView.lecture_name_text.setTextColor(
//                ContextCompat.getColorStateList(
//                    App.instance,
//                    R.color.white
//                )
//            )
//            holder.itemView.lecture_date_text.setTextColor(
//                ContextCompat.getColorStateList(
//                    App.instance,
//                    R.color.white
//                )
//            )
//        }
            val lecture= lectures!![position]
            holder.itemView.lecture_name_text.text = "Lecture Number: ${position+1}"
            holder.itemView.lecture_attendance_text.text = lecture.countAllStudents.toString()
            holder.itemView.lecture_date_text.text = lecture.createdAt
            holder.itemView.lecture_cell.setOnClickListener { callBack.notifier(lecture) }

    }

    override fun getItemCount(): Int {
        return lectures!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}