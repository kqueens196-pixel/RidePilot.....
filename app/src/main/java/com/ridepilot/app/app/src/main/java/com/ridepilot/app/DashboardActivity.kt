package com.ridepilot.app

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class DashboardActivity : Activity() {

    private lateinit var earningsTracker: EarningsTracker
    private var isScannerOn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        earningsTracker = EarningsTracker(this)

        val tvEarnings = findViewById<TextView>(R.id.tvTotalEarnings)
        val tvOrders = findViewById<TextView>(R.id.tvTotalOrders)
        val tvStatus = findViewById<TextView>(R.id.tvPilotStatus)
        val btnToggle = findViewById<Button>(R.id.btnToggleService)
        val btnSimulate = findViewById<Button>(R.id.btnSimulateOrder)
        val btnReset = findViewById<Button>(R.id.btnResetStats)

        fun updateUI() {
            tvEarnings.text = "₹${earningsTracker.getTotalEarnings().toInt()}"
            tvOrders.text = "${earningsTracker.getCompletedOrdersCount()}"
        }

        updateUI()

        // Toggle Auto-pilot ON/OFF
        btnToggle.setOnClickListener {
            isScannerOn = !isScannerOn
            if (isScannerOn) {
                tvStatus.text = "AUTO-PILOT ACTIVE"
                tvStatus.setTextColor(Color.parseColor("#10B981"))
                btnToggle.text = "PAUSE SCANNER"
                btnToggle.setBackgroundColor(Color.parseColor("#EF4444"))
            } else {
                tvStatus.text = "AUTO-PILOT PAUSED"
                tvStatus.setTextColor(Color.parseColor("#94A3B8"))
                btnToggle.text = "RESUME SCANNER"
                btnToggle.setBackgroundColor(Color.parseColor("#10B981"))
            }
        }

        // Test complete ride to verify earnings update
        btnSimulate.setOnClickListener {
            earningsTracker.addOrder(55.0)
            updateUI()
            Toast.makeText(this, "Order Completed: +₹55 added!", Toast.LENGTH_SHORT).show()
        }

        btnReset.setOnClickListener {
            earningsTracker.resetDailyStats()
            updateUI()
            Toast.makeText(this, "Daily stats reset to 0", Toast.LENGTH_SHORT).show()
        }
    }
}
