package com.ridepilot.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ridepilot.app.R

class SubscriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)

        // Buttons linking by exact XML IDs
        val btnSubscribeUpi = findViewById<Button>(R.id.btnSubscribeUpi)
        val btnPayCard = findViewById<Button>(R.id.btnPayCard)
        val btnPayNetBanking = findViewById<Button>(R.id.btnPayNetBanking)
        val btnFreeTrial = findViewById<Button>(R.id.btnFreeTrial)

        // UPI Click Action
        btnSubscribeUpi.setOnClickListener {
            launchUpiPayment("your-upi-id@okhdfcbank", "RidePilot Subscription", "199.00")
        }

        // Debit / Credit Card Action
        btnPayCard.setOnClickListener {
            Toast.makeText(this, "Redirecting to Card Gateway...", Toast.LENGTH_SHORT).show()
        }

        // Net Banking Action
        btnPayNetBanking.setOnClickListener {
            Toast.makeText(this, "Opening Bank Selection...", Toast.LENGTH_SHORT).show()
        }

        // Free Trial Action
        btnFreeTrial.setOnClickListener {
            Toast.makeText(this, "Free Trial Activated!", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun launchUpiPayment(upiId: String, name: String, amount: String) {
        val uri = Uri.parse("upi://pay").buildUpon()
            .appendQueryParameter("pa", upiId)
            .appendQueryParameter("pn", name)
            .appendQueryParameter("am", amount)
            .appendQueryParameter("cu", "INR")
            .build()

        val upiIntent = Intent(Intent.ACTION_VIEW)
        upiIntent.data = uri

        val chooser = Intent.createChooser(upiIntent, "Pay with UPI")
        if (chooser.resolveActivity(packageManager) != null) {
            startActivity(chooser)
        } else {
            Toast.makeText(this, "No UPI App found on this device!", Toast.LENGTH_SHORT).show()
        }
    }
}
