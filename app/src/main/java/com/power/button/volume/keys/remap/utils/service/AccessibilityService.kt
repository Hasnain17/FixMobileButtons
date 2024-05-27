package com.power.button.volume.keys.remap.utils.service

/**
 * @Author: Muhammad Hasnain Altaf
 * @Date: 27/05/2024
 */
import android.accessibilityservice.AccessibilityService
import android.app.Instrumentation
import android.os.Build
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import com.power.button.volume.keys.remap.MyApp

class AccessibilityService : AccessibilityService() {




    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Handle other accessibility events if needed
    }

    override fun onInterrupt() {
        // Handle service interruption
    }

    override fun onKeyEvent(event: KeyEvent): Boolean {
        val actionTypeMinus = MyApp.prefs.pull<Int>("eventIDMinus")
        val actionTypePlus = MyApp.prefs.pull<Int>("eventIDPlus")
        /***
         * For Default Action   --> 0
         * For Turn Off Screen  --> 1
         * For Open Power Menu  --> 2
         * For Do Nothing       --> 3
         * */

        fun performAction(actionType: Int): Boolean {
            return when (actionType) {
                1 -> {
                    // For Turn OFF Screen
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)
                    } else {
                        performGlobalAction(GLOBAL_ACTION_POWER_DIALOG)
                    }
                    true
                }
                2 -> {
                    // For Open Power Menu
                    performGlobalAction(GLOBAL_ACTION_POWER_DIALOG)
                    true
                }
                3 -> {
                    // For Do Nothing
                    true
                }
                else -> false
            }
        }

        if (event.action == KeyEvent.ACTION_DOWN) {
            return when (event.keyCode) {
                KeyEvent.KEYCODE_VOLUME_UP -> {
                    if (actionTypePlus != 0) performAction(actionTypePlus) else super.onKeyEvent(event)
                }
                KeyEvent.KEYCODE_VOLUME_DOWN -> {
                    if (actionTypeMinus != 0) performAction(actionTypeMinus) else super.onKeyEvent(event)
                }
                else -> super.onKeyEvent(event)
            }
        }
        return super.onKeyEvent(event)
    }
}
