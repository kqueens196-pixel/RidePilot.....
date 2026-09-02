package com.ridepilot.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*

class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 80, 50, 50)
        }

        val header = TextView(this).apply {
            text = "RidePilot Login"
            textSize = 24f
        }
        layout.addView(header)

        val phoneInput = EditText(this).apply {
            hint = "Mobile Number (10 Digits)"
            inputType = android.text.InputType.TYPE_CLASS_PHONE
        }
        layout.addView(phoneInput)

        val otpInput = EditText(this).apply {
            hint = "Enter 6-Digit OTP"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
        }
        layout.addView(otpInput)

        val verifyBtn = Button(this).apply {
            text = "Verify OTP & Start"
            setOnClickListener {
                if (phoneInput.text.length == 10) {
                    val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Valid 10-digit number enter karein", Toast.LENGTH_SHORT).show()
                }
            }
        }
        layout.addView(verifyBtn)

        setContentView(layout)
    }
}
