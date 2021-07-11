package com.jrangel.locationkotlinexample

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/** Tutorial
 * https://www.tutorialspoint.com/how-to-get-the-current-gps-location-programmatically-on-android-using-kotlin
 */

class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var tvLatitude: TextView
    private lateinit var tvLongitude: TextView
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvLatitude = findViewById(R.id.tvLatitude)
        tvLongitude = findViewById(R.id.tvLongitude)
        getLocation()
    }

    fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    fun showMap(v:View) {
        val intentMap = Intent(applicationContext, MapsActivity::class.java)
        val bundle = Bundle()
        val latitude = tvLatitude.text.toString()
        val longitude = tvLongitude.text.toString()
        bundle.putDouble("latitude",latitude.toDouble())
        bundle.putDouble("longitude",longitude.toDouble())
        intentMap.putExtras(bundle)
        startActivity(intentMap)
    }

    override fun onLocationChanged(p0: Location) {
        tvLatitude.text = p0.latitude.toString()
        tvLongitude.text = p0.longitude.toString()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}