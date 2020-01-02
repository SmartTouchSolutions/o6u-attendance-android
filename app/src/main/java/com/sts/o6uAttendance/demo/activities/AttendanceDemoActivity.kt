@file:Suppress("SpellCheckingInspection")

package com.sts.o6uAttendance.demo.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sts.o6uAttendance.R
import com.sts.o6uAttendance.core.App
import com.sts.o6uAttendance.data.db.AccountData
import com.sts.o6uAttendance.data.model.Attendance
import com.sts.o6uAttendance.data.model.AttendanceDto
import com.sts.o6uAttendance.data.model.ResponseDto
import com.sts.o6uAttendance.data.network.ApiService
import com.sts.o6uAttendance.demo.adapters.AttendanceDemoAdapter
import kotlinx.android.synthetic.main.activity_attendance_demo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AttendanceDemoActivity : AppCompatActivity() {

    private lateinit var attendanceDto: AttendanceDto

    private val apiService: ApiService = App.api!!

    private var subjectId: Int = 0
    private var lectureId: Int = 0
    private var subjectName: String = ""

    private val permission = 1
    private var mClss: Class<*>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance_demo)

        subjectId = intent?.getIntExtra("selected_subject_id", -1)!!
        lectureId = intent?.getIntExtra("selected_lecture_id", -1)!!
        subjectName = intent?.getStringExtra("selected_subject_name")!!

        init()

    }

    override fun onResume() {
        super.onResume()
        try {
            getAttendance()
        } catch (e: Exception) {
            val snackBar: Snackbar = Snackbar.make(
                (parentLayout)!!,
                "error Happened",
                Snackbar.LENGTH_INDEFINITE
            )
            snackBar.setAction("Try Again ? ") { getAttendance() }
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
        val linearLayoutManager = LinearLayoutManager(this@AttendanceDemoActivity)
        rv_demo_attendance.layoutManager = linearLayoutManager

    }

    private fun getAttendance() {

        apiService.getAttendance(
            Authorization = AccountData().getUserToken(App.instance),
            id = subjectId.toString()
        )
            .enqueue(object
                : Callback<ResponseDto<AttendanceDto?>?> {
                override fun onFailure(
                    call: Call<ResponseDto<AttendanceDto?>?>?,
                    t: Throwable?
                ) {
                    val snackBar: Snackbar = Snackbar.make(
                        (parentLayout)!!,
                        "error Happened",
                        Snackbar.LENGTH_INDEFINITE
                    )
                    snackBar.setAction("Try Again ? ") { getAttendance() }
                    snackBar.show()
                }

                @SuppressLint("DefaultLocale")
                override fun onResponse(
                    call: Call<ResponseDto<AttendanceDto?>?>?,
                    response: Response<ResponseDto<AttendanceDto?>?>?
                ) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                when {
                                    response.body()!!.success -> {
                                        attendanceDto = response.body()!!.data!!
                                        if (attendanceDto.attendance!!.isNotEmpty() ||
                                            attendanceDto.studentsWithNoAttendance!!.isNotEmpty()
                                        ) {
                                            val attendanceDemoAdapter = AttendanceDemoAdapter(
                                                makeAttendanceList(attendanceDto),
                                                attendanceDto.countAllLecturesOfSubject
                                            )
                                            progressBar_Layout.visibility = View.GONE
                                            rv_demo_attendance.adapter = attendanceDemoAdapter
                                        } else {
                                            progressBar_Layout.visibility = View.GONE
                                            val snackBar: Snackbar = Snackbar.make(
                                                (parentLayout)!!,
                                                "there is no Attendance Yet",
                                                Snackbar.LENGTH_INDEFINITE
                                            )
                                            snackBar.setAction("Refresh") { getAttendance() }
                                            snackBar.show()

                                        }
                                    }
                                    else -> {
                                        if (response.body()!!.error.toLowerCase().contains("token")) {

                                            progressBar_Layout.visibility = View.GONE
                                            Toast.makeText(
                                                this@AttendanceDemoActivity,
                                                "you have been logged in in another place",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            App.logout(
                                                this@AttendanceDemoActivity,
                                                progressBar_Layout
                                            )
                                        }
                                        progressBar_Layout.visibility = View.GONE
                                        val snackBar: Snackbar = Snackbar.make(
                                            (parentLayout)!!,
                                            response.body()!!.error,
                                            Snackbar.LENGTH_INDEFINITE
                                        )
                                        snackBar.setAction("Refresh") { getAttendance() }
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
                            snackBar.setAction("Try Again") { getAttendance() }
                            snackBar.show()
                        }
                    }
                }

            })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_attendance, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.add_attendance_button -> launchActivity(SimpleScannerDemoActivity::class.java)
        }
        return false
    }

    private fun launchActivity(clss: Class<*>) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            mClss = clss
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                permission
            )
        } else {
            val intent = Intent(this, clss)
            startActivityForResult(intent, permission)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            permission -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mClss != null) {
                        val intent = Intent(this, mClss)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Please grant camera permission to use the Scanner",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == permission) {
            //Write your code if there's no result
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val result = data?.getStringExtra("result")
                    addAttendance(result!!)
                }
                Activity.RESULT_CANCELED -> { //Write your code if there's no result
                }
            }
        }
    }


    private fun makeAttendanceList(attendanceDto: AttendanceDto): List<Attendance?>? {
        if (attendanceDto.studentsWithNoAttendance.isNullOrEmpty()) {
            return attendanceDto.attendance
        }
        val attendanceList: MutableList<Attendance?>? =
            attendanceDto.attendance as MutableList<Attendance?>?
        for (s in attendanceDto.studentsWithNoAttendance!!) {
            attendanceList!!.add(
                Attendance(0, s, s?.id)
            )
        }
        return attendanceList
    }

    private fun addAttendance(studentCode: String) {
        apiService.createAttendance(
            Authorization = AccountData().getUserToken(App.instance),
            id = subjectId.toString(),
            lecture_id = lectureId.toString(),
            student_code = studentCode

        )
            .enqueue(object
                : Callback<ResponseDto<String?>?> {
                override fun onFailure(
                    call: Call<ResponseDto<String?>?>?,
                    t: Throwable?
                ) {
                    val snackBar: Snackbar = Snackbar.make(
                        (parentLayout)!!,
                        "error Happened",
                        Snackbar.LENGTH_INDEFINITE
                    )
                    snackBar.setAction("Try Again ? ") { addAttendance(studentCode) }
                    snackBar.show()
                }

                @SuppressLint("DefaultLocale")
                override fun onResponse(
                    call: Call<ResponseDto<String?>?>?,
                    response: Response<ResponseDto<String?>?>?
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
                                        getAttendance()
                                    }
                                    else -> {
                                        if (response.body()!!.error.toLowerCase().contains("token")) {
                                            progressBar_Layout.visibility = View.GONE
                                            Toast.makeText(
                                                this@AttendanceDemoActivity,
                                                "you have been logged in in another place",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            App.logout(
                                                this@AttendanceDemoActivity,
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

}
