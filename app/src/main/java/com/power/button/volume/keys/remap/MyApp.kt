package com.power.button.volume.keys.remap

import android.app.Application
import android.content.Context
import com.cioccarellia.ksprefs.KsPrefs
import dagger.hilt.android.HiltAndroidApp

/**
 * @Author: Muhammad Hasnain Altaf
 * @Date: 21/05/2024
 */

@HiltAndroidApp
class MyApp: Application(){

    companion object {
        lateinit var appContext: Context
        val prefs by lazy { KsPrefs(appContext) }
    }
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}