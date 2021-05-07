package com.example.appcm
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.appcm.api.EndPoints
import com.example.appcm.api.LoginPost
import com.example.appcm.api.ServiceBuilder
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_add_marker.*
import retrofit2.Call
import retrofit2.Response

class AddMarkerActivity : AppCompatActivity() {

    private var selectedType: Int? = 1
    var longitude: Double? = null
    var latitude: Double? = null
    private var idUser: Int? = null

    //LOCATION
    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var lastLocation: Location


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_marker)

        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //GET USER ID FROM MAPS ACTIVITY
        val intentUser: Bundle? = intent.extras
        idUser = intentUser?.getInt(MapActivity.EXTRA_IDUSERLOGIN)

        //LOCATION
        createLocationRequest()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                val latLng = LatLng(lastLocation.latitude, lastLocation.longitude)
                latitude = latLng.latitude
                longitude = latLng.longitude
            }
        }

        val textField = findViewById<AutoCompleteTextView>(R.id.txtMarkerType)
        val mTypes = resources.getStringArray(R.array.problemTypes)


        if (textField != null) {
            val adapter =
                ArrayAdapter(this@AddMarkerActivity, R.layout.menu_items_problems, mTypes)
            textField.setText(mTypes[0])
            textField.setAdapter(adapter)
            textField.setOnItemClickListener { _, _, position, _ ->
                selectedType = position + 1
            }
        }

        buttonAddMarker.setOnClickListener {
            if (addMarkerDescription.text.isNullOrEmpty()) {
                Toast.makeText(
                    this@AddMarkerActivity,
                    R.string.description_required,
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                val request =
                    ServiceBuilder.buildService(EndPoints::class.java)

                val descr = addMarkerDescription.text.toString()

                val mType = selectedType

                val lat = latitude

                val long = longitude

                val userID = idUser

                val addMarker = request.postMarker(
                    descr, mType, lat, long, userID)

                addMarker.enqueue(object : retrofit2.Callback<LoginPost> {
                    override fun onResponse(
                        call: Call<LoginPost>,
                        response: Response<LoginPost>,
                    ) {
                        if (response.isSuccessful) {

                            val op: LoginPost = response.body()!!

                            if (!op.status) {
                                when (op.error) {
                                    "updating" -> Toast.makeText(
                                        this@AddMarkerActivity,
                                        R.string.error_updating_data,
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            } else {
                                finish()
                            }
                        }
                    }

                    override fun onFailure(call: Call<LoginPost>, t: Throwable) {
                        Toast.makeText(
                            this@AddMarkerActivity,
                            R.string.errorwebservice,
                            Toast.LENGTH_SHORT
                        )
                            .show()

                    }
                })
            }
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this@AddMarkerActivity,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@AddMarkerActivity,
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
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}