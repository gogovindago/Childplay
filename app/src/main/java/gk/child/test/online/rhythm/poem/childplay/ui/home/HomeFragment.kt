package gk.child.test.online.rhythm.poem.childplay.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import gk.child.test.online.rhythm.poem.childplay.R


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val TAG = "MainActivity"

    private var mAdView: AdView? = null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })


        // Find Banner ad

        // Find Banner ad
        mAdView = root.findViewById(R.id.adView)
        val adRequest =
            AdRequest.Builder().build()
        // Display Banner ad
        // Display Banner ad
        mAdView!!.loadAd(adRequest)
        return root
    }
}