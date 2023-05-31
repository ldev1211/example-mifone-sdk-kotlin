package com.exanple.implementkotlin

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class Permission(private val context: Context) {
    fun checkPermissions(permissions: Array<String?>): Boolean {
        var res = true
        for (permission in permissions) {
            res = res and (ContextCompat.checkSelfPermission(context, permission!!) == PackageManager.PERMISSION_GRANTED)
        }
        return res
    }

    fun requestPermissions(permissions: Array<String?>?, requestCode: Int) {
        (context as AppCompatActivity).requestPermissions(permissions!!, requestCode)
    }

    companion object {
        const val REQUEST_CODE_AUDIO_PERMISSION = 0
        const val REQUEST_CODE_READ_PHONE_STATE = 1
    }
}