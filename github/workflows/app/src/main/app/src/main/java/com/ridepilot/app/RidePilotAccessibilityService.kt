package com.ridepilot.app

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class RidePilotAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val rootNode = rootInActiveWindow ?: return

        // Scan button keywords across delivery apps
        val triggerKeywords = listOf("Accept", "Swipe to accept", "Tap to accept", "Order Lein")
        for (word in triggerKeywords) {
            val nodes = rootNode.findAccessibilityNodeInfosByText(word)
            if (nodes.isNotEmpty()) {
                nodes[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                break
            }
        }
    }

    override fun onInterrupt() {}
}
