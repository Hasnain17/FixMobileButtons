package com.app.hasnain.sample.mvvmdihilt.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * @Author: Muhammad Hasnain Altaf
 * @Date: 22/05/2024
 */
abstract class BaseActivity <VB:ViewBinding>:AppCompatActivity() {
    private var _binding:VB?=null
    protected val binding get() = _binding!!

    abstract fun  getViewBinding():VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=getViewBinding()

        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}