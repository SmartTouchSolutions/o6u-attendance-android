package com.sts.o6uAttendance.ui.home.subjects


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sts.o6uAttendance.R
import com.sts.o6uAttendance.core.FragmentFactory
import com.sts.o6uAttendance.data.model.Subject
import com.sts.o6uAttendance.databinding.FragmentSubjectsBinding
import com.sts.o6uAttendance.ui.home.HomeViewModel
import com.sts.o6uAttendance.ui.home.lectures.LecturesFragment
import com.sts.o6uAttendance.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.fragment_subjects.*

class SubjectsFragment : Fragment() {
//    private val TAG: String = SubjectsFragment::class.java.simpleName

    companion object {
        val FRAGMENT_NAME: String = SubjectsFragment::class.java.name
    }

    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }
    private val adapter: SubjectAdapter by lazy { SubjectAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSubjectsBinding =
            inflate(inflater, R.layout.fragment_subjects, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            welcomeMessage.observe(this@SubjectsFragment, Observer {
                welcome_text.text = it
            })
            subjectsData.observe(this@SubjectsFragment, Observer {
                //Toast.makeText(context, "toast shows in every rotation", Toast.LENGTH_SHORT).show()
                initView(it)
            })
            subjectSelected.observe(this@SubjectsFragment, Observer {
                showFragment(it)
            })
            showToast.observe(this@SubjectsFragment, Observer {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()

            })
            error.observe(this@SubjectsFragment, Observer {
                if (it == "token") {
                    startActivity(Intent(activity, SplashActivity::class.java))
                    activity?.finish()
                }
                progressBar_home.visibility = View.GONE
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            })
        }
    }

    private fun initView(it: MutableList<Subject>?) {
        rv_main_home.layoutManager =
            LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        rv_main_home.adapter = adapter
        progressBar_home.visibility = View.GONE

        if (it!!.isNotEmpty()) {
//            adapter.subjects.clear()
            adapter.setData(it)
        } else {
            Toast.makeText(context, context?.getString(R.string.empty_subject_list), Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun showFragment(subject: Subject?) {
        if (subject != null) {
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()

            fragmentTransaction!!
                .replace(
                    R.id.container,
                    FragmentFactory.getLectureFragment(activity?.supportFragmentManager!!),
                    LecturesFragment.FRAGMENT_NAME
                )
            fragmentTransaction.addToBackStack(FRAGMENT_NAME)
            fragmentTransaction.commit()
        }
    }

}