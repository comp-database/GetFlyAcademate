package com.getfly.technologies

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.getfly.technologies.databinding.ActivityLoginBinding
import com.getfly.technologies.model.AcademateRepository
import com.getfly.technologies.model.EaseBuzzRepository
import com.getfly.technologies.model.viewmodel.MainScreenViewModel


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: MainScreenViewModel

    //Shared preferences to store user login status
    companion object {
        private const val SHARED_PREFS_NAME = "AcademateLogin"
        lateinit var sharedPreferences: SharedPreferences
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences =
            applicationContext.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)


        val repository = AcademateRepository()
        val repositoryTwo = EaseBuzzRepository()
        val viewModelFactory = MainViewModelFactory(repository,repositoryTwo)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainScreenViewModel::class.java]

        //Login Button
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            if (networkInfo != null && networkInfo.isConnected) {

                binding.btnLogin.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE

                viewModel.postLogin(email, password)
                viewModel.LoginResponse.observe(this) { response ->
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            binding.btnLogin.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            if (response.body()?.userType == 3) {
                                val editor = LoginActivity.sharedPreferences.edit()
                                editor.putBoolean("isAdmission", true)
                                editor.putInt("uid", response.body()!!.uid)
                                editor.apply()
                                startActivity(Intent(this, AdmissionScreen::class.java))
                                finish()
                            } else {
                                val editor = LoginActivity.sharedPreferences.edit()
                                editor.putBoolean("isStudent", true)
                                editor.putInt("uid", response.body()!!.uid)
                                editor.apply()
                                val intent = Intent(this, HomeActivity::class.java)
                                Toast.makeText(
                                    this,
                                    "Welcome to Academate !",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            binding.btnLogin.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        binding.btnLogin.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        binding.etEmail.error = "Invalid Email or Password"
//                    Toast.makeText(this, response.code().toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                // Device is not connected to the internet
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val isStudent = sharedPreferences.getBoolean("isStudent", false)
        val isAdmission = sharedPreferences.getBoolean("isAdmission", false)
        if (isStudent) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            // User is logged in, proceed to the main activity or home screen
        }else if (isAdmission) {
            startActivity(Intent(this, AdmissionScreen::class.java))
            finish()
        }
        else {
//            Toast.makeText(this, "hehehhee", Toast.LENGTH_SHORT).show()
            // User is not logged in, show the login screen
        }
    }


//    override fun onStart() {
//        super.onStart()
//        viewModel.isLogin.observe(this){
//            if (it){
//                startActivity(Intent(this, StudentScreen::class.java))
//                finish()
//            }
//            else{
//                Log.d("Error","Error")
//            }
//        }
//    }

}