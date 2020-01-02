package com.sts.o6uAttendance.ui.home.subjects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sts.o6uAttendance.R
import com.sts.o6uAttendance.core.App
import com.sts.o6uAttendance.data.model.Subject
import com.sts.o6uAttendance.ui.util.BindableAdapter
import kotlinx.android.synthetic.main.subject_item_row.view.*


class SubjectAdapter :
    RecyclerView.Adapter<SubjectAdapter.SubjectHolder>(),
    BindableAdapter<MutableList<Subject>> {


    private lateinit var subjects: MutableList<Subject>

    override fun setData(data: MutableList<Subject>) {
        subjects = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SubjectHolder(
            inflater.inflate(R.layout.subject_item_row, parent, false)
        )
    }

    override fun getItemCount() = subjects.size


    class SubjectHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            subject: Subject,
            b: Boolean
//            callBack: CallBack<Subject>
        ) {
            if (b) {
                itemView.subject_cell.backgroundTintList =
                    ContextCompat.getColorStateList(App.instance, R.color.colorPrimary)
            } else {
                itemView.subject_cell.backgroundTintList =
                    ContextCompat.getColorStateList(App.instance, R.color.colorPrimaryDark)
                itemView.subject_name_text.setTextColor(
                    ContextCompat.getColorStateList(
                        App.instance,
                        R.color.white
                    )
                )
            }
            itemView.subject_name_text.text = "${subject.subjectName?.name}"
            itemView.subject_lectures_count_text.text = "${subject.lecturesCount}"
//            itemView.setOnClickListener { callBack.notifier(subject) }
        }
    }

    override fun onBindViewHolder(holder: SubjectHolder, position: Int) {
        if (position % 2 == 0)
            holder.bind(subjects[position], true)
        else
            holder.bind(subjects[position], false)
    }

}