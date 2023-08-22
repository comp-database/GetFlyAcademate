package com.getfly.technologies.ui.academics

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.getfly.technologies.MainViewModelFactory
import com.getfly.technologies.databinding.FragmentAcademicsBinding
import com.getfly.technologies.model.AcademateRepository
import com.getfly.technologies.model.EaseBuzzRepository
import com.getfly.technologies.model.response.SemDetailsResponse
import com.getfly.technologies.model.viewmodel.MainScreenViewModel

class AcademicsFragment : Fragment() {

    private lateinit var binding: FragmentAcademicsBinding
    private lateinit var viewModel: AcademicsFragmentViewModel

    //Shared preferences to store user uid
    companion object {
        const val SHARED_PREFS_NAME = "AcademateLogin"
        lateinit var sharedPreferences: SharedPreferences
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAcademicsBinding.inflate(layoutInflater)

        sharedPreferences = requireActivity().applicationContext.getSharedPreferences(
            AcademicsFragment.SHARED_PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val uidSP = AcademicsFragment.sharedPreferences.getInt("uid", 0)
        if (uidSP != 0) {
            val repository = AcademateRepository()
            val repositoryTwo = EaseBuzzRepository()
            val viewModelFactory = MainViewModelFactory(repository, repositoryTwo)

            val connectivityManager = this.requireActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {

                viewModel =
                    ViewModelProvider(this, viewModelFactory)[AcademicsFragmentViewModel::class.java]


                //Get Education details
                viewModel.getEducationDetails(uidSP.toString())
                viewModel.EducationDetailsResponse.observe(this.requireActivity()) { EducationDetailsResponseStatus ->
                    if (EducationDetailsResponseStatus.isSuccessful) {
                        binding.tvSscSchoolName.text =
                            "SSC Seat NO : " + EducationDetailsResponseStatus.body()?.data?.get(0)?.sscSeatNumber
                        binding.tvHscSchoolName.text =
                            "HSC Seat NO : " + EducationDetailsResponseStatus.body()?.data?.get(0)?.hscSeatYear
                        binding.tvSscMarksObtained.text =
                            "SSC Marks : " + EducationDetailsResponseStatus.body()?.data?.get(0)?.sscMarks
                        binding.tvHscMarksObtained.text =
                            "HSC Marks : " + EducationDetailsResponseStatus.body()?.data?.get(0)?.hscMarks
                        binding.tvSscPercentage.text =
                            "SSC Percentage : " + EducationDetailsResponseStatus.body()?.data?.get(0)?.sscPercentage + " %"
                        binding.tvHscPercentage.text =
                            "HSC Percentage : " + EducationDetailsResponseStatus.body()?.data?.get(0)?.hscPercentage + " %"
                    } else {
                        Toast.makeText(this.requireActivity(), "Unable to fetch details", Toast.LENGTH_SHORT).show()
                    }
                }

                //Get SEM details
                viewModel.getSemDetails(uidSP.toString())
                viewModel.SemDetailsResponse.observe(this.requireActivity()) { SemDetailsResponseStatus ->
                    if (SemDetailsResponseStatus.isSuccessful) {
                        val semSize: Int = SemDetailsResponseStatus.body()!!.entrance!!.size
                        Log.d("vishal", SemDetailsResponseStatus.body()!!.entrance!!.toString())
                        Log.d("vishal", semSize.toString())

                        //Logic for sem details of each sem
                        if (semSize == 1) {
                            val sem1 = SemDetailsResponseStatus!!.body()!!.entrance.get(0)
                            val sem2 = SemDetailsResponseStatus!!.body()!!.entrance.get(1)
                            binding.sem2.visibility = View.GONE
                            binding.sem3.visibility = View.GONE
                            binding.sem4.visibility = View.GONE
                            binding.sem5.visibility = View.GONE
                            binding.sem6.visibility = View.GONE
                            binding.sem7.visibility = View.GONE
                            binding.sem8.visibility = View.GONE

                            binding.tvSem1.text =
                                "Sem NO : " + sem1.semNumber
                            binding.tvSem1SchoolName.text =
                                "Stud Id : " + sem1.studId
                            binding.tvSem1MarksObtained.text =
                                "Grade Obtained : " + sem1.gradeObtained
                            binding.tvSem1Percentage.text =
                                "Total KT : " + sem1.totalKt
                        }
                        if (semSize == 2) {
                            val sem1 = SemDetailsResponseStatus!!.body()!!.entrance.get(0)
                            val sem2 = SemDetailsResponseStatus!!.body()!!.entrance.get(1)
//                        val sem3 = SemDetailsResponseStatus!!.body()!!.entrance.get(2)
//                        val sem4 = SemDetailsResponseStatus!!.body()!!.entrance.get(3)
//                        val sem5 = SemDetailsResponseStatus!!.body()!!.entrance.get(4)
//                        val sem6 = SemDetailsResponseStatus!!.body()!!.entrance.get(5)
//                        val sem7 = SemDetailsResponseStatus!!.body()!!.entrance.get(6)
//                        val sem8 = SemDetailsResponseStatus!!.body()!!.entrance.get(7)

//                        binding.sem2.visibility = View.GONE
                            binding.sem3.visibility = View.GONE
                            binding.sem4.visibility = View.GONE
                            binding.sem5.visibility = View.GONE
                            binding.sem6.visibility = View.GONE
                            binding.sem7.visibility = View.GONE
                            binding.sem8.visibility = View.GONE



                            binding.apply {
                                tvSem1.text = "Sem NO : " + sem1.semNumber
                                tvSem1SchoolName.text = "Stud Id : " + sem1.studId
                                tvSem1MarksObtained.text = "Grade Obtained : " + sem1.gradeObtained
                                tvSem1Percentage.text = "Total KT : " + sem1.totalKt

                                tvSem2.text = "Sem NO : " + sem2.semNumber
                                tvSem2SchoolName.text = "Stud Id : " + sem2.studId
                                tvSem2MarksObtained.text = "Grade Obtained : " + sem2.gradeObtained
                                tvSem2Percentage.text = "Total KT : " + sem2.totalKt

//                            tvSem3.text = "Sem NO : " + sem3.semNumber
//                            tvSem3SchoolName.text = "Stud Id : " + sem3.studId
//                            tvSem3MarksObtained.text = "Grade Obtained : " + sem3.gradeObtained
//                            tvSem3Percentage.text = "Total KT : " + sem3.totalKt
//
//                            tvSem4.text = "Sem NO : " + sem4.semNumber
//                            tvSem4SchoolName.text = "Stud Id : " + sem4.studId
//                            tvSem4MarksObtained.text = "Grade Obtained : " + sem4.gradeObtained
//                            tvSem4Percentage.text = "Total KT : " + sem4.totalKt
//
//                            tvSem5.text = "Sem NO : " + sem5.semNumber
//                            tvSem5SchoolName.text = "Stud Id : " + sem5.studId
//                            tvSem5MarksObtained.text = "Grade Obtained : " + sem5.gradeObtained
//                            tvSem5Percentage.text = "Total KT : " + sem5.totalKt
//
//                            tvSem6.text = "Sem NO : " + sem6.semNumber
//                            tvSem6SchoolName.text = "Stud Id : "+ sem6.studId
//                            tvSem6MarksObtained.text = "Grade Obtained : " + sem6.gradeObtained
//                            tvSem6Percentage.text = "Total KT : " + sem6.totalKt
//
//                            tvSem7.text = "Sem NO : " + sem7.semNumber
//                            tvSem7SchoolName.text = "Stud Id : " + sem7.studId
//                            tvSem7MarksObtained.text = "Grade Obtained : " + sem7.gradeObtained
//                            tvSem7Percentage.text = "Total KT : " + sem7.totalKt
//
//                            tvSem8.text = "Sem NO : " + sem8.semNumber
//                            tvSem8SchoolName.text = "Stud Id : " + sem8.studId
//                            tvSem8MarksObtained.text = "Grade Obtained : " + sem8.gradeObtained
//                            tvSem8Percentage.text = "Total KT : " + sem8.totalKt
                            }
                        }
                        if (semSize == 3) {
                            val sem1 = SemDetailsResponseStatus!!.body()!!.entrance.get(0)
                            val sem2 = SemDetailsResponseStatus!!.body()!!.entrance.get(1)
                            val sem3 = SemDetailsResponseStatus!!.body()!!.entrance.get(2)
//                        val sem4 = SemDetailsResponseStatus!!.body()!!.entrance.get(3)
//                        val sem5 = SemDetailsResponseStatus!!.body()!!.entrance.get(4)
//                        val sem6 = SemDetailsResponseStatus!!.body()!!.entrance.get(5)
//                        val sem7 = SemDetailsResponseStatus!!.body()!!.entrance.get(6)
//                        val sem8 = SemDetailsResponseStatus!!.body()!!.entrance.get(7)

//                        binding.sem2.visibility = View.GONE
//                        binding.sem3.visibility = View.GONE
                            binding.sem4.visibility = View.GONE
                            binding.sem5.visibility = View.GONE
                            binding.sem6.visibility = View.GONE
                            binding.sem7.visibility = View.GONE
                            binding.sem8.visibility = View.GONE



                            binding.apply {
                                tvSem1.text = "Sem NO : " + sem1.semNumber
                                tvSem1SchoolName.text = "Stud Id : " + sem1.studId
                                tvSem1MarksObtained.text = "Grade Obtained : " + sem1.gradeObtained
                                tvSem1Percentage.text = "Total KT : " + sem1.totalKt

                                tvSem2.text = "Sem NO : " + sem2.semNumber
                                tvSem2SchoolName.text = "Stud Id : " + sem2.studId
                                tvSem2MarksObtained.text = "Grade Obtained : " + sem2.gradeObtained
                                tvSem2Percentage.text = "Total KT : " + sem2.totalKt

                                tvSem3.text = "Sem NO : " + sem3.semNumber
                                tvSem3SchoolName.text = "Stud Id : " + sem3.studId
                                tvSem3MarksObtained.text = "Grade Obtained : " + sem3.gradeObtained
                                tvSem3Percentage.text = "Total KT : " + sem3.totalKt

//                            tvSem4.text = "Sem NO : " + sem4.semNumber
//                            tvSem4SchoolName.text = "Stud Id : " + sem4.studId
//                            tvSem4MarksObtained.text = "Grade Obtained : " + sem4.gradeObtained
//                            tvSem4Percentage.text = "Total KT : " + sem4.totalKt
//
//                            tvSem5.text = "Sem NO : " + sem5.semNumber
//                            tvSem5SchoolName.text = "Stud Id : " + sem5.studId
//                            tvSem5MarksObtained.text = "Grade Obtained : " + sem5.gradeObtained
//                            tvSem5Percentage.text = "Total KT : " + sem5.totalKt
//
//                            tvSem6.text = "Sem NO : " + sem6.semNumber
//                            tvSem6SchoolName.text = "Stud Id : "+ sem6.studId
//                            tvSem6MarksObtained.text = "Grade Obtained : " + sem6.gradeObtained
//                            tvSem6Percentage.text = "Total KT : " + sem6.totalKt
//
//                            tvSem7.text = "Sem NO : " + sem7.semNumber
//                            tvSem7SchoolName.text = "Stud Id : " + sem7.studId
//                            tvSem7MarksObtained.text = "Grade Obtained : " + sem7.gradeObtained
//                            tvSem7Percentage.text = "Total KT : " + sem7.totalKt
//
//                            tvSem8.text = "Sem NO : " + sem8.semNumber
//                            tvSem8SchoolName.text = "Stud Id : " + sem8.studId
//                            tvSem8MarksObtained.text = "Grade Obtained : " + sem8.gradeObtained
//                            tvSem8Percentage.text = "Total KT : " + sem8.totalKt
                            }
                        }
                        if (semSize == 4) {
                            val sem1 = SemDetailsResponseStatus!!.body()!!.entrance.get(0)
                            val sem2 = SemDetailsResponseStatus!!.body()!!.entrance.get(1)
                            val sem3 = SemDetailsResponseStatus!!.body()!!.entrance.get(2)
                            val sem4 = SemDetailsResponseStatus!!.body()!!.entrance.get(3)
//                        val sem5 = SemDetailsResponseStatus!!.body()!!.entrance.get(4)
//                        val sem6 = SemDetailsResponseStatus!!.body()!!.entrance.get(5)
//                        val sem7 = SemDetailsResponseStatus!!.body()!!.entrance.get(6)
//                        val sem8 = SemDetailsResponseStatus!!.body()!!.entrance.get(7)

//                        binding.sem2.visibility = View.GONE
//                        binding.sem3.visibility = View.GONE
//                        binding.sem4.visibility = View.GONE
                            binding.sem5.visibility = View.GONE
                            binding.sem6.visibility = View.GONE
                            binding.sem7.visibility = View.GONE
                            binding.sem8.visibility = View.GONE



                            binding.apply {
                                tvSem1.text = "Sem NO : " + sem1.semNumber
                                tvSem1SchoolName.text = "Stud Id : " + sem1.studId
                                tvSem1MarksObtained.text = "Grade Obtained : " + sem1.gradeObtained
                                tvSem1Percentage.text = "Total KT : " + sem1.totalKt

                                tvSem2.text = "Sem NO : " + sem2.semNumber
                                tvSem2SchoolName.text = "Stud Id : " + sem2.studId
                                tvSem2MarksObtained.text = "Grade Obtained : " + sem2.gradeObtained
                                tvSem2Percentage.text = "Total KT : " + sem2.totalKt

                                tvSem3.text = "Sem NO : " + sem3.semNumber
                                tvSem3SchoolName.text = "Stud Id : " + sem3.studId
                                tvSem3MarksObtained.text = "Grade Obtained : " + sem3.gradeObtained
                                tvSem3Percentage.text = "Total KT : " + sem3.totalKt

                                tvSem4.text = "Sem NO : " + sem4.semNumber
                                tvSem4SchoolName.text = "Stud Id : " + sem4.studId
                                tvSem4MarksObtained.text = "Grade Obtained : " + sem4.gradeObtained
                                tvSem4Percentage.text = "Total KT : " + sem4.totalKt

//                            tvSem5.text = "Sem NO : " + sem5.semNumber
//                            tvSem5SchoolName.text = "Stud Id : " + sem5.studId
//                            tvSem5MarksObtained.text = "Grade Obtained : " + sem5.gradeObtained
//                            tvSem5Percentage.text = "Total KT : " + sem5.totalKt
//
//                            tvSem6.text = "Sem NO : " + sem6.semNumber
//                            tvSem6SchoolName.text = "Stud Id : "+ sem6.studId
//                            tvSem6MarksObtained.text = "Grade Obtained : " + sem6.gradeObtained
//                            tvSem6Percentage.text = "Total KT : " + sem6.totalKt
//
//                            tvSem7.text = "Sem NO : " + sem7.semNumber
//                            tvSem7SchoolName.text = "Stud Id : " + sem7.studId
//                            tvSem7MarksObtained.text = "Grade Obtained : " + sem7.gradeObtained
//                            tvSem7Percentage.text = "Total KT : " + sem7.totalKt
//
//                            tvSem8.text = "Sem NO : " + sem8.semNumber
//                            tvSem8SchoolName.text = "Stud Id : " + sem8.studId
//                            tvSem8MarksObtained.text = "Grade Obtained : " + sem8.gradeObtained
//                            tvSem8Percentage.text = "Total KT : " + sem8.totalKt
                            }
                        }
                        if (semSize == 5) {
                            val sem1 = SemDetailsResponseStatus!!.body()!!.entrance.get(0)
                            val sem2 = SemDetailsResponseStatus!!.body()!!.entrance.get(1)
                            val sem3 = SemDetailsResponseStatus!!.body()!!.entrance.get(2)
                            val sem4 = SemDetailsResponseStatus!!.body()!!.entrance.get(3)
                            val sem5 = SemDetailsResponseStatus!!.body()!!.entrance.get(4)
//                        val sem6 = SemDetailsResponseStatus!!.body()!!.entrance.get(5)
//                        val sem7 = SemDetailsResponseStatus!!.body()!!.entrance.get(6)
//                        val sem8 = SemDetailsResponseStatus!!.body()!!.entrance.get(7)

//                        binding.sem2.visibility = View.GONE
//                        binding.sem3.visibility = View.GONE
//                        binding.sem4.visibility = View.GONE
//                        binding.sem5.visibility = View.GONE
                            binding.sem6.visibility = View.GONE
                            binding.sem7.visibility = View.GONE
                            binding.sem8.visibility = View.GONE



                            binding.apply {
                                tvSem1.text = "Sem NO : " + sem1.semNumber
                                tvSem1SchoolName.text = "Stud Id : " + sem1.studId
                                tvSem1MarksObtained.text = "Grade Obtained : " + sem1.gradeObtained
                                tvSem1Percentage.text = "Total KT : " + sem1.totalKt

                                tvSem2.text = "Sem NO : " + sem2.semNumber
                                tvSem2SchoolName.text = "Stud Id : " + sem2.studId
                                tvSem2MarksObtained.text = "Grade Obtained : " + sem2.gradeObtained
                                tvSem2Percentage.text = "Total KT : " + sem2.totalKt

                                tvSem3.text = "Sem NO : " + sem3.semNumber
                                tvSem3SchoolName.text = "Stud Id : " + sem3.studId
                                tvSem3MarksObtained.text = "Grade Obtained : " + sem3.gradeObtained
                                tvSem3Percentage.text = "Total KT : " + sem3.totalKt

                                tvSem4.text = "Sem NO : " + sem4.semNumber
                                tvSem4SchoolName.text = "Stud Id : " + sem4.studId
                                tvSem4MarksObtained.text = "Grade Obtained : " + sem4.gradeObtained
                                tvSem4Percentage.text = "Total KT : " + sem4.totalKt

                                tvSem5.text = "Sem NO : " + sem5.semNumber
                                tvSem5SchoolName.text = "Stud Id : " + sem5.studId
                                tvSem5MarksObtained.text = "Grade Obtained : " + sem5.gradeObtained
                                tvSem5Percentage.text = "Total KT : " + sem5.totalKt

//                            tvSem6.text = "Sem NO : " + sem6.semNumber
//                            tvSem6SchoolName.text = "Stud Id : "+ sem6.studId
//                            tvSem6MarksObtained.text = "Grade Obtained : " + sem6.gradeObtained
//                            tvSem6Percentage.text = "Total KT : " + sem6.totalKt
//
//                            tvSem7.text = "Sem NO : " + sem7.semNumber
//                            tvSem7SchoolName.text = "Stud Id : " + sem7.studId
//                            tvSem7MarksObtained.text = "Grade Obtained : " + sem7.gradeObtained
//                            tvSem7Percentage.text = "Total KT : " + sem7.totalKt
//
//                            tvSem8.text = "Sem NO : " + sem8.semNumber
//                            tvSem8SchoolName.text = "Stud Id : " + sem8.studId
//                            tvSem8MarksObtained.text = "Grade Obtained : " + sem8.gradeObtained
//                            tvSem8Percentage.text = "Total KT : " + sem8.totalKt
                            }
                        }
                        if (semSize == 6) {
                            val sem1 = SemDetailsResponseStatus!!.body()!!.entrance.get(0)
                            val sem2 = SemDetailsResponseStatus!!.body()!!.entrance.get(1)
                            val sem3 = SemDetailsResponseStatus!!.body()!!.entrance.get(2)
                            val sem4 = SemDetailsResponseStatus!!.body()!!.entrance.get(3)
                            val sem5 = SemDetailsResponseStatus!!.body()!!.entrance.get(4)
                            val sem6 = SemDetailsResponseStatus!!.body()!!.entrance.get(5)
//                        val sem7 = SemDetailsResponseStatus!!.body()!!.entrance.get(6)
//                        val sem8 = SemDetailsResponseStatus!!.body()!!.entrance.get(7)

//                        binding.sem2.visibility = View.GONE
//                        binding.sem3.visibility = View.GONE
//                        binding.sem4.visibility = View.GONE
//                        binding.sem5.visibility = View.GONE
//                        binding.sem6.visibility = View.GONE
                            binding.sem7.visibility = View.GONE
                            binding.sem8.visibility = View.GONE



                            binding.apply {
                                tvSem1.text = "Sem NO : " + sem1.semNumber
                                tvSem1SchoolName.text = "Stud Id : " + sem1.studId
                                tvSem1MarksObtained.text = "Grade Obtained : " + sem1.gradeObtained
                                tvSem1Percentage.text = "Total KT : " + sem1.totalKt

                                tvSem2.text = "Sem NO : " + sem2.semNumber
                                tvSem2SchoolName.text = "Stud Id : " + sem2.studId
                                tvSem2MarksObtained.text = "Grade Obtained : " + sem2.gradeObtained
                                tvSem2Percentage.text = "Total KT : " + sem2.totalKt

                                tvSem3.text = "Sem NO : " + sem3.semNumber
                                tvSem3SchoolName.text = "Stud Id : " + sem3.studId
                                tvSem3MarksObtained.text = "Grade Obtained : " + sem3.gradeObtained
                                tvSem3Percentage.text = "Total KT : " + sem3.totalKt

                                tvSem4.text = "Sem NO : " + sem4.semNumber
                                tvSem4SchoolName.text = "Stud Id : " + sem4.studId
                                tvSem4MarksObtained.text = "Grade Obtained : " + sem4.gradeObtained
                                tvSem4Percentage.text = "Total KT : " + sem4.totalKt

                                tvSem5.text = "Sem NO : " + sem5.semNumber
                                tvSem5SchoolName.text = "Stud Id : " + sem5.studId
                                tvSem5MarksObtained.text = "Grade Obtained : " + sem5.gradeObtained
                                tvSem5Percentage.text = "Total KT : " + sem5.totalKt

                                tvSem6.text = "Sem NO : " + sem6.semNumber
                                tvSem6SchoolName.text = "Stud Id : " + sem6.studId
                                tvSem6MarksObtained.text = "Grade Obtained : " + sem6.gradeObtained
                                tvSem6Percentage.text = "Total KT : " + sem6.totalKt

//                            tvSem7.text = "Sem NO : " + sem7.semNumber
//                            tvSem7SchoolName.text = "Stud Id : " + sem7.studId
//                            tvSem7MarksObtained.text = "Grade Obtained : " + sem7.gradeObtained
//                            tvSem7Percentage.text = "Total KT : " + sem7.totalKt
//
//                            tvSem8.text = "Sem NO : " + sem8.semNumber
//                            tvSem8SchoolName.text = "Stud Id : " + sem8.studId
//                            tvSem8MarksObtained.text = "Grade Obtained : " + sem8.gradeObtained
//                            tvSem8Percentage.text = "Total KT : " + sem8.totalKt
                            }
                        }
                        if (semSize == 7) {
                            val sem1 = SemDetailsResponseStatus!!.body()!!.entrance.get(0)
                            val sem2 = SemDetailsResponseStatus!!.body()!!.entrance.get(1)
                            val sem3 = SemDetailsResponseStatus!!.body()!!.entrance.get(2)
                            val sem4 = SemDetailsResponseStatus!!.body()!!.entrance.get(3)
                            val sem5 = SemDetailsResponseStatus!!.body()!!.entrance.get(4)
                            val sem6 = SemDetailsResponseStatus!!.body()!!.entrance.get(5)
                            val sem7 = SemDetailsResponseStatus!!.body()!!.entrance.get(6)
//                        val sem8 = SemDetailsResponseStatus!!.body()!!.entrance.get(7)

//                        binding.sem2.visibility = View.GONE
//                        binding.sem3.visibility = View.GONE
//                        binding.sem4.visibility = View.GONE
//                        binding.sem5.visibility = View.GONE
//                        binding.sem6.visibility = View.GONE
//                        binding.sem7.visibility = View.GONE
                            binding.sem8.visibility = View.GONE



                            binding.apply {
                                tvSem1.text = "Sem NO : " + sem1.semNumber
                                tvSem1SchoolName.text = "Stud Id : " + sem1.studId
                                tvSem1MarksObtained.text = "Grade Obtained : " + sem1.gradeObtained
                                tvSem1Percentage.text = "Total KT : " + sem1.totalKt

                                tvSem2.text = "Sem NO : " + sem2.semNumber
                                tvSem2SchoolName.text = "Stud Id : " + sem2.studId
                                tvSem2MarksObtained.text = "Grade Obtained : " + sem2.gradeObtained
                                tvSem2Percentage.text = "Total KT : " + sem2.totalKt

                                tvSem3.text = "Sem NO : " + sem3.semNumber
                                tvSem3SchoolName.text = "Stud Id : " + sem3.studId
                                tvSem3MarksObtained.text = "Grade Obtained : " + sem3.gradeObtained
                                tvSem3Percentage.text = "Total KT : " + sem3.totalKt

                                tvSem4.text = "Sem NO : " + sem4.semNumber
                                tvSem4SchoolName.text = "Stud Id : " + sem4.studId
                                tvSem4MarksObtained.text = "Grade Obtained : " + sem4.gradeObtained
                                tvSem4Percentage.text = "Total KT : " + sem4.totalKt

                                tvSem5.text = "Sem NO : " + sem5.semNumber
                                tvSem5SchoolName.text = "Stud Id : " + sem5.studId
                                tvSem5MarksObtained.text = "Grade Obtained : " + sem5.gradeObtained
                                tvSem5Percentage.text = "Total KT : " + sem5.totalKt

                                tvSem6.text = "Sem NO : " + sem6.semNumber
                                tvSem6SchoolName.text = "Stud Id : " + sem6.studId
                                tvSem6MarksObtained.text = "Grade Obtained : " + sem6.gradeObtained
                                tvSem6Percentage.text = "Total KT : " + sem6.totalKt

                                tvSem7.text = "Sem NO : " + sem7.semNumber
                                tvSem7SchoolName.text = "Stud Id : " + sem7.studId
                                tvSem7MarksObtained.text = "Grade Obtained : " + sem7.gradeObtained
                                tvSem7Percentage.text = "Total KT : " + sem7.totalKt


                            }
                        }
                        if (semSize == 8) {
                            val sem1 = SemDetailsResponseStatus!!.body()!!.entrance.get(0)
                            val sem2 = SemDetailsResponseStatus!!.body()!!.entrance.get(1)
                            val sem3 = SemDetailsResponseStatus!!.body()!!.entrance.get(2)
                            val sem4 = SemDetailsResponseStatus!!.body()!!.entrance.get(3)
                            val sem5 = SemDetailsResponseStatus!!.body()!!.entrance.get(4)
                            val sem6 = SemDetailsResponseStatus!!.body()!!.entrance.get(5)
                            val sem7 = SemDetailsResponseStatus!!.body()!!.entrance.get(6)
                            val sem8 = SemDetailsResponseStatus!!.body()!!.entrance.get(7)

//                        binding.sem2.visibility = View.GONE
//                        binding.sem3.visibility = View.GONE
//                        binding.sem4.visibility = View.GONE
//                        binding.sem5.visibility = View.GONE
//                        binding.sem6.visibility = View.GONE
//                        binding.sem7.visibility = View.GONE
//                        binding.sem8.visibility = View.GONE


                            binding.apply {
                                tvSem1.text = "Sem NO : " + sem1.semNumber
                                tvSem1SchoolName.text = "Stud Id : " + sem1.studId
                                tvSem1MarksObtained.text = "Grade Obtained : " + sem1.gradeObtained
                                tvSem1Percentage.text = "Total KT : " + sem1.totalKt

                                tvSem2.text = "Sem NO : " + sem2.semNumber
                                tvSem2SchoolName.text = "Stud Id : " + sem2.studId
                                tvSem2MarksObtained.text = "Grade Obtained : " + sem2.gradeObtained
                                tvSem2Percentage.text = "Total KT : " + sem2.totalKt

                                tvSem3.text = "Sem NO : " + sem3.semNumber
                                tvSem3SchoolName.text = "Stud Id : " + sem3.studId
                                tvSem3MarksObtained.text = "Grade Obtained : " + sem3.gradeObtained
                                tvSem3Percentage.text = "Total KT : " + sem3.totalKt

                                tvSem4.text = "Sem NO : " + sem4.semNumber
                                tvSem4SchoolName.text = "Stud Id : " + sem4.studId
                                tvSem4MarksObtained.text = "Grade Obtained : " + sem4.gradeObtained
                                tvSem4Percentage.text = "Total KT : " + sem4.totalKt

                                tvSem5.text = "Sem NO : " + sem5.semNumber
                                tvSem5SchoolName.text = "Stud Id : " + sem5.studId
                                tvSem5MarksObtained.text = "Grade Obtained : " + sem5.gradeObtained
                                tvSem5Percentage.text = "Total KT : " + sem5.totalKt

                                tvSem6.text = "Sem NO : " + sem6.semNumber
                                tvSem6SchoolName.text = "Stud Id : " + sem6.studId
                                tvSem6MarksObtained.text = "Grade Obtained : " + sem6.gradeObtained
                                tvSem6Percentage.text = "Total KT : " + sem6.totalKt

                                tvSem7.text = "Sem NO : " + sem7.semNumber
                                tvSem7SchoolName.text = "Stud Id : " + sem7.studId
                                tvSem7MarksObtained.text = "Grade Obtained : " + sem7.gradeObtained
                                tvSem7Percentage.text = "Total KT : " + sem7.totalKt

                                tvSem8.text = "Sem NO : " + sem8.semNumber
                                tvSem8SchoolName.text = "Stud Id : " + sem8.studId
                                tvSem8MarksObtained.text = "Grade Obtained : " + sem8.gradeObtained
                                tvSem8Percentage.text = "Total KT : " + sem8.totalKt
                            }

                        }
                    } else {
                        Toast.makeText(this.requireActivity(), "Unable to fetch details", Toast.LENGTH_SHORT).show()
                    }
                }

            } else {
                // Device is not connected to the internet
                Toast.makeText(
                    this.requireActivity(),
                    "No internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return binding.root
    }
}