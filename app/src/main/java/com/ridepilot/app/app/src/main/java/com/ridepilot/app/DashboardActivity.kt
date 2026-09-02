package com.ridepilot.app

import android.app.Activity
import android.os.Bundle
import android.widget.*

class DashboardActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 60, 40, 40)
        }

        // App Branding
        val title = TextView(this).apply {
            text = "RidePilot - Auto Pilot Mode"
            textSize = 22f
        }
        layout.addView(title)

        // Dynamic Price Banner
        val price = RidePilotEngine.getSubscriptionPrice()
        val offerText = if (RidePilotEngine.isLaunchOfferActive()) {
            "Special Launch Offer: ₹99/month (1st Order Free Trial)"
        } else {
            "Standard Plan: ₹149/month"
        }
        val banner = TextView(this).apply {
            text = "\nPlan: $offerText\n"
            textSize = 16f
        }
        layout.addView(banner)

        // Vehicle Filter
        val vehicleLabel = TextView(this).apply { text = "Select Vehicle Type:" }
        layout.addView(vehicleLabel)

        val vehicleSpinner = Spinner(this).apply {
            adapter = ArrayAdapter(this@DashboardActivity, android.R.layout.simple_spinner_dropdown_item, 
                arrayOf("Bike (Delivery + Ride)", "Auto (Ride + Parcel)", "Car (Only Rides)"))
        }
        layout.addView(vehicleSpinner)

        // Distance Filter
        val distanceLabel = TextView(this).apply { text = "\nSelect Distance Radius:" }
        layout.addView(distanceLabel)

        val distanceSpinner = Spinner(this).apply {
            adapter = ArrayAdapter(this@DashboardActivity, android.R.layout.simple_spinner_dropdown_item, 
                arrayOf("Under 2 KM", "Under 5 KM", "10 KM+"))
        }
        layout.addView(distanceSpinner)

        // Master Switch
        val toggle = ToggleButton(this).apply {
            textOn = "AUTO-PILOT ACTIVE (RUNNING)"
            textOff = "START RIDE-PILOT"
            isChecked = false
        }
        layout.addView(toggle)

        setContentView(layout)
    }
}
