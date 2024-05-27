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
        val actionTypeMinus=MyApp.prefs.pull<Int>("eventIDMinus")
        val actionTypePlus=MyApp.prefs.pull<Int>("eventIDPlus")
        /***
         * For Default Action   --> 0
         * For Turn Off Screen  --> 1
         * For Open Power Menu  --> 2
         * For Do Nothing       --> 3
         * */

        //For Volume Key Up
        if (event.keyCode == KeyEvent.KEYCODE_VOLUME_UP && event.action == KeyEvent.ACTION_DOWN){
            if (actionTypePlus!=0)
            {
                if (actionTypePlus==1){
                    //For Turn OFF Screen
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                    {
                        performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN);
                    } else
                    {
                        // Provide an alternative or show a message
                        performGlobalAction(GLOBAL_ACTION_POWER_DIALOG);
                    }
                    return true;
                }

                if (actionTypePlus==2){
                    //For Open Power Menu

                    performGlobalAction(GLOBAL_ACTION_POWER_DIALOG);
                    return true

                }

                if (actionTypePlus==3)
                {
                    //For Do Nothing
                    return true
                }
            }
        }



        //For Volume Key Down
        if (event.keyCode == KeyEvent.KEYCODE_VOLUME_DOWN && event.action == KeyEvent.ACTION_DOWN){
            if (actionTypeMinus!=0)
            {
                    if (actionTypeMinus==1){
                        //For Turn OFF Screen
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                        {
                            performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN);
                        } else
                        {
                            // Provide an alternative or show a message
                            performGlobalAction(GLOBAL_ACTION_POWER_DIALOG);
                        }
                        return true;
                    }

                    if (actionTypeMinus==2){
                        //For Open Power Menu

                        performGlobalAction(GLOBAL_ACTION_POWER_DIALOG);
                        return true

                    }

                    if (actionTypeMinus==3)
                    {
                        //For Do Nothing
                        return true
                    }
            }
        }


        return super.onKeyEvent(event)
    }


    private fun showPowerMenu() {
        try {
            val keyEvent = KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_POWER)
            val instrumentation = Instrumentation()
            instrumentation.sendKeyDownUpSync(keyEvent.keyCode)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
