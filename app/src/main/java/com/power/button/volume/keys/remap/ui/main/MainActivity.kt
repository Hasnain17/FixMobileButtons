package com.power.button.volume.keys.remap.ui.main

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.activity.viewModels
import com.app.hasnain.sample.mvvmdihilt.ui.base.BaseActivity
import com.google.android.material.button.MaterialButton
import com.power.button.volume.keys.remap.R
import com.power.button.volume.keys.remap.databinding.ActivityMainBinding
import com.power.button.volume.keys.remap.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding> () {

    private val mainViewModel: MainViewModel by viewModels()
    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.loadKey()

        mainViewModel.key.observe(this){
            binding.key=it
        }

        callForDropDown()
    }

    private fun callForPermissionDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.layout_permission_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window!!.attributes.windowAnimations = R.style.DialogFragmentAnimation

        dialog.setCancelable(false)

        dialog.findViewById<MaterialButton>(R.id.mtPermission).setOnClickListener{
            showToast("click")
        }

        dialog.findViewById<ImageView>(R.id.mtCancelExit).setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()

    }

    private fun callForDropDown() {
        val actions=resources.getStringArray(R.array.actionList)
        val arrayAdapter = ArrayAdapter(this, R.layout.drop_down_item, actions)

        val autoCompletePlus = binding.autoCompleteActionsPlus
        val autoCompleteMinus = binding.autoCompleteActionsMinus
        autoCompletePlus.setAdapter(arrayAdapter)
        autoCompleteMinus.setAdapter(arrayAdapter)

        // Set default selection for autoCompletePlus
        if (arrayAdapter.count > 0) {
            autoCompletePlus.setText(arrayAdapter.getItem(0).toString(), false)
            // Simulate click on the default selection
            autoCompletePlus.performCompletion()
        }


        // Set default selection for autoCompleteMinus
        if (arrayAdapter.count > 0) {
            autoCompleteMinus.setText(arrayAdapter.getItem(0).toString(), false)
            // Simulate click on the default selection
            autoCompleteMinus.performCompletion()
        }

        //PLUS
        binding.autoCompleteActionsPlus.setOnItemClickListener{parent, _, postion,_->
            when(postion){
                0-> {
                    showToast("Default Action")
                }
                1-> {
                    showToast("TURN OFF SCREEN")

                }
                2-> {
                    showToast("OPEN POWER MENU")
                }
            }
        }

        //MINUS
        binding.autoCompleteActionsMinus.setOnItemClickListener{parent, _, postion,_->
            when(postion){
                0-> {
                    showToast("Default Action")
                }
                1-> {
                    showToast("TURN OFF SCREEN")

                }
                2-> {
                    showToast("OPEN POWER MENU")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        callForPermissionDialog()
    }
}