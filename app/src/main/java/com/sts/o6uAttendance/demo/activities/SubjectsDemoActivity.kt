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
import com.sts.o6uAttendance.ui.util.SavePrefs
import com.sts.o6uAttendance.R
import com.sts.o6uAttendance.core.App
import com.sts.o6uAttendance.data.db.AccountData
import com.sts.o6uAttendance.data.model.ResponseDto
import com.sts.o6uAttendance.data.model.Subject
import com.sts.o6uAttendance.data.model.User
import com.sts.o6uAttendance.data.network.ApiService
import com.sts.o6uAttendance.demo.adapters.SubjectsDemoAdapter
import com.sts.o6uAttendance.ui.util.CallBack
import kotlinx.android.synthetic.main.activity_subjects_demo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubjectsDemoActivity : AppCompatActivity(), CallBack<Subject> {


    private lateinit var subjects: MutableList<Subject>
    private val apiService: ApiService = App.api!!
    val user = SavePrefs<User>(App.instance, User::class.java).load()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subjects_demo)

        welcomeMessage()
        init()


    }

    override fun onResume() {
        super.onResume()
        try {
            getSubjects()
        } catch (e: Exception) {
            val snackBar: Snackbar = Snackbar.make(
                (parentLayout)!!,
                "error Happened",
                Snackbar.LENGTH_INDEFINITE
            )
            snackBar.setAction("Try Again ? ") { getSubjects() }
            snackBar.show()
        }

    }

    private fun init() { // toolbar settings
//        val textView: TextView = toolbar.findViewById(R.id.title)
//        textView.setText(string.main_activity_page_title)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar!!.title = ""
        //

        progressBar_Layout.visibility = View.VISIBLE
        // Recycler View Setup
        val linearLayoutManager = LinearLayoutManager(this@SubjectsDemoActivity)
        rv_demo_subjects.layoutManager = linearLayoutManager

    }

    @SuppressLint("DefaultLocale", "SetTextI18n")
    private fun welcomeMessage() {
        if (user.type == "doctor") {
            welcome_text.text = "Welcome Dr/ ${user.name.capitalize()}"
        } else {
            welcome_text.text = "Welcome Mr/ ${user.name.capitalize()}"
        }
    }

    private fun getSubjects() {

        apiService.getSubjects(Authorization = AccountData().getUserToken(App.instance))
            .enqueue(object
                : Callback<ResponseDto<MutableList<Subject>?>?> {
                override fun onFailure(
                    call: Call<ResponseDto<MutableList<Subject>?>?>?,
                    t: Throwable?
                ) {
                    val snackBar: Snackbar = Snackbar.make(
                        (parentLayout)!!,
                        "error Happened",
                        Snackbar.LENGTH_INDEFINITE
                    )
                    snackBar.setAction("Try Again ? ") { getSubjects() }
                    snackBar.show()
                }

                @SuppressLint("DefaultLocale")
                override fun onResponse(
                    call: Call<ResponseDto<MutableList<Subject>?>?>?,
                    response: Response<ResponseDto<MutableList<Subject>?>?>?
                ) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                when {
                                    response.body()!!.success -> {
                                        subjects = response.body()!!.data!!
                                        if (subjects.isNotEmpty()) {
                                            val subjectsDemoAdapter = SubjectsDemoAdapter(
                                                this@SubjectsDemoActivity,
                                                subjects
                                            )
                                            progressBar_Layout.visibility = View.GONE
                                            rv_demo_subjects.adapter = subjectsDemoAdapter
                                        }else{
                                            progressBar_Layout.visibility = View.GONE
                                            val snackBar: Snackbar = Snackbar.make(
                                                (parentLayout)!!,
                                                getString(R.string.empty_subject_list),
                                                Snackbar.LENGTH_INDEFINITE
                                            )
                                            snackBar.setAction("Refresh") { getSubjects() }
                                            snackBar.show()

                                        }
                                    }
                                    else -> {
                                        if (response.body()!!.error.toLowerCase().contains("token")) {
                                            progressBar_Layout.visibility = View.GONE
                                            Toast.makeText(
                                                this@SubjectsDemoActivity,
                                                "you have been logged in in another place",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            logout()

                                        }
                                        val snackBar: Snackbar = Snackbar.make(
                                            (parentLayout)!!,
                                            response.body()!!.error,
                                            Snackbar.LENGTH_INDEFINITE
                                        )
                                        snackBar.setAction("Try Again") { getSubjects() }
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
                            snackBar.setAction("Try Again") { getSubjects() }
                            snackBar.show()
                        }
                    }
                }

            })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_subject, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //R.id.change_language -> changeLanguage()
            R.id.logout_button -> logout()
        }
        return false
    }

    private fun destroyToken() {
        apiService.logout(Authorization = AccountData().getUserToken(App.instance))
            .enqueue(object : Callback<ResponseDto<String?>?> {
                override fun onFailure(call: Call<ResponseDto<String?>?>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<ResponseDto<String?>?>,
                    response: Response<ResponseDto<String?>?>
                ) {

                }
            })
    }

    private fun logout() {

        destroyToken()
        App.logout(this@SubjectsDemoActivity,progressBar_Layout)
    }


    override fun notifier(obj: Subject) {
        val intent = Intent(this, LecturesDemoActivity::class.java)
        intent.putExtra("selected_subject_id", obj.id)
        intent.putExtra("selected_subject_name", obj.subjectName?.name)
        startActivity(intent)
    }

}
