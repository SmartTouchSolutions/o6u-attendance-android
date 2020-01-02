package com.sts.o6uAttendance.demo.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.o6uAttendance.R
import com.sts.o6uAttendance.data.model.Attendance
import kotlinx.android.synthetic.main.attendance_item_row.view.*


class AttendanceDemoAdapter(
    private var attendance: List<Attendance?>?,
    private val lecturesCount: Int?
) : RecyclerView.Adapter<AttendanceDemoAdapter.ViewHolder>() {
    @SuppressLint("DefaultLocale")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.attendance_item_row, parent, false)
        attendance = attendance?.sortedBy { it?.student?.username?.toLowerCase() }
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val studentAttend = attendance!![position]

        holder.itemView.student_name_text.text = studentAttend?.student?.username
        val attend: Int = studentAttend?.countAllLectures ?: 0
        val allLectures: Int = lecturesCount ?: 0
        holder.itemView.student_attend_count_text.text = attend.toString()
        holder.itemView.student_absent_count_text.text = allLectures.minus(attend).toString()


    }

    override fun getItemCount(): Int {
        return attendance!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}