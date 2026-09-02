package com.ridepilot.app

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import kotlin.random.Random

class LoginActivity : Activity() {

    private var generatedOtp: String? = null
    private var targetPhone: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val layoutPhone = findViewById<LinearLayout>(R.id.layoutPhoneInput)
        val layoutOtp = findViewById<LinearLayout>(R.id.layoutOtpInput)
        val etPhone = findViewById<EditText>(R.id.etPhoneNumber)
        val etOtp = findViewById<EditText>(R.id.etOtpCode)
        val btnSendOtp = findViewById<Button>(R.id.btnSendOtp)
        val btnVerifyOtp = findViewById<Button>(R.id.btnVerifyOtp)

        // Android 6.0+ ke liye SMS runtime permission check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.SEND_SMS), 102)
            }
        }

        btnSendOtp.setOnClickListener {
            val phone = etPhone.text.toString().trim()
            if (phone.length < 10) {
                Toast.makeText(this, "Kripya valid 10-digit mobile number enter karein", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            targetPhone = phone

            // 6-digit unique random OTP generate karein
            val randomCode = Random.nextInt(100000, 999999).toString()
            generatedOtp = randomCode

            try {
                val smsManager: SmsManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    this.getSystemService(SmsManager::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    SmsManager.getDefault()
                }

                val smsBody = "RidePilot Verification Code: $randomCode. Do not share this OTP with anyone."
                smsManager.sendTextMessage(targetPhone, null, smsBody, null, null)

                Toast.makeText(this, "OTP SMS sent to $targetPhone", Toast.LENGTH_LONG).show()

                // Mobile number chupakar OTP box dikhayein
                layoutPhone.visibility = View.GONE
                layoutOtp.visibility = View.VISIBLE
            } catch (e: Exception) {
                Toast.makeText(this, "SMS send nahi hua! SMS balance ya permission check karein", Toast.LENGTH_LONG).show()
            }
        }

        btnVerifyOtp.setOnClickListener {
            val enteredOtp = etOtp.text.toString().trim()

            if (enteredOtp.isEmpty()) {
                Toast.makeText(this, "Kripya SMS me aaya 6-digit OTP dalein", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Exact match validation
            if (enteredOtp == generatedOtp) {
                Toast.makeText(this, "OTP Verified Successfully!", Toast.LENGTH_SHORT).show()
                
                // Subscription paywall screen open karein
                val intent = Intent(this, SubscriptionActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Galat OTP! Kripya sahi 6-digit code dalein", Toast.LENGTH_LONG).show()
            }
        }
    }
}
