package com.ridepilot.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class SubscriptionActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)

        val btnPay = findViewById<Button>(R.id.btnSubscribeUpi)
        val btnTrial = findViewById<Button>(R.id.btnFreeTrial)

        btnPay.setOnClickListener {
            val uri = Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", "ridepilot@upi")
                .appendQueryParameter("pn", "RidePilot")
                .appendQueryParameter("am", "99.00")
                .appendQueryParameter("cu", "INR")
                .appendQueryParameter("tn", "RidePilot Monthly Pro")
                .build()

            val upiIntent = Intent(Intent.ACTION_VIEW, uri)
            try {
                startActivityForResult(Intent.createChooser(upiIntent, "Pay with UPI"), 101)
            } catch (e: Exception) {
                Toast.makeText(this, "Opening Dashboard...", Toast.LENGTH_SHORT).show()
                proceedToDashboard()
            }
        }

        btnTrial.setOnClickListener {
            Toast.makeText(this, "Free Trial Activated!", Toast.LENGTH_SHORT).show()
            proceedToDashboard()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        proceedToDashboard()
    }

    private fun proceedToDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}
