package com.exanple.implementkotlin

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.mifonelibproj.core.FactoryMifone
import com.example.mifonelibproj.listener.MifoneCoreListener
import com.example.mifonelibproj.model.other.RegistrationState
import com.example.mifonelibproj.model.other.State

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var TAG_FRAGMENT_CALL = "FRAGMENT_CALL"
    var editText: EditText? = null
    var button: ImageView? = null
    var t1: TextView? = null
    var t2: TextView? = null
    var t3: TextView? = null
    var t4: TextView? = null
    var t5: TextView? = null
    var t6: TextView? = null
    var t7: TextView? = null
    var t8: TextView? = null
    var fragmentTransaction : FragmentTransaction? = null
    var t9: TextView? = null
    var t0: TextView? = null
    var ts: TextView? = null
    var tsh: TextView? = null
    var tvState: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val permission = Permission(this)
        if (!permission.checkPermissions(arrayOf<String?>(Manifest.permission.RECORD_AUDIO))) {
            permission.requestPermissions(arrayOf<String?>(Manifest.permission.RECORD_AUDIO), 0)
        }
        tvState = findViewById(R.id.tv_state)
        editText = findViewById(R.id.editTextNumber)
        button = findViewById(R.id.button)
        t1 = findViewById(R.id.num_1)
        t2 = findViewById(R.id.num_2)
        t3 = findViewById(R.id.num_3)
        t4 = findViewById(R.id.num_4)
        t5 = findViewById(R.id.num_5)
        t6 = findViewById(R.id.num_6)
        t7 = findViewById(R.id.num_7)
        t8 = findViewById(R.id.num_8)
        t9 = findViewById(R.id.num_9)
        t0 = findViewById(R.id.num_0)
        ts = findViewById(R.id.star)
        tsh = findViewById(R.id.sharp)
        t1?.setOnClickListener(this)
        t2?.setOnClickListener(this)
        t3?.setOnClickListener(this)
        t4?.setOnClickListener(this)
        t5?.setOnClickListener(this)
        t6?.setOnClickListener(this)
        t7?.setOnClickListener(this)
        t8?.setOnClickListener(this)
        t9?.setOnClickListener(this)
        t0?.setOnClickListener(this)
        ts?.setOnClickListener(this)
        tsh?.setOnClickListener(this)
        button?.setOnClickListener(View.OnClickListener { v: View? ->
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.add(R.id.frame_fragment, CallFragment(editText?.getText().toString(), CallFragment.TYPE_CALL_OUT), TAG_FRAGMENT_CALL)
            fragmentTransaction.commit()
        })
        FactoryMifone.registerListener(object : MifoneCoreListener {
            override fun onResultConfigAccount(isSuccess: Boolean, message: String) {
                Log.d(TAG, "onResultConfigAccount: $isSuccess, message: $message")
            }

            override fun onCallStateChanged(state: State, message: String) {
                Log.d(TAG, "onCallStateChanged: $state, message: $message")
                when (state.toInt()) {
                    State.Released -> {
                        fragmentTransaction = supportFragmentManager.beginTransaction()
                        val fragment = supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_CALL)
                        fragmentTransaction?.remove(fragment!!)?.commit()
                    }

                    State.IncomingReceived -> {
                        fragmentTransaction = supportFragmentManager.beginTransaction()
                        fragmentTransaction?.addToBackStack(null)
                        fragmentTransaction?.replace(R.id.frame_fragment, CallFragment(null, CallFragment.TYPE_INCOMING), TAG_FRAGMENT_CALL)
                        fragmentTransaction?.commit()
                    }

                    State.Connected -> {
                        val bundle = Bundle()
                        bundle.putString("actionConnected", "connected")
                        val fragmentCall: Fragment = CallFragment(null, CallFragment.TYPE_CONNECTED)
                        fragmentCall.arguments = bundle
                        supportFragmentManager.beginTransaction()
                                .replace(R.id.frame_fragment, fragmentCall, TAG_FRAGMENT_CALL).commit()
                    }
                }
            }

            override fun onRegistrationStateChanged(state: RegistrationState, message: String) {
                Log.d(TAG, "onRegistrationStateChanged: code state: " + state.toInt() + ", message: " + state.toMessage())
                if (state.toInt() == RegistrationState.PROGRESS) {
                    tvState?.setText("Registration in progressing")
                    tvState?.setTextColor(Color.parseColor("#DFA248"))
                } else if (state.toInt() == RegistrationState.OK) {
                    tvState?.setText("Online")
                    tvState?.setTextColor(Color.parseColor("#69A30C"))
                }
            }

            override fun onError(message: String) {
                Log.d(TAG, "onError: $message")
            }

            override fun onExpiredAccessToken() {}
            override fun onResultConfigProxy(isSuccess: Boolean) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.num_0) editText!!.setText(editText!!.text.toString() + "0") else if (id == R.id.num_1) editText!!.setText(editText!!.text.toString() + "1") else if (id == R.id.num_2) editText!!.setText(editText!!.text.toString() + "2") else if (id == R.id.num_3) editText!!.setText(editText!!.text.toString() + "3") else if (id == R.id.num_4) editText!!.setText(editText!!.text.toString() + "4") else if (id == R.id.num_5) editText!!.setText(editText!!.text.toString() + "5") else if (id == R.id.num_6) editText!!.setText(editText!!.text.toString() + "6") else if (id == R.id.num_7) editText!!.setText(editText!!.text.toString() + "7") else if (id == R.id.num_8) editText!!.setText(editText!!.text.toString() + "8") else if (id == R.id.num_9) editText!!.setText(editText!!.text.toString() + "9") else if (id == R.id.sharp) editText!!.setText(editText!!.text.toString() + "*") else if (id == R.id.star) editText!!.setText(editText!!.text.toString() + "#")
    }

    companion object {
        var TAG = "IMPLEMENTDEBUG"
    }
}