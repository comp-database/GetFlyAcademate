package com.getfly.technologies.ui.payment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.easebuzz.payment.kit.PWECouponsActivity
import com.getfly.technologies.HomeActivity
import com.getfly.technologies.MainViewModelFactory
import com.getfly.technologies.databinding.FragmentPaymentBinding
import com.getfly.technologies.model.AcademateRepository
import com.getfly.technologies.model.EaseBuzzRepository
import com.getfly.technologies.model.api.AcademateWebService
import com.getfly.technologies.model.api.EaseBuzzWebService
import com.getfly.technologies.model.response.FeeStructureResponse
import com.getfly.technologies.model.viewmodel.MainScreenViewModel
import com.getfly.technologies.ui.academics.AcademicsFragment
import com.ncorti.slidetoact.SlideToActView
import com.squareup.okhttp.Call
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.security.auth.callback.Callback

class PaymentsFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private lateinit var viewModel: MainScreenViewModel
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
        binding = FragmentPaymentBinding.inflate(layoutInflater)

        PaymentsFragment.sharedPreferences =
            requireActivity().applicationContext.getSharedPreferences(
                PaymentsFragment.SHARED_PREFS_NAME,
                Context.MODE_PRIVATE
            )

        val uidSP = PaymentsFragment.sharedPreferences.getInt("uid", 0)
        if (uidSP != 0) {
            val repository = AcademateRepository()
            val repositoryTwo = EaseBuzzRepository()
            val viewModelFactory = MainViewModelFactory(repository, repositoryTwo)

            val connectivityManager = this.requireActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {

                viewModel =
                    ViewModelProvider(this, viewModelFactory)[MainScreenViewModel::class.java]

                //Get Fee Structure
                viewModel.getFeeStructure(uidSP.toString())
                viewModel.FeeStructureResponse.observe(viewLifecycleOwner) { FeeStructureResponseStatus ->
                    if (FeeStructureResponseStatus.isSuccessful) {

                        Log.d("vishal", FeeStructureResponseStatus.body()?.result?.size.toString())
                        val feeHeadCount: Int = FeeStructureResponseStatus.body()!!.result!!.size

                        when (feeHeadCount) {
                            6 -> {
                                val head1 = FeeStructureResponseStatus.body()!!.result!!.get(0).fhId
                                val head2 = FeeStructureResponseStatus.body()!!.result!!.get(1).fhId
                                val head3 = FeeStructureResponseStatus.body()!!.result!!.get(2).fhId
                                val head4 = FeeStructureResponseStatus.body()!!.result!!.get(3).fhId
                                val head5 = FeeStructureResponseStatus.body()!!.result!!.get(4).fhId
                                val head15 =
                                    FeeStructureResponseStatus.body()!!.result!!.get(5).fhId

                                if (head1.equals(1)) {
                                    binding.tvHead1.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(2)) {
                                    binding.tvHead1.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(3)) {
                                    binding.tvHead1.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(4)) {
                                    binding.tvHead1.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(5)) {
                                    binding.tvHead1.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(15)) {
                                    binding.tvHead1.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else {
                                    binding.tvHead1.visibility = View.GONE
                                }
                                if (head2.equals(1)) {
                                    binding.tvHead2.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(2)) {
                                    binding.tvHead2.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(3)) {
                                    binding.tvHead2.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(4)) {
                                    binding.tvHead2.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(5)) {
                                    binding.tvHead2.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(15)) {
                                    binding.tvHead2.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else {
                                    binding.tvHead2.visibility = View.GONE
                                }
                                if (head3.equals(1)) {
                                    binding.tvHead3.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(2)) {
                                    binding.tvHead3.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(3)) {
                                    binding.tvHead3.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(4)) {
                                    binding.tvHead3.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(5)) {
                                    binding.tvHead3.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(15)) {
                                    binding.tvHead3.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else {
                                    binding.tvHead3.visibility = View.GONE
                                }
                                if (head4.equals(1)) {
                                    binding.tvHead4.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            3
                                        ).amount
                                } else if (head4.equals(2)) {
                                    binding.tvHead4.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            3
                                        ).amount
                                } else if (head4.equals(3)) {
                                    binding.tvHead4.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            3
                                        ).amount
                                } else if (head4.equals(4)) {
                                    binding.tvHead4.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            3
                                        ).amount
                                } else if (head4.equals(5)) {
                                    binding.tvHead4.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            3
                                        ).amount
                                } else if (head4.equals(15)) {
                                    binding.tvHead4.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            3
                                        ).amount
                                } else {
                                    binding.tvHead4.visibility = View.GONE
                                }
                                if (head5.equals(1)) {
                                    binding.tvHead5.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            4
                                        ).amount
                                } else if (head5.equals(2)) {
                                    binding.tvHead5.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            4
                                        ).amount
                                } else if (head5.equals(3)) {
                                    binding.tvHead5.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            4
                                        ).amount
                                } else if (head5.equals(4)) {
                                    binding.tvHead5.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            4
                                        ).amount
                                } else if (head5.equals(5)) {
                                    binding.tvHead5.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            4
                                        ).amount
                                } else if (head5.equals(15)) {
                                    binding.tvHead5.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            4
                                        ).amount
                                } else {
                                    binding.tvHead5.visibility = View.GONE
                                }
                                if (head15.equals(1)) {
                                    binding.tvHead15.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            5
                                        ).amount
                                } else if (head15.equals(2)) {
                                    binding.tvHead15.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            5
                                        ).amount
                                } else if (head15.equals(3)) {
                                    binding.tvHead15.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            5
                                        ).amount
                                } else if (head15.equals(4)) {
                                    binding.tvHead15.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            5
                                        ).amount
                                } else if (head15.equals(5)) {
                                    binding.tvHead15.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            5
                                        ).amount
                                } else if (head15.equals(15)) {
                                    binding.tvHead15.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            5
                                        ).amount
                                } else {
                                    binding.tvHead15.visibility = View.GONE
                                }

//                            binding.tvHead1.visibility = View.GONE
//                            binding.tvHead2.visibility = View.GONE
//                            binding.tvHead3.visibility = View.GONE
//                            binding.tvHead4.visibility = View.GONE
//                            binding.tvHead5.visibility = View.GONE
//                            binding.tvHead15.visibility = View.GONE
                            }

                            5 -> {
                                val head1 = FeeStructureResponseStatus.body()!!.result!!.get(0).fhId
                                val head2 = FeeStructureResponseStatus.body()!!.result!!.get(1).fhId
                                val head3 = FeeStructureResponseStatus.body()!!.result!!.get(2).fhId
                                val head4 = FeeStructureResponseStatus.body()!!.result!!.get(3).fhId
                                val head5 = FeeStructureResponseStatus.body()!!.result!!.get(4).fhId
//                            val head15 = FeeStructureResponseStatus.body()!!.result!!.get(5).fhId

                                if (head1.equals(1)) {
                                    binding.tvHead1.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(2)) {
                                    binding.tvHead1.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(3)) {
                                    binding.tvHead1.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(4)) {
                                    binding.tvHead1.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(5)) {
                                    binding.tvHead1.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(15)) {
                                    binding.tvHead1.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else {
                                    binding.tvHead1.visibility = View.GONE
                                }
                                if (head2.equals(1)) {
                                    binding.tvHead2.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(2)) {
                                    binding.tvHead2.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(3)) {
                                    binding.tvHead2.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(4)) {
                                    binding.tvHead2.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(5)) {
                                    binding.tvHead2.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(15)) {
                                    binding.tvHead2.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else {
                                    binding.tvHead2.visibility = View.GONE
                                }
                                if (head3.equals(1)) {
                                    binding.tvHead3.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(2)) {
                                    binding.tvHead3.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(3)) {
                                    binding.tvHead3.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(4)) {
                                    binding.tvHead3.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(5)) {
                                    binding.tvHead3.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(15)) {
                                    binding.tvHead3.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else {
                                    binding.tvHead3.visibility = View.GONE
                                }
                                if (head4.equals(1)) {
                                    binding.tvHead4.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            3
                                        ).amount
                                } else if (head4.equals(2)) {
                                    binding.tvHead4.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            3
                                        ).amount
                                } else if (head4.equals(3)) {
                                    binding.tvHead4.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            3
                                        ).amount
                                } else if (head4.equals(4)) {
                                    binding.tvHead4.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            3
                                        ).amount
                                } else if (head4.equals(5)) {
                                    binding.tvHead4.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            3
                                        ).amount
                                } else if (head4.equals(15)) {
                                    binding.tvHead4.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            3
                                        ).amount
                                } else {
                                    binding.tvHead4.visibility = View.GONE
                                }
                                if (head5.equals(1)) {
                                    binding.tvHead5.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            4
                                        ).amount
                                } else if (head5.equals(2)) {
                                    binding.tvHead5.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            4
                                        ).amount
                                } else if (head5.equals(3)) {
                                    binding.tvHead5.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            4
                                        ).amount
                                } else if (head5.equals(4)) {
                                    binding.tvHead5.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            4
                                        ).amount
                                } else if (head5.equals(5)) {
                                    binding.tvHead5.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            4
                                        ).amount
                                } else if (head5.equals(15)) {
                                    binding.tvHead5.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            4
                                        ).amount
                                } else {
                                    binding.tvHead5.visibility = View.GONE
                                }
//                            if (head15.equals(1)) {
//                                binding.tvHead15.text =
//                                    "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(2)){
//                                binding.tvHead15.text =
//                                    "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(3)){
//                                binding.tvHead15.text =
//                                    "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(4)){
//                                binding.tvHead15.text =
//                                    "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(5)){
//                                binding.tvHead15.text =
//                                    "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(15)){
//                                binding.tvHead15.text =
//                                    "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            }else {binding.tvHead15.visibility = View.GONE}

//                            binding.tvHead1.visibility = View.GONE
//                            binding.tvHead2.visibility = View.GONE
//                            binding.tvHead3.visibility = View.GONE
//                            binding.tvHead4.visibility = View.GONE
//                            binding.tvHead5.visibility = View.GONE
                                binding.tvHead15.visibility = View.GONE
                            }

                            4 -> {
                                val head1 = FeeStructureResponseStatus.body()!!.result!!.get(0).fhId
                                val head2 = FeeStructureResponseStatus.body()!!.result!!.get(1).fhId
                                val head3 = FeeStructureResponseStatus.body()!!.result!!.get(2).fhId
                                val head4 = FeeStructureResponseStatus.body()!!.result!!.get(3).fhId
//                            val head5 = FeeStructureResponseStatus.body()!!.result!!.get(4).fhId
//                            val head15 = FeeStructureResponseStatus.body()!!.result!!.get(5).fhId

                                if (head1.equals(1)) {
                                    binding.tvHead1.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(2)) {
                                    binding.tvHead1.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(3)) {
                                    binding.tvHead1.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(4)) {
                                    binding.tvHead1.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(5)) {
                                    binding.tvHead1.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(15)) {
                                    binding.tvHead1.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else {
                                    binding.tvHead1.visibility = View.GONE
                                }
                                if (head2.equals(1)) {
                                    binding.tvHead2.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(2)) {
                                    binding.tvHead2.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(3)) {
                                    binding.tvHead2.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(4)) {
                                    binding.tvHead2.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(5)) {
                                    binding.tvHead2.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(15)) {
                                    binding.tvHead2.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else {
                                    binding.tvHead2.visibility = View.GONE
                                }
                                if (head3.equals(1)) {
                                    binding.tvHead3.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(2)) {
                                    binding.tvHead3.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(3)) {
                                    binding.tvHead3.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(4)) {
                                    binding.tvHead3.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(5)) {
                                    binding.tvHead3.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(15)) {
                                    binding.tvHead3.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else {
                                    binding.tvHead3.visibility = View.GONE
                                }
                                if (head4.equals(1)) {
                                    binding.tvHead4.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            3
                                        ).amount
                                } else if (head4.equals(2)) {
                                    binding.tvHead4.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            3
                                        ).amount
                                } else if (head4.equals(3)) {
                                    binding.tvHead4.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            3
                                        ).amount
                                } else if (head4.equals(4)) {
                                    binding.tvHead4.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            3
                                        ).amount
                                } else if (head4.equals(5)) {
                                    binding.tvHead4.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            3
                                        ).amount
                                } else if (head4.equals(15)) {
                                    binding.tvHead4.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            3
                                        ).amount
                                } else {
                                    binding.tvHead4.visibility = View.GONE
                                }
//                            if (head5.equals(1)) {
//                                binding.tvHead5.text =
//                                    "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(2)){
//                                binding.tvHead5.text =
//                                    "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(3)){
//                                binding.tvHead5.text =
//                                    "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(4)){
//                                binding.tvHead5.text =
//                                    "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(5)){
//                                binding.tvHead5.text =
//                                    "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(15)){
//                                binding.tvHead5.text =
//                                    "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            }else {binding.tvHead5.visibility = View.GONE}
//                            if (head15.equals(1)) {
//                                binding.tvHead15.text =
//                                    "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(2)){
//                                binding.tvHead15.text =
//                                    "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(3)){
//                                binding.tvHead15.text =
//                                    "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(4)){
//                                binding.tvHead15.text =
//                                    "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(5)){
//                                binding.tvHead15.text =
//                                    "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(15)){
//                                binding.tvHead15.text =
//                                    "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            }else {binding.tvHead15.visibility = View.GONE}

//                            binding.tvHead1.visibility = View.GONE
//                            binding.tvHead2.visibility = View.GONE
//                            binding.tvHead3.visibility = View.GONE
//                            binding.tvHead4.visibility = View.GONE
                                binding.tvHead5.visibility = View.GONE
                                binding.tvHead15.visibility = View.GONE
                            }

                            3 -> {
                                val head1 = FeeStructureResponseStatus.body()!!.result!!.get(0).fhId
                                val head2 = FeeStructureResponseStatus.body()!!.result!!.get(1).fhId
                                val head3 = FeeStructureResponseStatus.body()!!.result!!.get(2).fhId
//                            val head4 = FeeStructureResponseStatus.body()!!.result!!.get(3).fhId
//                            val head5 = FeeStructureResponseStatus.body()!!.result!!.get(4).fhId
//                            val head15 = FeeStructureResponseStatus.body()!!.result!!.get(5).fhId

                                if (head1.equals(1)) {
                                    binding.tvHead1.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(2)) {
                                    binding.tvHead1.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(3)) {
                                    binding.tvHead1.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(4)) {
                                    binding.tvHead1.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(5)) {
                                    binding.tvHead1.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(15)) {
                                    binding.tvHead1.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else {
                                    binding.tvHead1.visibility = View.GONE
                                }
                                if (head2.equals(1)) {
                                    binding.tvHead2.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(2)) {
                                    binding.tvHead2.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(3)) {
                                    binding.tvHead2.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(4)) {
                                    binding.tvHead2.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(5)) {
                                    binding.tvHead2.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(15)) {
                                    binding.tvHead2.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else {
                                    binding.tvHead2.visibility = View.GONE
                                }
                                if (head3.equals(1)) {
                                    binding.tvHead3.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(2)) {
                                    binding.tvHead3.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(3)) {
                                    binding.tvHead3.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(4)) {
                                    binding.tvHead3.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(5)) {
                                    binding.tvHead3.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else if (head3.equals(15)) {
                                    binding.tvHead3.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            2
                                        ).amount
                                } else {
                                    binding.tvHead3.visibility = View.GONE
                                }
//                            if (head4.equals(1)) {
//                                binding.tvHead4.text =
//                                    "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(3).amount
//                            } else if (head4.equals(2)){
//                                binding.tvHead4.text =
//                                    "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(3).amount
//                            } else if (head4.equals(3)){
//                                binding.tvHead4.text =
//                                    "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(3).amount
//                            } else if (head4.equals(4)){
//                                binding.tvHead4.text =
//                                    "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(3).amount
//                            } else if (head4.equals(5)){
//                                binding.tvHead4.text =
//                                    "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(3).amount
//                            } else if (head4.equals(15)){
//                                binding.tvHead4.text =
//                                    "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(3).amount
//                            }else {binding.tvHead4.visibility = View.GONE}
//                            if (head5.equals(1)) {
//                                binding.tvHead5.text =
//                                    "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(2)){
//                                binding.tvHead5.text =
//                                    "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(3)){
//                                binding.tvHead5.text =
//                                    "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(4)){
//                                binding.tvHead5.text =
//                                    "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(5)){
//                                binding.tvHead5.text =
//                                    "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(15)){
//                                binding.tvHead5.text =
//                                    "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            }else {binding.tvHead5.visibility = View.GONE}
//                            if (head15.equals(1)) {
//                                binding.tvHead15.text =
//                                    "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(2)){
//                                binding.tvHead15.text =
//                                    "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(3)){
//                                binding.tvHead15.text =
//                                    "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(4)){
//                                binding.tvHead15.text =
//                                    "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(5)){
//                                binding.tvHead15.text =
//                                    "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(15)){
//                                binding.tvHead15.text =
//                                    "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            }else {binding.tvHead15.visibility = View.GONE}

//                            binding.tvHead1.visibility = View.GONE
//                            binding.tvHead2.visibility = View.GONE
//                            binding.tvHead3.visibility = View.GONE
                                binding.tvHead4.visibility = View.GONE
                                binding.tvHead5.visibility = View.GONE
                                binding.tvHead15.visibility = View.GONE
                            }

                            2 -> {
                                val head1 = FeeStructureResponseStatus.body()!!.result!!.get(0).fhId
                                val head2 = FeeStructureResponseStatus.body()!!.result!!.get(1).fhId
//                            val head3 = FeeStructureResponseStatus.body()!!.result!!.get(2).fhId
//                            val head4 = FeeStructureResponseStatus.body()!!.result!!.get(3).fhId
//                            val head5 = FeeStructureResponseStatus.body()!!.result!!.get(4).fhId
//                            val head15 = FeeStructureResponseStatus.body()!!.result!!.get(5).fhId

                                if (head1.equals(1)) {
                                    binding.tvHead1.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(2)) {
                                    binding.tvHead1.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(3)) {
                                    binding.tvHead1.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(4)) {
                                    binding.tvHead1.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(5)) {
                                    binding.tvHead1.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(15)) {
                                    binding.tvHead1.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else {
                                    binding.tvHead1.visibility = View.GONE
                                }
                                if (head2.equals(1)) {
                                    binding.tvHead2.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(2)) {
                                    binding.tvHead2.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(3)) {
                                    binding.tvHead2.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(4)) {
                                    binding.tvHead2.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(5)) {
                                    binding.tvHead2.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else if (head2.equals(15)) {
                                    binding.tvHead2.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            1
                                        ).amount
                                } else {
                                    binding.tvHead2.visibility = View.GONE
                                }
//                            if (head3.equals(1)) {
//                                binding.tvHead3.text =
//                                    "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(2).amount
//                            } else if (head3.equals(2)){
//                                binding.tvHead3.text =
//                                    "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(2).amount
//                            } else if (head3.equals(3)){
//                                binding.tvHead3.text =
//                                    "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(2).amount
//                            } else if (head3.equals(4)){
//                                binding.tvHead3.text =
//                                    "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(2).amount
//                            } else if (head3.equals(5)){
//                                binding.tvHead3.text =
//                                    "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(2).amount
//                            } else if (head3.equals(15)){
//                                binding.tvHead3.text =
//                                    "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(2).amount
//                            }else {binding.tvHead3.visibility = View.GONE}
//                            if (head4.equals(1)) {
//                                binding.tvHead4.text =
//                                    "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(3).amount
//                            } else if (head4.equals(2)){
//                                binding.tvHead4.text =
//                                    "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(3).amount
//                            } else if (head4.equals(3)){
//                                binding.tvHead4.text =
//                                    "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(3).amount
//                            } else if (head4.equals(4)){
//                                binding.tvHead4.text =
//                                    "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(3).amount
//                            } else if (head4.equals(5)){
//                                binding.tvHead4.text =
//                                    "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(3).amount
//                            } else if (head4.equals(15)){
//                                binding.tvHead4.text =
//                                    "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(3).amount
//                            }else {binding.tvHead4.visibility = View.GONE}
//                            if (head5.equals(1)) {
//                                binding.tvHead5.text =
//                                    "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(2)){
//                                binding.tvHead5.text =
//                                    "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(3)){
//                                binding.tvHead5.text =
//                                    "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(4)){
//                                binding.tvHead5.text =
//                                    "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(5)){
//                                binding.tvHead5.text =
//                                    "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(15)){
//                                binding.tvHead5.text =
//                                    "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            }else {binding.tvHead5.visibility = View.GONE}
//                            if (head15.equals(1)) {
//                                binding.tvHead15.text =
//                                    "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(2)){
//                                binding.tvHead15.text =
//                                    "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(3)){
//                                binding.tvHead15.text =
//                                    "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(4)){
//                                binding.tvHead15.text =
//                                    "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(5)){
//                                binding.tvHead15.text =
//                                    "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(15)){
//                                binding.tvHead15.text =
//                                    "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            }else {binding.tvHead15.visibility = View.GONE}

//                            binding.tvHead1.visibility = View.GONE
//                            binding.tvHead2.visibility = View.GONE
                                binding.tvHead3.visibility = View.GONE
                                binding.tvHead4.visibility = View.GONE
                                binding.tvHead5.visibility = View.GONE
                                binding.tvHead15.visibility = View.GONE
                            }

                            1 -> {
                                val head1 = FeeStructureResponseStatus.body()!!.result!!.get(0).fhId
//                            val head2 = FeeStructureResponseStatus.body()!!.result!!.get(1).fhId
//                            val head3 = FeeStructureResponseStatus.body()!!.result!!.get(2).fhId
//                            val head4 = FeeStructureResponseStatus.body()!!.result!!.get(3).fhId
//                            val head5 = FeeStructureResponseStatus.body()!!.result!!.get(4).fhId
//                            val head15 = FeeStructureResponseStatus.body()!!.result!!.get(5).fhId

                                if (head1.equals(1)) {
                                    binding.tvHead1.text =
                                        "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(2)) {
                                    binding.tvHead1.text =
                                        "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(3)) {
                                    binding.tvHead1.text =
                                        "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(4)) {
                                    binding.tvHead1.text =
                                        "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(5)) {
                                    binding.tvHead1.text =
                                        "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else if (head1.equals(15)) {
                                    binding.tvHead1.text =
                                        "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(
                                            0
                                        ).amount
                                } else {
                                    binding.tvHead1.visibility = View.GONE
                                }
//                            if (head2.equals(1)) {
//                                binding.tvHead2.text =
//                                    "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(1).amount
//                            } else if (head2.equals(2)){
//                                binding.tvHead2.text =
//                                    "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(1).amount
//                            } else if (head2.equals(3)){
//                                binding.tvHead2.text =
//                                    "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(1).amount
//                            } else if (head2.equals(4)){
//                                binding.tvHead2.text =
//                                    "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(1).amount
//                            } else if (head2.equals(5)){
//                                binding.tvHead2.text =
//                                    "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(1).amount
//                            } else if (head2.equals(15)){
//                                binding.tvHead2.text =
//                                    "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(1).amount
//                            }else {binding.tvHead2.visibility = View.GONE}
//                            if (head3.equals(1)) {
//                                binding.tvHead3.text =
//                                    "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(2).amount
//                            } else if (head3.equals(2)){
//                                binding.tvHead3.text =
//                                    "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(2).amount
//                            } else if (head3.equals(3)){
//                                binding.tvHead3.text =
//                                    "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(2).amount
//                            } else if (head3.equals(4)){
//                                binding.tvHead3.text =
//                                    "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(2).amount
//                            } else if (head3.equals(5)){
//                                binding.tvHead3.text =
//                                    "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(2).amount
//                            } else if (head3.equals(15)){
//                                binding.tvHead3.text =
//                                    "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(2).amount
//                            }else {binding.tvHead3.visibility = View.GONE}
//                            if (head4.equals(1)) {
//                                binding.tvHead4.text =
//                                    "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(3).amount
//                            } else if (head4.equals(2)){
//                                binding.tvHead4.text =
//                                    "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(3).amount
//                            } else if (head4.equals(3)){
//                                binding.tvHead4.text =
//                                    "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(3).amount
//                            } else if (head4.equals(4)){
//                                binding.tvHead4.text =
//                                    "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(3).amount
//                            } else if (head4.equals(5)){
//                                binding.tvHead4.text =
//                                    "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(3).amount
//                            } else if (head4.equals(15)){
//                                binding.tvHead4.text =
//                                    "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(3).amount
//                            }else {binding.tvHead4.visibility = View.GONE}
//                            if (head5.equals(1)) {
//                                binding.tvHead5.text =
//                                    "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(2)){
//                                binding.tvHead5.text =
//                                    "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(3)){
//                                binding.tvHead5.text =
//                                    "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(4)){
//                                binding.tvHead5.text =
//                                    "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(5)){
//                                binding.tvHead5.text =
//                                    "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            } else if (head5.equals(15)){
//                                binding.tvHead5.text =
//                                    "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(4).amount
//                            }else {binding.tvHead5.visibility = View.GONE}
//                            if (head15.equals(1)) {
//                                binding.tvHead15.text =
//                                    "Tuition Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(2)){
//                                binding.tvHead15.text =
//                                    "Development Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(3)){
//                                binding.tvHead15.text =
//                                    "Other Fee : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(4)){
//                                binding.tvHead15.text =
//                                    "Bonafide Certificate : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(5)){
//                                binding.tvHead15.text =
//                                    "REFUNDABLE FEES : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            } else if (head15.equals(15)){
//                                binding.tvHead15.text =
//                                    "Amartya Shiksha Yojna : " + FeeStructureResponseStatus!!.body()!!.result.get(5).amount
//                            }else {binding.tvHead15.visibility = View.GONE}

//                            binding.tvHead1.visibility = View.GONE
                                binding.tvHead2.visibility = View.GONE
                                binding.tvHead3.visibility = View.GONE
                                binding.tvHead4.visibility = View.GONE
                                binding.tvHead5.visibility = View.GONE
                                binding.tvHead15.visibility = View.GONE
                            }
                        }
                    } else {
                    Toast.makeText(this.requireActivity(), "Unable to fetch details", Toast.LENGTH_SHORT).show()
                }
                }

                var final_answer = " "
                //Fee request
                viewModel.getFeeDetails(uidSP.toString())
                viewModel.FeeDetailsResponse.observe(viewLifecycleOwner) { FeeDetailsResponseStatus ->
                    if (FeeDetailsResponseStatus.isSuccessful) {

                        binding.tvTotalAmount.text =
                            "Total : " + FeeDetailsResponseStatus.body()?.totalAmount.toString()

                        binding.tvPaidAmount.text =
                            FeeDetailsResponseStatus.body()?.paidAmount.toString()
                        val pendingAmount = (FeeDetailsResponseStatus.body()?.totalAmount?.minus(
                            FeeDetailsResponseStatus.body()!!.paidAmount
                        ))!!.toFloat()
                        Log.d("PendingAmount", pendingAmount.toString())
                        viewModel.getInitiatePaymentBodyDetails(uidSP.toString())
                        viewModel.InitiatePaymentBodyResponse.observe(this.requireActivity()) { InitiatePaymentBodyResponseStatus ->
                            if (InitiatePaymentBodyResponseStatus.isSuccessful) {
                                Log.d(
                                    "paymentBody",
                                    InitiatePaymentBodyResponseStatus.body().toString()
                                )
                                val Hash = "ZHJ8U1ZHPB|${
                                    InitiatePaymentBodyResponseStatus.body()!!.txnid.trim()
                                }|${
                                    pendingAmount.toString().trim()
                                }|${
                                    InitiatePaymentBodyResponseStatus.body()!!.ay.trim()
                                }|${
                                    InitiatePaymentBodyResponseStatus.body()!!.student.trim()
                                }|${
                                    InitiatePaymentBodyResponseStatus.body()!!.email.trim()
                                }|||||||||||39Z5CCABM0"
                                val newHash = encryptString(Hash)
                                Log.d("HASH", newHash)
                                Log.d("name", InitiatePaymentBodyResponseStatus.body()!!.student)
                                Log.d("name", InitiatePaymentBodyResponseStatus.body()!!.txnid)
                                Log.d("name", InitiatePaymentBodyResponseStatus.body()!!.ay)
                                Log.d("name", InitiatePaymentBodyResponseStatus.body()!!.email)
                                Log.d("name", InitiatePaymentBodyResponseStatus.body()!!.contact)
                                Log.d("name", pendingAmount.toString())
                                Log.d("name", newHash)
                                viewModel.postDataToInitiatePayment(
                                    name = InitiatePaymentBodyResponseStatus.body()!!.student,
                                    txnid = InitiatePaymentBodyResponseStatus.body()!!.txnid,
                                    productinfo = InitiatePaymentBodyResponseStatus.body()!!.ay,
                                    email = InitiatePaymentBodyResponseStatus.body()!!.email,
                                    phone = InitiatePaymentBodyResponseStatus.body()!!.contact,
                                    amount = pendingAmount,
                                    hash = newHash
                                )
                                viewModel.InitiatePaymentResponse.observe(this.requireActivity()) { InitiatePaymentResponseStatus ->
                                    if (InitiatePaymentResponseStatus.isSuccessful) {
                                        Log.d(
                                            "paymentSuccess",
                                            InitiatePaymentResponseStatus.body().toString()
                                        )
                                        final_answer =
                                            InitiatePaymentResponseStatus.body()?.data.toString()

                                        Log.d("AccessKey", final_answer)
                                    }
                                }
                                Log.d(
                                    "InitiatePaymentBody",
                                    InitiatePaymentBodyResponseStatus.body().toString()
                                )
                            } else {
                                Log.d("Error", InitiatePaymentBodyResponseStatus.code().toString())
                            }
                        }
                        binding.tvPendingAmount.text = pendingAmount.toString()
                        if (pendingAmount?.toInt() == 0) {
                            binding.btnPayNow.visibility = View.GONE
                        }
                    } else {
                        Toast.makeText(this.requireActivity(), "Unable to fetch details", Toast.LENGTH_SHORT).show()
                    }

                    val AcademateAPI = AcademateWebService().api
                    val EaseBuzzAPI = EaseBuzzWebService().api
                    lifecycleScope.launch {
                        val total_Amount =
                            AcademateAPI.getFeeDetails(uidSP.toString()).body()!!.totalAmount
                        val paid_Amount =
                            AcademateAPI.getFeeDetails(uidSP.toString()).body()!!.paidAmount
                        val pendingAmount = (total_Amount - paid_Amount).toFloat()
                        Log.d("Pending_Amount", pendingAmount.toString())

                        //Initiate Payment Body
                        val EaseBuzz_Academate_data =
                            AcademateAPI.getInitiatePaymentBodyDetails(uidSP.toString()).body()
                        val key = "ZHJ8U1ZHPB"
                        val txnid = EaseBuzz_Academate_data!!.txnid
                        Log.d("txnid", txnid)
                        val amount = pendingAmount

                        val productinfo = EaseBuzz_Academate_data.ay
                        Log.d("productinfo", productinfo)

                        val firstname = EaseBuzz_Academate_data.student
                        Log.d("firstname", firstname)

                        val phone = EaseBuzz_Academate_data.contact
                        Log.d("phone", phone)

                        val email = EaseBuzz_Academate_data.email
                        Log.d("email", email)

//            val surl = "http://vppcoe-va.getflytechnologies.com/api/account/success_payment/"
//            val furl = "http://vppcoe-va.getflytechnologies.com/api/account/failed_payment/"

                        //Hash

                        val Hash = "${key}|${
                            txnid
                        }|${
                            pendingAmount.toString().trim()
                        }|${
                            productinfo
                        }|${
                            firstname
                        }|${
                            email
                        }|||||||||||39Z5CCABM0"
                        val FinalHash = encryptString(Hash)
                        Log.d("FinalHash", FinalHash)

                        //EaseBuzzPart
                        val responseEaseBuzz = EaseBuzzAPI.postInitiatePaymentData(
                            amount,
                            FinalHash,
                            txnid,
                            email,
                            firstname,
                            phone,
                            productinfo
                        )

                        val Data = responseEaseBuzz.body()?.data

                        Log.d("Data", Data!!)


                        binding.btnPayNow.onSlideCompleteListener =
                            object : SlideToActView.OnSlideCompleteListener {
                                override fun onSlideComplete(view: SlideToActView) {
                                    val intentProceed =
                                        Intent(
                                            requireContext(),
                                            PWECouponsActivity::class.java
                                        )
                                    intentProceed.flags =
                                        Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
                                    intentProceed.putExtra(
                                        "access_key",
                                        Data
                                    )
                                    intentProceed.putExtra("pay_mode", "production")
                                    pweActivityResultLauncher!!.launch(intentProceed)
                                }
                            }

                        binding.btnPayNow.onSlideResetListener =
                            object : SlideToActView.OnSlideResetListener {
                                override fun onSlideReset(view: SlideToActView) {
                                    return
                                }
                            }
                    }
                }
            }

            pweActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
                ActivityResultContracts.StartActivityForResult()
            ) { result ->
                val data = result.data
                if (data != null) {
                    val result = data.getStringExtra("result")
                    val payment_response = data.getStringExtra("payment_response")
                    try {
                        print(result)
                        // Handle response here
                    } catch (e: Exception) {
                        // Handle exception here
                    }
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

        return binding.root
    }

    fun encryptString(input: String): String {
        return try {
            val md = MessageDigest.getInstance("SHA-512")
            val messageDigest = md.digest(input.toByteArray())
            val no = BigInteger(1, messageDigest)
            var hashtext = no.toString(16)
            while (hashtext.length < 32) {
                hashtext = "0$hashtext"
            }
            hashtext
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
    }
}