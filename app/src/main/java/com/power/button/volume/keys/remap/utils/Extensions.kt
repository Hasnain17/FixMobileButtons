package com.power.button.volume.keys.remap.utils

import android.content.Context
import android.view.View
import android.widget.Toast

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