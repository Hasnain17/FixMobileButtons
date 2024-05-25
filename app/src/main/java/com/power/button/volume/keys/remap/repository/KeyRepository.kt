package com.power.button.volume.keys.remap.repository

import com.power.button.volume.keys.remap.model.Key
import javax.inject.Inject

/**
 * @Author: Muhammad Hasnain Altaf
 * @Date: 23/05/2024
 */
class KeyRepository @Inject constructor(){
    fun getKey (): Key {
        return Key(keyName = "Power", 0)
    }
}