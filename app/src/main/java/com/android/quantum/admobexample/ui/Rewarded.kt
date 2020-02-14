package com.android.quantum.admob.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.quantum.admobexample.R
import com.android.quantum.admobexample.utils.Constants.Companion.REWARDED_UNIT_TEST
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.android.synthetic.main.fragment_rewarded.*

class Rewarded : Fragment() {

    private val TAG: String = "AppDebug"

    private lateinit var rewardedAd: RewardedAd
    private lateinit var adLoadCallback: RewardedAdLoadCallback
    private var numOfCoins: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rewardedAd = RewardedAd(activity!!, REWARDED_UNIT_TEST)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rewarded, container, false)

        rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(rewardedAd.isLoaded){
            btnAction.visibility = View.VISIBLE
        } else {
            adLoadCallback = object : RewardedAdLoadCallback() {
                override fun onRewardedAdLoaded() {
                    Log.d(TAG, "Ad Loaded...")
                    btnAction.visibility = View.VISIBLE
                }

                override fun onRewardedAdFailedToLoad(errorCode: Int) {
                    Log.d(TAG, "Failed Load")
                    rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)
                }
            }

            rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)
        }

        btnAction.setOnClickListener {
            mostrarAnuncio()
        }

    }

    private fun mostrarAnuncio() {
        Log.d(TAG, "SHOWING AD...")
        rewardedAd.show(activity!!, object : RewardedAdCallback(){
            override fun onUserEarnedReward(rewardedItem: RewardItem) {
                Log.d(TAG, "Rewarded Item Amount: ${rewardedItem.amount}")

                numOfCoins += rewardedItem.amount

                btnAction.visibility = View.GONE

                tvCoins.text = "$numOfCoins disponibles"
            }

        })
    }


}
