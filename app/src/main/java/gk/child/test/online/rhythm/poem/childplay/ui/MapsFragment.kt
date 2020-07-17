package gk.child.test.online.rhythm.poem.childplay.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import gk.child.test.online.rhythm.poem.childplay.R


class MapsFragment : Fragment() {

    private  var mMap: GoogleMap? = null
    private val callback = OnMapReadyCallback { googleMap ->

        mMap=googleMap
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
//        val sydney = LatLng(-34.0, 151.0)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mMap = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mMap?.getMapAsync(callback)
        setUpMapIfNeeded()

    }


    private fun setUpMapIfNeeded() {

        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.

            if (activity?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) }
                == PackageManager.PERMISSION_GRANTED
            ) {
                mMap?.isMyLocationEnabled = true
            } else {
                Toast.makeText(
                    this.context,
                    "You have to accept to enjoy all app's services!",
                    Toast.LENGTH_LONG
                ).show()
                if (this.context?.let {
                        ContextCompat.checkSelfPermission(
                            it,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    }
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    mMap?.isMyLocationEnabled = true
                }
            }
            if (mMap != null) {
                mMap?.setOnMyLocationChangeListener(OnMyLocationChangeListener { arg0 -> // TODO Auto-generated method stub
                    val center = CameraUpdateFactory.newLatLng(
                        LatLng(
                            arg0.latitude,
                            arg0.longitude
                        )
                    )
                    val zoom = CameraUpdateFactory.zoomTo(12f)
                    mMap?.moveCamera(center)
                    mMap?.animateCamera(zoom)
                })
            }
        }
    }
}