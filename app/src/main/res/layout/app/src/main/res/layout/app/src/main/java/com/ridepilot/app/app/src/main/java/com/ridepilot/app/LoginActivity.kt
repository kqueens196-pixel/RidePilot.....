package com.ridepilot.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast

class LoginActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val layoutPhone = findViewById<LinearLayout>(R.id.layoutPhoneInput)
        val layoutOtp = findViewById<LinearLayout>(R.id.layoutOtpInput)
        val etPhone = findViewById<EditText>(R.id.etPhoneNumber)
        val etOtp = findViewById<EditText>(R.id.etOtpCode)
        val btnSendOtp = findViewById<Button>(R.id.btnSendOtp)
        val btnVerifyOtp = findViewById<Button>(R.id.btnVerifyOtp)

        btnSendOtp.setOnClickListener {
            val phone = etPhone.text.toString().trim()
            if (phone.length < 10) {
                Toast.makeText(this, "Valid 10-digit number enter karein", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Switch to OTP box
            layoutPhone.visibility = View.GONE
            layoutOtp.visibility = View.VISIBLE
            Toast.makeText(this, "OTP Sent to $phone", Toast.LENGTH_SHORT).show()
        }

        btnVerifyOtp.setOnClickListener {
            val otp = etOtp.text.toString().trim()
            if (otp.length == 6) {
                Toast.makeText(this, "Verified Successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SubscriptionActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Enter 6 digit OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
