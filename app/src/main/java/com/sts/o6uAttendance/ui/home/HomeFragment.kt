package com.sts.o6uAttendance.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sts.o6uAttendance.R
import com.sts.o6uAttendance.data.model.SubjectDto
import com.sts.o6uAttendance.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : androidx.fragment.app.Fragment(){
    private val TAG: String = HomeFragment::class.java.simpleName
    companion object {
        val FRAGMENT_NAME: String = HomeFragment::class.java.name
    }

    private val viewModel: HomeViewModel by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }
    val adapter : HomeAdapter by lazy { HomeAdapter(mutableListOf()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding : FragmentHomeBinding = inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            homeData.observe(this@HomeFragment, Observer {
                Toast.makeText(context,"toast shows in every rotation",Toast.LENGTH_SHORT).show()
                initView(it)
            }
            )
            showToast.observe(this@HomeFragment, Observer {
                Toast.makeText(context,"$it",Toast.LENGTH_LONG).show()

            })
            error.observe(this@HomeFragment, Observer {
                progressBar_home.visibility= View.GONE
                Toast.makeText(context, "$it", Toast.LENGTH_LONG).show()
            })
        }
    }

    private fun initView(it: SubjectDto?) {
        rv_main_home.layoutManager =
            LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        rv_main_home.adapter = adapter
        progressBar_home.visibility= View.GONE
        if (it!!.subjects.isNotEmpty()) {
            adapter.clear()
            adapter.add(it.subjects)

        }else{
            Toast.makeText(context, context?.getString(R.string.empty_list), Toast.LENGTH_LONG).show()
        }
    }
}