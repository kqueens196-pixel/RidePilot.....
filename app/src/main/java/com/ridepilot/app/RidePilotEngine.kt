package com.ridepilot.app

import android.content.Context
import java.util.Date

object RidePilotEngine {
    // 90-Day Launch Offer Setup
    // Launch Date: Sept 2026
    private const val LAUNCH_TIMESTAMP = 1788393600000L 
    private const val NINETY_DAYS_MS = 90L * 24 * 60 * 60 * 1000

    fun isLaunchOfferActive(): Boolean {
        return (System.currentTimeMillis() - LAUNCH_TIMESTAMP) <= NINETY_DAYS_MS
    }

    fun getSubscriptionPrice(): Int {
        return if (isLaunchOfferActive()) 99 else 149
    }

    fun canAcceptOrder(context: Context): Boolean {
        val prefs = context.getSharedPreferences("RidePilotPrefs", Context.MODE_PRIVATE)
        val isSubscribed = prefs.getBoolean("is_subscribed", false)
        val freeOrdersUsed = prefs.getInt("free_orders_used", 0)

        // Subscribed users can always accept
        if (isSubscribed) return true

        // Launch period allows 1 free order
        if (isLaunchOfferActive() && freeOrdersUsed < 1) {
            return true
        }

        return false
    }

    fun markOrderAccepted(context: Context) {
        val prefs = context.getSharedPreferences("RidePilotPrefs", Context.MODE_PRIVATE)
        val used = prefs.getInt("free_orders_used", 0)
        prefs.edit().putInt("free_orders_used", used + 1).apply()
    }
}
