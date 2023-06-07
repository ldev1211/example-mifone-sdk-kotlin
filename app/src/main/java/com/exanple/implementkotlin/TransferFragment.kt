package com.exanple.implementkotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mifonelibproj.core.FactoryMifone

class TransferFragment internal constructor() : Fragment(), View.OnClickListener {
    var viewTransferFragment: View? = null
    var t1: TextView? = null
    var t2: TextView? = null
    var t3: TextView? = null
    var t4: TextView? = null
    var t5: TextView? = null
    var t6: TextView? = null
    var t7: TextView? = null
    var t8: TextView? = null
    var t9: TextView? = null
    var t0: TextView? = null
    var ts: TextView? = null
    var tsh: TextView? = null
    var btnTransfer: ImageView? = null
    var editText: EditText? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewTransferFragment = inflater.inflate(R.layout.fragment_transfer, container, false)
        editText = view?.findViewById(R.id.edt_transfer)
        btnTransfer = view?.findViewById(R.id.btn_transfer)
        t1 = viewTransferFragment?.findViewById(R.id.num_1_trans)
        t2 = viewTransferFragment?.findViewById(R.id.num_2_trans)
        t3 = viewTransferFragment?.findViewById(R.id.num_3_trans)
        t4 = viewTransferFragment?.findViewById(R.id.num_4_trans)
        t5 = viewTransferFragment?.findViewById(R.id.num_5_trans)
        t6 = viewTransferFragment?.findViewById(R.id.num_6_trans)
        t7 = viewTransferFragment?.findViewById(R.id.num_7_trans)
        t8 = viewTransferFragment?.findViewById(R.id.num_8_trans)
        t9 = viewTransferFragment?.findViewById(R.id.num_9_trans)
        t0 = viewTransferFragment?.findViewById(R.id.num_0_trans)
        ts = viewTransferFragment?.findViewById(R.id.star_trans)
        tsh = viewTransferFragment?.findViewById(R.id.sharp_trans)
        btnTransfer?.setOnClickListener(View.OnClickListener { v: View? -> FactoryMifone.transfer(editText?.getText().toString()) })
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
        return viewTransferFragment
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.num_0) editText!!.setText(editText!!.text.toString() + "0") else if (id == R.id.num_1) editText!!.setText(editText!!.text.toString() + "1") else if (id == R.id.num_2) editText!!.setText(editText!!.text.toString() + "2") else if (id == R.id.num_3) editText!!.setText(editText!!.text.toString() + "3") else if (id == R.id.num_4) editText!!.setText(editText!!.text.toString() + "4") else if (id == R.id.num_5) editText!!.setText(editText!!.text.toString() + "5") else if (id == R.id.num_6) editText!!.setText(editText!!.text.toString() + "6") else if (id == R.id.num_7) editText!!.setText(editText!!.text.toString() + "7") else if (id == R.id.num_8) editText!!.setText(editText!!.text.toString() + "8") else if (id == R.id.num_9) editText!!.setText(editText!!.text.toString() + "9") else if (id == R.id.sharp) editText!!.setText(editText!!.text.toString() + "*") else if (id == R.id.star) editText!!.setText(editText!!.text.toString() + "#")
    }
}