package com.sts.o6uAttendance.demo.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sts.o6uAttendance.R
import com.sts.o6uAttendance.core.App
import com.sts.o6uAttendance.data.db.AccountData
import com.sts.o6uAttendance.data.model.Lecture
import com.sts.o6uAttendance.data.model.ResponseDto
import com.sts.o6uAttendance.data.network.ApiService
import com.sts.o6uAttendance.demo.adapters.LecturesDemoAdapter
import com.sts.o6uAttendance.ui.util.CallBack
import kotlinx.android.synthetic.main.activity_lectures_demo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LecturesDemoActivity : AppCompatActivity(), CallBack<Lecture> {

    private lateinit var lectures: List<Lecture>
    private val apiService: ApiService = App.api!!
//    val user = SavePrefs<User>(App.instance, User::class.java).load()
    private var subjectId: Int = 0
    private var subjectName: String = ""

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lectures_demo)

        subjectId = intent?.getIntExtra("selected_subject_id", -1)!!
        subjectName = intent?.getStringExtra("selected_subject_name")!!.capitalize()

        welcomeMessage()

        init()


    }

    override fun onResume() {
        super.onResume()
        try {
            getLectures()
        } catch (e: Exception) {
            val snackBar: Snackbar = Snackbar.make(
                (parentLayout)!!,
                "error Happened",
                Snackbar.LENGTH_INDEFINITE
            )
            snackBar.setAction("Try Again ? ") { getLectures() }
            snackBar.show()
        }
    }


    private fun init() { // toolbar settings
//        val textView: TextView = toolbar.findViewById(R.id.title)
//        textView.setText(string.main_activity_page_title)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.back_arrow)
        supportActionBar!!.title = ""
        //

        progressBar_Layout.visibility = View.VISIBLE
        // Recycler View Setup
        val linearLayoutManager = LinearLayoutManager(this@LecturesDemoActivity)
        rv_demo_lectures.layoutManager = linearLayoutManager

    }

    @SuppressLint("SetTextI18n")
    private fun welcomeMessage() {
        lecture_subject_name_text.text = "Subject : $subjectName"

    }

    private fun getLectures() {

        apiService.getLectures(
            Authorization = AccountData().getUserToken(App.instance),
            id = subjectId.toString()
        )
            .enqueue(object
                : Callback<ResponseDto<MutableList<Lecture>?>?> {
                override fun onFailure(
                    call: Call<ResponseDto<MutableList<Lecture>?>?>?,
                    t: Throwable?
                ) {
                    val snackBar: Snackbar = Snackbar.make(
                        (parentLayout)!!,
                        "error Happened",
                        Snackbar.LENGTH_INDEFINITE
                    )
                    snackBar.setAction("Try Again ? ") { getLectures() }
                    snackBar.show()
                }

                @SuppressLint("DefaultLocale")
                override fun onResponse(
                    call: Call<ResponseDto<MutableList<Lecture>?>?>?,
                    response: Response<ResponseDto<MutableList<Lecture>?>?>?
                ) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                when {
                                    response.body()!!.success -> {
                                        lectures = response.body()!!.data!!
                                        if (lectures.isNotEmpty()) {
                                            val lecturesDemoAdapter = LecturesDemoAdapter(
                                                this@LecturesDemoActivity,
                                                lectures
                                            )
                                            progressBar_Layout.visibility = View.GONE
                                            rv_demo_lectures.adapter = lecturesDemoAdapter
                                        } else {
                                            progressBar_Layout.visibility = View.GONE
                                            val snackBar: Snackbar = Snackbar.make(
                                                (parentLayout)!!,
                                                getString(R.string.empty_lecture_list),
                                                Snackbar.LENGTH_INDEFINITE
                                            )
                                            snackBar.setAction("Refresh") { getLectures() }
                                            snackBar.show()

                                        }
                                    }
                                    else -> {
                                        if (response.body()!!.error.toLowerCase().contains("token")) {

                                            progressBar_Layout.visibility = View.GONE
                                            Toast.makeText(
                                                this@LecturesDemoActivity,
                                                "you have been logged in in another place",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            App.logout(
                                                this@LecturesDemoActivity,
                                                progressBar_Layout
                                            )
                                        }
                                        val snackBar: Snackbar = Snackbar.make(
                                            (parentLayout)!!,
                                            response.body()!!.error,
                                            Snackbar.LENGTH_INDEFINITE
                                        )
                                        snackBar.setAction("Try Again") { getLectures() }
                                        snackBar.show()

                                    }
                                }
                            }
                        } else {
                            val snackBar: Snackbar = Snackbar.make(
                                (parentLayout)!!,
                                response.errorBody()!!.string(),
                                Snackbar.LENGTH_INDEFINITE
                            )
                            snackBar.setAction("Try Again") { getLectures() }
                            snackBar.show()
                        }
                    }
                }

            })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_lecture, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.add_lecture_button -> addLecture()
        }
        return false
    }

    private fun addLecture() {
        apiService.createLectures(
            Authorization = AccountData().getUserToken(App.instance),
            id = subjectId.toString()
        )
            .enqueue(object
                : Callback<ResponseDto<String>?> {
                override fun onFailure(
                    call: Call<ResponseDto<String>?>?,
                    t: Throwable?
                ) {
                    val snackBar: Snackbar = Snackbar.make(
                        (parentLayout)!!,
                        "error Happened",
                        Snackbar.LENGTH_INDEFINITE
                    )
                    snackBar.setAction("Try Again ? ") { addLecture() }
                    snackBar.show()
                }

                @SuppressLint("DefaultLocale")
                override fun onResponse(
                    call: Call<ResponseDto<String>?>?,
                    response: Response<ResponseDto<String>?>?
                ) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                when {
                                    response.body()!!.success -> {
                                        val snackBar: Snackbar = Snackbar.make(
                                            (parentLayout)!!,
                                            response.body()!!.data!!,
                                            Snackbar.LENGTH_INDEFINITE
                                        )
                                        snackBar.show()
                                        getLectures()
                                    }
                                    else -> {
                                        if (response.body()!!.error.toLowerCase().contains("token")) {
                                            progressBar_Layout.visibility = View.GONE
                                            Toast.makeText(
                                                this@LecturesDemoActivity,
                                                "you have been logged in in another place",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            App.logout(
                                                this@LecturesDemoActivity,
                                                progressBar_Layout
                                            )
                                        }
                                        val snackBar: Snackbar = Snackbar.make(
                                            (parentLayout)!!,
                                            response.body()!!.error,
                                            Snackbar.LENGTH_INDEFINITE
                                        )
                                        snackBar.show()

                                    }
                                }
                            }
                        } else {
                            val snackBar: Snackbar = Snackbar.make(
                                (parentLayout)!!,
                                response.errorBody()!!.string(),
                                Snackbar.LENGTH_INDEFINITE
                            )
                            snackBar.show()
                        }
                    }
                }

            })

    }

    override fun notifier(obj: Lecture) {
            val intent = Intent(this, AttendanceDemoActivity::class.java)
            intent.putExtra("selected_subject_id", subjectId)
            intent.putExtra("selected_lecture_id", obj.id)
            intent.putExtra("selected_subject_name", subjectName)
            startActivity(intent)

    }

}


