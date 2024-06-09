package com.power.button.volume.keys.remap.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.power.button.volume.keys.remap.utils.service.AccessibilityService


/**
 * @Author: Muhammad Hasnain Altaf
 * @Date: 22/05/2024
 */
fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Context.showToast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}



//check for the accessibility Service.
fun isAccessibilityServiceEnabled(context: Context, service: Class<out AccessibilityService>): Boolean {
    val enabledServices = Settings.Secure.getString(context.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
    if (enabledServices.isNullOrEmpty()) {
        return false
    }
    val colonSplitter = TextUtils.SimpleStringSplitter(':')
    colonSplitter.setString(enabledServices)
    while (colonSplitter.hasNext()) {
        val componentName = colonSplitter.next()
        if (ComponentName.unflattenFromString(componentName)?.className == service.name) {
            return true
        }
    }
    return false
}

//request for the accessibility service

fun  Context.requestForAccessibilityPermission(){
    try {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        /** request permission via StartActivity activity for result  */
        startActivity(intent)
    }
    catch (e:Exception){
        e.printStackTrace().toString();
    }
}

