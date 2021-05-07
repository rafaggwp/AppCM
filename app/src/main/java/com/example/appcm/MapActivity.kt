package com.example.appcm

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.appcm.Login.Companion.EXTRA_USERID
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.example.appcm.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.appcm.Login
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_map.*



class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var markers: List<Marker>
    private var userID: Int? = null
    private var userLocation: LatLng? = null
    private lateinit var markerDescription: TextView
    private lateinit var latLng: TextView
    private lateinit var mType: TextView
    private var markerID: String = ""

    //LOCATION

    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var lastLocation: Location


  //cr
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        createLocationRequest()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                userLocation = LatLng(lastLocation.latitude, lastLocation.longitude)
            }
        }


        val fab: View = findViewById(R.id.fabAdd)
        fab.setOnClickListener {
            val intent = Intent(
                this@MapActivity, AddMarkerActivity::class.java
            )
            intent.putExtra(EXTRA_IDUSERLOGIN, userID)
            startActivity(intent)
        }


        this.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_login_24)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_map, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                val sharedPrefs: SharedPreferences =
                    getSharedPreferences(getString(R.string.pref_file_key), Context.MODE_PRIVATE)

                val editor = sharedPrefs.edit()
                editor.clear().apply()

                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
                return true
            }
                R.id.notestab -> {

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    return true

            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this@MapActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MapActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 10000 //5 em 5 segundos
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }


    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
        getMarker(userLastLocation = null)
    }

    private fun getMarker(userLastLocation: LatLng?) {
        val intent: Bundle? = intent.extras
        userID = intent?.getInt(Login.EXTRA_USERID)
        val request = ServiceBuilder.buildService(EndPoints::class.java)

        val call = request.getMarker()
        var position: LatLng
        call.enqueue(object : Callback<List<Marker>> {

            override fun onResponse(call: Call<List<Marker>>, response: Response<List<Marker>>) {
                if (response.isSuccessful) {
                    markers = response.body()!!

                    for (marker in markers) {
                        position = LatLng(
                            marker.lat.toString().toDouble(),
                            marker.lng.toString().toDouble()
                        )

                        val items = resources.getStringArray(R.array.problemTypes)

                        var mTypes = ""

                        when (marker.mType) {
                            1 -> {
                                mTypes = items[0].toString()
                            }
                            2 -> {
                                mTypes = items[1].toString()
                            }
                            3 -> {
                                mTypes = items[2].toString()
                            }
                        }

                        // Setting a custom info window adapter for the google map
                        mMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
                            override fun getInfoContents(p0: com.google.android.gms.maps.model.Marker?): View {
                                var v: View? = null

                                try {

                                    // Getting view from the layout file info_window_layout
                                    v = layoutInflater.inflate(R.layout.info_popup_marker, null)

                                    markerDescription =
                                        v!!.findViewById<View>(R.id.descr) as TextView
                                    markerDescription.text = p0?.title

                                    mType =
                                        v.findViewById<View>(R.id.mType) as TextView
                                    mType.text = p0?.snippet?.substringBefore(" -")

                                    latLng =
                                        v.findViewById<View>(R.id.latLngMarker) as TextView
                                    latLng.text = p0?.position.toString()

                                    markerID = p0?.title?.substringBefore(" -").toString()

                                } catch (ev: Exception) {
                                    print(ev.message)
                                }
                                return v!!
                            }

                            override fun getInfoWindow(p0: com.google.android.gms.maps.model.Marker?): View? {
                                return null
                            }
                        })


                        mMap.setOnMarkerClickListener { mark ->

                            mark.showInfoWindow()

                            val handler = Handler()
                            handler.postDelayed({ mark.showInfoWindow() }, 400)

                            true
                        }
                        if (marker.user_id == userID) {

                            mMap.addMarker(
                                MarkerOptions().position(position)
                                    .title("" + marker.id + " - " + marker.descr)
                                    .icon(
                                        BitmapDescriptorFactory.defaultMarker(
                                            BitmapDescriptorFactory.HUE_AZURE
                                        )
                                    )
                            )
                        } else {
                            mMap.addMarker(
                                MarkerOptions().position(position)
                                    .title("" + marker.id + " - " + marker.descr)
                            )
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Marker>>, t: Throwable) {

            }
        })





    }
    companion object {
        const val EXTRA_IDUSERLOGIN = "USERIDLOGIN"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1


    }

}