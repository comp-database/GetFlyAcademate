package com.getfly.technologies

import android.content.Context
import com.easebuzz.payment.kit.PWECouponsActivity;
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.getfly.technologies.databinding.ActivityHomePageBinding
import com.getfly.technologies.model.AcademateRepository
import com.getfly.technologies.model.EaseBuzzRepository
import com.getfly.technologies.model.viewmodel.MainScreenViewModel
import okhttp3.OkHttpClient

class StudentScreen : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding
    private lateinit var viewModel: MainScreenViewModel
    var pweActivityResultLauncher: ActivityResultLauncher<Intent>? = null
    val client = OkHttpClient()

    //Shared preferences to store user uid
    companion object {
        const val SHARED_PREFS_NAME = "AcademateLogin"
        lateinit var sharedPreferences: SharedPreferences
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = applicationContext.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
//        val uid = intent.getStringExtra("uid")

        val uidSP = StudentScreen.sharedPreferences.getInt("uid", 0)
        if (uidSP != 0) {
            val repository = AcademateRepository()
            val repositoryTwo = EaseBuzzRepository()
            val viewModelFactory = MainViewModelFactory(repository,repositoryTwo)

            viewModel = ViewModelProvider(this, viewModelFactory)[MainScreenViewModel::class.java]

            //Personal details request
            viewModel.getPersonalDetails(uidSP.toString())
            viewModel.PersonalDetailsResponse.observe(this) { PersonalDetailsResponseStatus ->
                if (PersonalDetailsResponseStatus.isSuccessful) {
//                    Log.d("Atharv", PersonalDetailsResponseStatus.body().toString())
                    binding.tvName.text = PersonalDetailsResponseStatus.body()?.radd!![0].name
                    binding.tvCaste.text = PersonalDetailsResponseStatus.body()?.radd!![0].caste
                }
            }

            //Fee request
            viewModel.getFeeDetails(uidSP.toString())
            viewModel.FeeDetailsResponse.observe(this) { FeeDetailsResponseStatus ->
                if (FeeDetailsResponseStatus.isSuccessful) {
                    binding.tvPaidAmount.text =
                        FeeDetailsResponseStatus.body()?.paidAmount.toString()
                    val pendingAmount = (FeeDetailsResponseStatus.body()?.totalAmount?.minus(
                        FeeDetailsResponseStatus.body()!!.paidAmount
                    )).toString()
                    binding.tvPendingAmount.text = pendingAmount
                    if (pendingAmount.toInt() == 0) {
                        binding.btnPayNow.visibility = View.GONE
                    }

                }
            }

            //Current Course details request
            viewModel.getCurrentCourseDetails(uidSP.toString())
            viewModel.CurrentCourseDetailsResponse.observe(this) { CurrentCourseDetailsResponseStatus ->
                if (CurrentCourseDetailsResponseStatus.isSuccessful) {
                    binding.tvStudId.text =
                        CurrentCourseDetailsResponseStatus.body()?.data!![0].studClgId.toString()
                }
            }

            //Get documents
            viewModel.getDocDetails(uidSP.toString())
            viewModel.DocDeatailResponse.observe(this) { DocDetailsResponseStatus ->
                if (DocDetailsResponseStatus.isSuccessful) {
                    val url = DocDetailsResponseStatus.body()?.docs?.photo
                    Glide.with(this)
                        .load(url)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.logo)
                        .into(binding.profilePic)
                }
            }
        } else {
            Toast.makeText(this, "Unable to fetch details", Toast.LENGTH_SHORT).show()
        }

        //CODE FOR EASE-BUZZ ACTIVITY
        pweActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val data = result.data
            if (data != null) {
                val result = data.getStringExtra("result")
                val payment_response = data.getStringExtra("payment_response")
                try {
                    Log.d("payment", result.toString())
                    // Handle response here
                } catch (e: Exception) {
                    // Handle exception here
                }
            }
        }

//        binding.btnPayNow.setOnClickListener {
//
//            viewModel.PaymentResponse.observe(this) { paymentResponseStatus ->
//                if (paymentResponseStatus.isSuccessful) {
//                    val intent = Intent(this, StudentScreen::class.java)
//                    intent.putExtra("uid", paymentResponseStatus.body()?.data.toString())
//                    startActivity(intent)
//                    finish()
//                } else {
//                    Toast.makeText(this, paymentResponseStatus.body()?.data.toString(), Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            val intentProceed = Intent(baseContext, PWECouponsActivity::class.java)
//            intentProceed.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
//            intentProceed.putExtra("access_key", "2PBP7IABZ2")
//            intentProceed.putExtra("pay_mode", "test")
//            pweActivityResultLauncher!!.launch(intentProceed)
//        }
//
//        //Logout button
//        binding.btnLogout.setOnClickListener {
//            val editor = StudentScreen.sharedPreferences.edit()
//            editor.putBoolean("isStudent", false)
//            editor.putBoolean("isAdmission", false)
//            editor.apply()
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//        }
    }

//    val mediaType = MediaType.parse("application/x-www-form-urlencoded")
//    val body = RequestBody.create(mediaType, "key=&txnid=&amount=2.1&productinfo=&firstname=&phone=&email=&surl=&furl=&hash=&udf1=&udf2=&udf3=&udf4=&udf5=&udf6=&udf7=&address1=&address2=&city=&state=&country=&zipcode=&show_payment_mode=&split_payments=&request_flow=&sub_merchant_id=&payment+category=&account_no=")
//    val request = Request.Builder()
//        .url("https://testpay.easebuzz.in/payment/initiateLink")
//        .post(body)
//        .addHeader("Content-Type", "application/x-www-form-urlencoded")
//        .addHeader("Accept", "application/json")
//        .build()
//
//    val response = client.newCall(request).execute()
//    Log.d("paymentOK", response.toString());

}