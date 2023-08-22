package com.getfly.technologies.ui.profile

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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.getfly.technologies.LoginActivity
import com.getfly.technologies.MainViewModelFactory
import com.getfly.technologies.R
import com.getfly.technologies.databinding.FragmentProfileBinding
import com.getfly.technologies.model.AcademateRepository
import com.getfly.technologies.model.EaseBuzzRepository

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileFragmentViewModel
    var pweActivityResultLauncher: ActivityResultLauncher<Intent>? = null


    //Shared preferences to store user uid
    companion object {
        const val SHARED_PREFS_NAME = "AcademateLogin"
        lateinit var sharedPreferences: SharedPreferences
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        ProfileFragment.sharedPreferences =
            requireActivity().applicationContext.getSharedPreferences(
                ProfileFragment.SHARED_PREFS_NAME,
                Context.MODE_PRIVATE
            )

        val uidSP = ProfileFragment.sharedPreferences.getInt("uid", 0)
        if (uidSP != 0) {
            val repository = AcademateRepository()
            val repositoryTwo = EaseBuzzRepository()
            val viewModelFactory = MainViewModelFactory(repository, repositoryTwo)

            val connectivityManager = this.requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {

                Log.d("UID", uidSP.toString())
                viewModel =
                    ViewModelProvider(this, viewModelFactory)[ProfileFragmentViewModel::class.java]


                //Personal details request
                viewModel.getPersonalDetails(uidSP.toString())
                viewModel.PersonalDetailsResponse.observe(this.requireActivity()) { PersonalDetailsResponseStatus ->
                    if (PersonalDetailsResponseStatus.isSuccessful) {
//                    Log.d("Atharv", PersonalDetailsResponseStatus.body().toString())
                        binding.tvName.text = PersonalDetailsResponseStatus.body()?.radd!![0].name
                        binding.tvCaste.text =
                            "Caste : " + PersonalDetailsResponseStatus.body()?.radd!![0].caste
                        binding.tvDob.text =
                            "DOB : " + PersonalDetailsResponseStatus.body()?.radd!![0].dob.substring(
                                0,
                                10
                            )

                    } else {
                        Toast.makeText(this.requireActivity(), "Unable to fetch details", Toast.LENGTH_SHORT).show()
                    }
                }


                //Current Course details request
                viewModel.getCurrentCourseDetails(uidSP.toString())
                viewModel.CurrentCourseDetailsResponse.observe(this.requireActivity()) { CurrentCourseDetailsResponseStatus ->
                    if (CurrentCourseDetailsResponseStatus.isSuccessful) {
                        binding.tvStudId.text =
                            "ID N0 : " + CurrentCourseDetailsResponseStatus.body()?.data!![0].studClgId
                        binding.tvGrNo.text =
                            "GR N0 : " + CurrentCourseDetailsResponseStatus.body()?.data!![0].grNumber
                        binding.tvBranch.text =
                            when (CurrentCourseDetailsResponseStatus.body()?.data!![0].branchId) {
                                1 -> "Branch : " + "Computer Engineering"
                                2 -> "Branch : " + "Artificial Intelligence and Data Science"
                                3 -> "Branch : " + "Electronics and Telecommunication"
                                4 -> "Branch : " + "Information Technology"
                                5 -> "Branch : " + "Computer Engineering"
                                else ->
                                    (Toast.makeText(
                                        this.requireActivity(),
                                        "Unable to fetch details",
                                        Toast.LENGTH_SHORT
                                    ).show())
                                        .toString()
                            }
                        binding.tvYear.text =
                            "Year : " + CurrentCourseDetailsResponseStatus.body()?.data!![0].academicYear
                    } else {
                        Toast.makeText(this.requireActivity(), "Unable to fetch details", Toast.LENGTH_SHORT).show()
                    }
                }

                //Get documents
                viewModel.getDocDetails(uidSP.toString())
                viewModel.DocDeatailResponse.observe(this.requireActivity()) { DocDetailsResponseStatus ->
                    if (DocDetailsResponseStatus.isSuccessful) {
                        val url = DocDetailsResponseStatus.body()?.docs?.photo
                        Glide.with(this)
                            .load(url)
                            .apply(RequestOptions.bitmapTransform(CircleCrop()))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.baseline_person_24)
                            .into(binding.profilePic)
                    } else {
                        Toast.makeText(this.requireActivity(), "Unable to fetch details", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                // Device is not connected to the internet
                Toast.makeText(this.requireActivity(), "No internet connection", Toast.LENGTH_SHORT).show()
            }

            //Logout button
            binding.btnLogout.setOnClickListener {
                val editor = ProfileFragment.sharedPreferences.edit()
                editor.putBoolean("isStudent", false)
                editor.putBoolean("isAdmission", false)
                editor.apply()
                startActivity(Intent(this.requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }

        return binding.root
    }

}