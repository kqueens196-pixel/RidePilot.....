package com.ridepilot.app

import android.content.Context
import android.content.SharedPreferences

class EarningsTracker(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("ridepilot_earnings", Context.MODE_PRIVATE)

    fun addOrder(amount: Double) {
        val currentTotal = getTotalEarnings()
        val currentOrders = getCompletedOrdersCount()

        prefs.edit()
            .putFloat("total_earnings", (currentTotal + amount).toFloat())
            .putInt("total_orders", currentOrders + 1)
            .apply()
    }

    fun getTotalEarnings(): Double {
        return prefs.getFloat("total_earnings", 0.0f).toDouble()
    }

    fun getCompletedOrdersCount(): Int {
        return prefs.getInt("total_orders", 0)
    }

    fun resetDailyStats() {
        prefs.edit().clear().apply()
    }
}
