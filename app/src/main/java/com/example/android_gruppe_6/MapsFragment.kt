package com.example.android_gruppe_6

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.android_gruppe_6.domain.getHarbors
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*


class MapsFragment : Fragment() {
    private val REQUEST_LOCATION_PERMISSION = 1
    private var locationPermissionGranted = false
    private val defaultLocation = LatLng(63.4, 10.4)
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var currentLocation: LatLng
    private val markers = arrayListOf<Marker>()

    private var lastKnownLocation: Location? = null
    val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        activity?.let { MapsInitializer.initialize(it) }
        map = googleMap
        enableMyLocation()

        googleMap.setMaxZoomPreference(6.0F)

        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        val harbors = getHarbors()
        for (harbor in harbors) {
            var coordinates = LatLng(harbor.lat, harbor.lon)
            val marker = MarkerOptions().icon(bitmapDescriptorFromVector(requireActivity(), R.drawable.ic_icon_grey)).position(coordinates).title(harbor.name).snippet(harbor.name)
            /*marker.visible(false)*/
            markers.add(map.addMarker(marker)!!)
           /* val mapMarker = googleMap.addMarker(
                MarkerOptions()
            )*/


        }

        map.setOnMarkerClickListener { marker ->
            val harbor = harbors.find { it.name == marker.title }
            if (harbor != null) {
                this.findNavController().navigate(
                    MapsFragmentDirections
                        .actionMapsFragmentToShowTideFragment2(harbor)
                )
            }

            true
        }



        val zoomlevel = 4f
        /*getLastKnownLocation()*/



        /*googleMap.addMarker(MarkerOptions().position(stroemsund).title("Marker in Stroemsund"))*/
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, zoomlevel))
        /*getDeviceLocation()*/


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fusedLocationClient =
            activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!
        return inflater.inflate(R.layout.fragment_maps, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            } /*else {
                requestLocationPermission()
            }*/
        }
    }

    private fun isPermissionGranted(): Boolean {
        return activity?.let {
            ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    fun getLastKnownLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    currentLocation = LatLng(location.latitude, location.longitude)
                    // use your location object
                    // get latitude , longitude and other info from this
                }

            }

    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationClient.lastLocation
                activity?.let {
                    locationResult.addOnCompleteListener(it) { task ->
                        if (task.isSuccessful) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.result
                            if (lastKnownLocation != null) {
                                map.moveCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        LatLng(
                                            lastKnownLocation!!.latitude,
                                            lastKnownLocation!!.longitude
                                        ), DEFAULT_ZOOM.toFloat()
                                    )
                                )
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.")
                            Log.e(TAG, "Exception: %s", task.exception)
                            map.moveCamera(
                                CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                            )
                            map.uiSettings.isMyLocationButtonEnabled = false
                        }
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            map.isMyLocationEnabled = true
        } else {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION
                )
            }
        }
    }

    private fun requestLocationPermission() {
        Toast.makeText(requireContext(),"Spør etter tillatelser...", Toast.LENGTH_SHORT).show()
        requestPermissions(permissions, REQUEST_LOCATION_PERMISSION)
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }


    companion object {
        private val TAG = "HG-LOG"
        private const val DEFAULT_ZOOM = 4


    }
}