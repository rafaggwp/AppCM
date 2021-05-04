package com.example.appcm

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.appcm.Login.Companion.EXTRA_USERID
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.appcm.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.appcm.Login
import com.google.android.gms.maps.model.BitmapDescriptorFactory


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var markers: List<Marker>
    private var userID: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        this.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_login_24)


        //  setSupportActionBar(findViewById(R.id.toolbar))


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)
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
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val intent: Bundle? = intent.extras
        userID = intent?.getInt(Login.EXTRA_USERID)

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getMarker()
        call.enqueue(object : Callback<List<Marker>> {

            override fun onResponse(call: Call<List<Marker>>, response: Response<List<Marker>>) {
                if (response.isSuccessful) {
                    markers = response.body()!!
                    for (marker in markers){
                        var position = LatLng(marker.lat.toString().toDouble(), marker.lng.toString().toDouble())
                        if (marker.user_id == userID) {

                            mMap.addMarker(
                                    MarkerOptions().position(position)
                                            .title("" + marker.id + " - " + marker.address).icon(
                                                    BitmapDescriptorFactory.defaultMarker(
                                                            BitmapDescriptorFactory.HUE_AZURE
                                                    )
                                            )
                            )
                        } else {
                            mMap.addMarker(
                                    MarkerOptions().position(position)
                                            .title("" + marker.id + " - " + marker.address)
                            )
                        }
                    }


                    }
                }

            //fail

            override fun onFailure(call: Call<List<Marker>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}