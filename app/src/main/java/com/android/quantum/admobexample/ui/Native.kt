package com.android.quantum.admob.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.android.quantum.admobexample.R
import com.android.quantum.admobexample.utils.Constants.Companion.NATIVE_UNIT_TEST
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import kotlinx.android.synthetic.main.fragment_native.*
import kotlinx.android.synthetic.main.layout_native_ad.view.*

class Native : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(activity!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_native, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adLoader = AdLoader.Builder(activity!!, NATIVE_UNIT_TEST)
            .forUnifiedNativeAd { unifiedNativeAd ->
                val unifiedNativeAdView = LayoutInflater.from(activity!!).inflate(
                    R.layout.layout_native_ad,
                    null
                ) as UnifiedNativeAdView

                mapUnifiedNativeAdLayout(unifiedNativeAd, unifiedNativeAdView)

                id_native_ad.removeAllViews()
                id_native_ad.addView(unifiedNativeAdView)
            }
            .build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun mapUnifiedNativeAdLayout(adFromGoogle: UnifiedNativeAd?, myAdView: UnifiedNativeAdView) {

        myAdView.apply {
            mediaView = mvAd
            headlineView = tvHeadline
            bodyView = tvBody
            callToActionView = btnAction
            iconView = ivIcon
            priceView = tvPrice
            starRatingView = rbAd
            storeView = tvStore
            advertiserView = tvAdvertiser
        }

        adFromGoogle?.let {

            it.headline?.let {
                (myAdView.headlineView as TextView).text = adFromGoogle?.headline
            } ?: run {
                myAdView.headlineView.visibility = View.GONE
            }

            it.body?.let {
                (myAdView.bodyView as TextView).text = adFromGoogle.body
            } ?: run {
                myAdView.bodyView.visibility = View.GONE
            }

            it.callToAction?.let {
                (myAdView.btnAction as Button).text = adFromGoogle.callToAction
            } ?: run {
                myAdView.callToActionView.visibility = View.GONE
            }

            it.icon?.let {
                (myAdView.iconView as ImageView).setImageDrawable(adFromGoogle.icon.drawable)
            } ?: run {
                myAdView.iconView.visibility = View.GONE
            }

            it.price?.let {
                (myAdView.priceView as TextView).text = adFromGoogle.price
            } ?: run {
                myAdView.priceView.visibility = View.GONE
            }

            it.starRating?.let {
                (myAdView.starRatingView as RatingBar).rating = adFromGoogle.starRating.toFloat()
            }?: run {
                myAdView.starRatingView.visibility = View.GONE
            }

            it.store?.let {
                (myAdView.storeView as TextView).text = adFromGoogle.store
            }?: run {
                myAdView.storeView.visibility = View.GONE
            }

            it.advertiser?.let {
                (myAdView.advertiserView as TextView).text = adFromGoogle.advertiser
            }?: run {
                myAdView.advertiserView.visibility = View.GONE
            }
        }

        myAdView.setNativeAd(adFromGoogle)
    }
}
