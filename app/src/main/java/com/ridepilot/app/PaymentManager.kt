package com.ridepilot.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object PaymentManager {

    // Apna UPI ID yahan daalein
    private const val UPI_ID = "yourupi@bank" 
    private const val MERCHANT_NAME = "RidePilot Tech"

    fun startUPIPayment(activity: Activity, amount: Int) {
        val uri = Uri.Builder()
            .scheme("upi")
            .authority("pay")
            .appendQueryParameter("pa", UPI_ID)
            .appendQueryParameter("pn", MERCHANT_NAME)
            .appendQueryParameter("tn", "RidePilot Monthly Subscription")
            .appendQueryParameter("am", amount.toString())
            .appendQueryParameter("cu", "INR")
            .build()

        val upiIntent = Intent(Intent.ACTION_VIEW, uri)
        val chooser = Intent.createChooser(upiIntent, "Pay with UPI")

        try {
            activity.startActivityForResult(chooser, 101)
        } catch (e: Exception) {
            Toast.makeText(activity, "Koi UPI App nahi mila", Toast.LENGTH_SHORT).show()
        }
    }
}
