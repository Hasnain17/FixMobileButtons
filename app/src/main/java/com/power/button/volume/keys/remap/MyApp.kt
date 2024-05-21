package com.power.button.volume.keys.remap

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.cioccarellia.ksprefs.KsPrefs

/**
 * @Author: Muhammad Hasnain Altaf
 * @Date: 21/05/2024
 */
class MyApp: MultiDexApplication(){

    companion object {
        lateinit var appContext: Context
        val prefs by lazy { KsPrefs(appContext) }
    }
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}