package com.app.hasnain.sample.mvvmdihilt.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.viewbinding.ViewBinding

/**
 * @Author: Muhammad Hasnain Altaf
 * @Date: 22/05/2024
 */
abstract class BaseFragment <VB:ViewBinding> :Fragment(){
    private var _binding: VB?=null
    protected val binding get()=_binding!!

    abstract fun  getViewBinding(inflater: LayoutInflater,container: ViewGroup?):VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=getViewBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}