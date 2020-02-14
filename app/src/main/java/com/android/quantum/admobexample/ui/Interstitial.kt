package com.android.quantum.admob.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.quantum.admobexample.R
import com.android.quantum.admobexample.utils.Constants.Companion.INTERSTITIAL_UNIT_TEST
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.fragment_interstitial.*

class Interstitial : Fragment() {

    private val TAG: String = "AppDebug"

    private lateinit var mInterstitialAd: InterstitialAd
    private var numLevel: Int = 1

    companion object {
        val ERROR_CODE_INTERNAL_ERROR = 0
        val ERROR_CODE_INVALID_REQUEST = 1
        val ERROR_CODE_NETWORK_ERROR = 2
        val ERROR_CODE_NO_FILL = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(activity!!)
        mInterstitialAd = InterstitialAd(activity)
        mInterstitialAd.adUnitId = INTERSTITIAL_UNIT_TEST
        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_interstitial, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvLevel.text = "Nivel $numLevel"

        btnNextLevel.setOnClickListener {
            if(mInterstitialAd.isLoaded){
                Toast.makeText(activity!!, "AD LOADED...", Toast.LENGTH_SHORT).show()
                mInterstitialAd.show()
            }else {
                Log.d(TAG, "The interstitial wasn't loaded yet.")
            }
        }

        mInterstitialAd.adListener = object: AdListener() {
            override fun onAdFailedToLoad(erroCode: Int) {
                super.onAdFailedToLoad(erroCode)

                when(erroCode) {
                    ERROR_CODE_INTERNAL_ERROR -> Log.d(TAG, "ERROR_CODE_INTERNAL_ERROR")
                    ERROR_CODE_INVALID_REQUEST -> Log.d(TAG, "ERROR_CODE_INVALID_REQUEST")
                    ERROR_CODE_NETWORK_ERROR -> Log.d(TAG, "ERROR_CODE_NETWORK_ERROR")
                    ERROR_CODE_NO_FILL -> Log.d(TAG, "ERROR_CODE_NO_FILL")
                }

                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }

            override fun onAdClosed() {
                super.onAdClosed()

                numLevel++
                tvLevel.text = "Nivel $numLevel"
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }

    }


}
