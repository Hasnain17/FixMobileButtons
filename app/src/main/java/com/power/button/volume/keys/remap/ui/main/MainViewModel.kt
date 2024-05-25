package com.power.button.volume.keys.remap.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.power.button.volume.keys.remap.model.Key
import com.power.button.volume.keys.remap.repository.KeyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author: Muhammad Hasnain Altaf
 * @Date: 23/05/2024
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val keyRepository: KeyRepository): ViewModel(){

    private val _key=MutableLiveData<Key>()

    val key: LiveData<Key> get()=_key

    fun loadKey(){
        _key.value=keyRepository.getKey()
    }
}