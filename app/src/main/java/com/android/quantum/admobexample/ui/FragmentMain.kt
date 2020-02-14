package com.android.quantum.admob.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.android.quantum.admobexample.R
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.fragment_main.*

class FragmentMain : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(activity!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBanner.setOnClickListener {
            Navigation.findNavController(activity!!,
                R.id.main_nav_host_fragment
            ).navigate(R.id.action_fragmentMain_to_banner)
        }


        btnIntertitial.setOnClickListener {
            Navigation.findNavController(activity!!,
                R.id.main_nav_host_fragment
            ).navigate(R.id.action_fragmentMain_to_interstitial)
        }

        btnNative.setOnClickListener {
            Navigation.findNavController(activity!!,
                R.id.main_nav_host_fragment
            ).navigate(R.id.action_fragmentMain_to_native1)
        }

        btnRewarded.setOnClickListener {
            Navigation.findNavController(activity!!,
                R.id.main_nav_host_fragment
            ).navigate(R.id.action_fragmentMain_to_rewarded)
        }
    }


}
