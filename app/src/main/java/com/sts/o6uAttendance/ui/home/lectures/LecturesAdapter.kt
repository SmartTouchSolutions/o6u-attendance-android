package com.sts.o6uAttendance.ui.home.lectures

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sts.o6uAttendance.R
import com.sts.o6uAttendance.core.App
import com.sts.o6uAttendance.data.model.Lecture
import com.sts.o6uAttendance.ui.util.BindableAdapter
import kotlinx.android.synthetic.main.lecture_item_row.view.*


class LecturesAdapter : RecyclerView.Adapter<LecturesAdapter.LecturesHolder>(),
    BindableAdapter<MutableList<Lecture>> {


    private lateinit var lectures: MutableList<Lecture>

    override fun setData(data: MutableList<Lecture>) {
        lectures = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LecturesHolder {
        val inflater = LayoutInflater.from(parent.context)
        return LecturesHolder(
            inflater.inflate(R.layout.lecture_item_row, parent, false)
        )
    }

    override fun getItemCount() = lectures.size


    class LecturesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(
            lecture: Lecture,
            b: Boolean,
            position: Int
        ) {
            if (b) {
                itemView.lecture_cell.backgroundTintList =
                    ContextCompat.getColorStateList(App.instance, R.color.colorPrimary)
            } else {
                itemView.lecture_cell.backgroundTintList =
                    ContextCompat.getColorStateList(App.instance, R.color.colorPrimaryDark)
                itemView.lecture_name_text.setTextColor(
                    ContextCompat.getColorStateList(
                        App.instance,
                        R.color.white
                    )
                )
            }
            itemView.lecture_name_text.text = "Lecture Number: $position"
            itemView.lecture_date_text.text = "${lecture.createdAt}"
            itemView.lecture_attendance_text.text = "${lecture.countAllStudents}"
//            itemView.lecture_cell.setOnClickListener {callBack.notifier(lecture)}
        }
    }

    override fun onBindViewHolder(holder: LecturesHolder, position: Int) {
        if (position % 2 == 0)
            holder.bind(lectures[position], true,position)
        else
            holder.bind(lectures[position], false, position)
    }
}