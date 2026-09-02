package com.ridepilot.app

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import android.widget.ImageView

class FloatingOverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var floatingIcon: ImageView

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        floatingIcon = ImageView(this).apply {
            setImageResource(android.R.drawable.presence_online) // Green indicator dot/icon
            setBackgroundColor(0xFF00FF88.toInt())
            setPadding(15, 15, 15, 15)
        }

        val params = WindowManager.LayoutParams(
            120,
            120,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 0
            y = 300
        }

        floatingIcon.setOnClickListener {
            // Instant Toggle Pilot ON / OFF
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        }

        windowManager.addView(floatingIcon, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::floatingIcon.isInitialized) {
            windowManager.removeView(floatingIcon)
        }
    }
}
