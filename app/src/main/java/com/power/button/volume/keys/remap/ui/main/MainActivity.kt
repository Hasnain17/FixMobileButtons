package com.power.button.volume.keys.remap.ui.main

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.app.hasnain.sample.mvvmdihilt.ui.base.BaseActivity
import com.google.android.material.button.MaterialButton
import com.power.button.volume.keys.remap.MyApp
import com.power.button.volume.keys.remap.R
import com.power.button.volume.keys.remap.databinding.ActivityMainBinding
import com.power.button.volume.keys.remap.utils.isAccessibilityServiceEnabled
import com.power.button.volume.keys.remap.utils.requestForAccessibilityPermission
import com.power.button.volume.keys.remap.utils.service.AccessibilityService
import com.power.button.volume.keys.remap.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding> () {

    private lateinit var dialog:Dialog

    private lateinit var arrayAdapter:ArrayAdapter<String>

    private lateinit var autoCompletePlus:AutoCompleteTextView

    private lateinit var autoCompleteMinus:AutoCompleteTextView

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

        dialog= Dialog(this)
        callForDropDown()
    }

    private fun callForPermissionDialog() {
        dialog.setContentView(R.layout.layout_permission_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window!!.attributes.windowAnimations = R.style.DialogFragmentAnimation

        dialog.setCancelable(false)

        dialog.findViewById<MaterialButton>(R.id.mtPermission).setOnClickListener{
            this.requestForAccessibilityPermission()
        }

        dialog.findViewById<ImageView>(R.id.mtCancelExit).setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()

            val handler = Handler(Looper.getMainLooper())
            val runnable = object : Runnable {
                override fun run() {
                    if (isAccessibilityServiceEnabled(this@MainActivity,AccessibilityService::class.java)) {
                        dialog.dismiss()
                        handler.removeCallbacks(this)
                    } else {
                        handler.postDelayed(this, 1000)
                    }
                }
            }

            val lifecycleObserver = object : DefaultLifecycleObserver {
                override fun onResume(owner: LifecycleOwner) {
                    handler.post(runnable)
                }

                override fun onPause(owner: LifecycleOwner) {
                    handler.removeCallbacks(runnable)
                }
            }

            ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)

            // Remove observer when dialog is dismissed
            dialog.setOnDismissListener {
                ProcessLifecycleOwner.get().lifecycle.removeObserver(lifecycleObserver)
                handler.removeCallbacks(runnable)
            }


    }

    private fun callForDropDown() {
        val actions=resources.getStringArray(R.array.actionList)
         arrayAdapter = ArrayAdapter(this, R.layout.drop_down_item, actions)

         autoCompletePlus = binding.autoCompleteActionsPlus
        autoCompleteMinus = binding.autoCompleteActionsMinus
        autoCompletePlus.setAdapter(arrayAdapter)
        autoCompleteMinus.setAdapter(arrayAdapter)



        //PLUS
        binding.autoCompleteActionsPlus.setOnItemClickListener{parent, _, postion,_->
            when(postion){
                0-> {
                    showToast("Default Action")
                    MyApp.prefs.push("eventIDPlus",0)
                }
                1-> {
                    showToast("TURN OFF SCREEN")
                    MyApp.prefs.push("eventIDPlus",1)
                }
                2-> {
                    showToast("OPEN POWER MENU")
                    MyApp.prefs.push("eventIDPlus",2)
                }
                3-> {
                    showToast("DO NOTHING")
                    MyApp.prefs.push("eventIDPlus",3)
                }
            }
        }

        //MINUS
        binding.autoCompleteActionsMinus.setOnItemClickListener{parent, _, postion,_->
            when(postion){
                0-> {
                    showToast("Default Action")
                    MyApp.prefs.push("eventIDMinus",0)
                }
                1-> {
                    showToast("TURN OFF SCREEN")
                    MyApp.prefs.push("eventIDMinus",1)
                }
                2-> {
                    showToast("OPEN POWER MENU")
                    MyApp.prefs.push("eventIDMinus",2)
                }
                3-> {
                    showToast("DO NOTHING")
                    MyApp.prefs.push("eventIDMinus",3)

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val isMyServiceEnabled = isAccessibilityServiceEnabled(this@MainActivity,AccessibilityService::class.java)
        if (!isMyServiceEnabled){
            if (!dialog.isShowing){
                callForPermissionDialog()
            }
        }

        initSettingsForMainView()
    }

    private fun initSettingsForMainView() {

        //Set Default value for Volume Down
        if (!MyApp.prefs.exists("eventIDMinus")){
            MyApp.prefs.push("eventIDMinus",0)
        }

        //Set Default value for Volume Plus
        if (!MyApp.prefs.exists("eventIDPlus")){
            MyApp.prefs.push("eventIDPlus",0)
        }


        val valuePlus=MyApp.prefs.pull<Int>("eventIDPlus")
        val valueMinus=MyApp.prefs.pull<Int>("eventIDMinus")

        try {
            // Set default selection for autoCompletePlus
            autoCompletePlus.setText(arrayAdapter.getItem(valuePlus).toString(), false)
            // Simulate click on the default selection
            autoCompletePlus.performCompletion()

            // Set default selection for autoCompleteMinus
            autoCompleteMinus.setText(arrayAdapter.getItem(valueMinus).toString(), false)
            // Simulate click on the default selection
            autoCompleteMinus.performCompletion()
        }
        catch (e:Exception){
            e.printStackTrace().toString()
        }    }

    override fun onDestroy() {
        super.onDestroy()
    }
}