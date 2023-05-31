package com.exanple.implementkotlin

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mifonelibproj.core.Factory
import com.example.mifonelibproj.listener.MifoneCoreListener
import com.example.mifonelibproj.model.other.ConfigMifoneCore
import com.example.mifonelibproj.model.other.RegistrationState
import com.example.mifonelibproj.model.other.State
import com.example.mifonelibproj.model.other.User
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    var btnLogin: TextView? = null
    var user: User? = null
    var viewDialog: View? = null
    var textInputLayoutUsername: TextInputEditText? = null
    var textInputLayoutPassword: TextInputEditText? = null
    var dialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        textInputLayoutPassword = findViewById(R.id.edt_password)
        textInputLayoutUsername = findViewById(R.id.edt_username)
        btnLogin = findViewById(R.id.btn_login)
        viewDialog = layoutInflater.inflate(R.layout.layout_dialog_prog, null)
        Factory.registerListener(object : MifoneCoreListener {
            override fun onResultConfigAccount(isSuccess: Boolean, message: String) {
                if (isSuccess) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                    dialog!!.dismiss()
                }
            }

            override fun onCallStateChanged(state: State, message: String) {}
            override fun onRegistrationStateChanged(state: RegistrationState, message: String) {}
            override fun onError(message: String) {}
            override fun onExpiredAccessToken() {}
            override fun onResultConfigProxy(isSuccess: Boolean) {}
        })
        btnLogin?.setOnClickListener(View.OnClickListener { v: View? ->
            if (dialog == null) {
                dialog = Dialog(this@LoginActivity)
                dialog!!.window!!.setBackgroundDrawableResource(
                        R.color.transparent)
                dialog!!.setContentView(viewDialog as View)
            }
            dialog!!.show()
            user = User(textInputLayoutUsername?.getText().toString().trim { it <= ' ' }, textInputLayoutPassword?.getText().toString().trim { it <= ' ' }, "sf")
            Factory.createMifoneCore(this@LoginActivity, ConfigMifoneCore(5, "", ""), user)
            Factory.configCore()
        })
    }
}