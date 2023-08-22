package com.getfly.technologies

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.getfly.technologies.databinding.ActivityAdmissionSectionBinding
import com.getfly.technologies.model.AcademateRepository
import com.getfly.technologies.model.EaseBuzzRepository
import com.getfly.technologies.model.viewmodel.MainScreenViewModel
import java.io.FileOutputStream

class AdmissionScreen : AppCompatActivity() {

    private lateinit var binding: ActivityAdmissionSectionBinding
    private lateinit var viewModel: MainScreenViewModel

    private var pendingCS : Int = 0
    private var pendingIT : Int = 0
    private var pendingAIDS : Int = 0
    private var pendingEXTC : Int = 0

    //For CSV file download
    private val PERMISSION_REQUEST_CODE = 123
    private val rawStringResponse = "your,raw,string,response,here"
    private val fileName = "converted_excel_file"

    //Shared preferences to store user uid
    companion object {
        const val SHARED_PREFS_NAME = "AcademateLogin"
        lateinit var sharedPreferences: SharedPreferences
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdmissionSectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = applicationContext.getSharedPreferences(
            AdmissionScreen.SHARED_PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val uidSP = AdmissionScreen.sharedPreferences.getInt("uid", 0)
        if (uidSP != 0) {
            val repository = AcademateRepository()
            val repositoryTwo = EaseBuzzRepository()
            val viewModelFactory = MainViewModelFactory(repository,repositoryTwo)

            viewModel = ViewModelProvider(this, viewModelFactory)[MainScreenViewModel::class.java]

            //Get Faculty dashboard details
            viewModel.getfacultyDashboard(uidSP.toString())
            viewModel.FacultyDashboardResponse.observe(this) { FacultyDashboardResponseStatus ->
                if (FacultyDashboardResponseStatus.isSuccessful) {
                    binding.tvFirstYearCompleted.text =
                        "Total : " + FacultyDashboardResponseStatus.body()?.count1.toString()
                    binding.tvDseCompleted.text =
                        "Total : " + FacultyDashboardResponseStatus.body()?.count2.toString()
                    binding.tvSecondYearCompleted.text =
                        "Total : " + FacultyDashboardResponseStatus.body()?.count3.toString()
                    binding.tvThirdYearCompleted.text =
                        "Total : " + FacultyDashboardResponseStatus.body()?.count4.toString()
                    binding.tvFinalYearCompleted.text =
                        "Total : " + FacultyDashboardResponseStatus.body()?.count5.toString()
                    binding.tvComputerTotal.text =
                        "Total : " + FacultyDashboardResponseStatus.body()?.cs.toString()
                    binding.tvItTotal.text =
                        "Total : " + FacultyDashboardResponseStatus.body()?.it.toString()
                    binding.tvAidsTotal.text =
                        "Total : " + FacultyDashboardResponseStatus.body()?.aids.toString()
                    binding.tvExtcTotal.text =
                        "Total : " + FacultyDashboardResponseStatus.body()?.extc.toString()

                    pendingCS = FacultyDashboardResponseStatus.body()?.cs.toString().toInt()
                    pendingIT = FacultyDashboardResponseStatus.body()?.it.toString().toInt()
                    pendingAIDS = FacultyDashboardResponseStatus.body()?.aids.toString().toInt()
                    pendingEXTC = FacultyDashboardResponseStatus.body()?.extc.toString().toInt()


                    viewModel.getPendingApplication(uidSP.toString())
                    viewModel.pendingApplicationsLiveData.observe(this, Observer { pendingApplications ->
                        binding.tvComputerPending.text =
                            "Pending : " + pendingCS.minus(pendingApplications.get(0).pcs.toString().toInt())
                        binding.tvItPending.text =
                            "Pending : " + pendingIT.minus(pendingApplications.get(0).pit.toString().toInt())
                        binding.tvAidsPending.text =
                            "Pending : " + pendingAIDS.minus(pendingApplications.get(0).paids.toString().toInt())
                        binding.tvExtcPending.text =
                            "Pending : " + pendingEXTC.minus(pendingApplications.get(0).pextc.toString().toInt())
                    })
                }
            }


            binding.downloadComputer.setOnClickListener {
                val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)
                } else {
                    downloadExcelFile()
                }
            }


            //Logout button
            binding.btnLogout.setOnClickListener {
                val editor = AdmissionScreen.sharedPreferences.edit()
                editor.putBoolean("isStudent", false)
                editor.putBoolean("isAdmission", false)
                editor.apply()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

        }
    }

    private fun downloadExcelFile() {
        ExcelUtils.convertStringToExcel(this, rawStringResponse, fileName)
        Toast.makeText(this, "Excel file saved", Toast.LENGTH_SHORT).show()
    }

    // Handle permission request result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadExcelFile()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}