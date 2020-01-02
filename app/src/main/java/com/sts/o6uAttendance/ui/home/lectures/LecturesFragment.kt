package com.sts.o6uAttendance.ui.home.lectures


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
import com.sts.o6uAttendance.data.model.Lecture
import com.sts.o6uAttendance.databinding.FragmentLecturesBinding
import com.sts.o6uAttendance.ui.home.HomeViewModel
import com.sts.o6uAttendance.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.fragment_subjects.*

class LecturesFragment : Fragment() {
//    private val TAG: String = LecturesFragment::class.java.simpleName

    companion object {
        val FRAGMENT_NAME: String = LecturesFragment::class.java.name
    }

    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }
    private val adapter: LecturesAdapter by lazy { LecturesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLecturesBinding =
            inflate(inflater, R.layout.fragment_lectures, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLectures().observe(this, Observer {
            initView(it)
        })
        with(viewModel) {
//            welcomeMessage.observe(this@LecturesFragment, Observer {
//                welcome_text.text = it
//            })
            lecturesData.observe(this@LecturesFragment, Observer {
                Toast.makeText(context, "toast shows in every rotation", Toast.LENGTH_SHORT).show()
                initView(it)
            })
            showToast.observe(this@LecturesFragment, Observer {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()

            })
            error.observe(this@LecturesFragment, Observer {
                if (it == "token") {
                    startActivity(Intent(activity, SplashActivity::class.java))
                    activity?.finish()
                }
                progressBar_home.visibility = View.GONE
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            })
        }
    }

    private fun initView(it: MutableList<Lecture>?) {
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
            Toast.makeText(context, context?.getString(R.string.empty_lecture_list), Toast.LENGTH_LONG)
                .show()
        }
    }
}