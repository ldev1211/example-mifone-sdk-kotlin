package com.exanple.implementkotlin;

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.AudioManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mifonelibproj.core.Factory
import java.util.Timer
import java.util.TimerTask

class CallFragment    // Required empty public constructor
(var mNumberPhone: String?, var mType: Int) : Fragment() {
    var viewFragmentCall: View? = null
    var timer: Timer? = null
    var countTime = 0
    var tvDuration: TextView? = null
    var tvNumbPhone: TextView? = null
    var grOpt: LinearLayout? = null
    var btnEnd: ImageView? = null
    var btnAccept: ImageView? = null
    var optTransfer: ImageView? = null
    var optToggleSpeaker: ImageView? = null
    var optToggleMic: ImageView? = null
    var optToggle: TextView? = null
    private fun convertTimeToString(countTime: Int): String {
        val second = countTime % 60
        val minute = countTime / 60
        return (if (minute < 10) "0$minute" else minute.toString()) + ":" + if (second < 10) "0$second" else second.toString()
    }

    var isPaused = false
    var isMuteMic = false
    var isOnSpeaker = false
    var pause: Drawable? = null
    var resume: Drawable? = null
    var muteMic: Drawable? = null
    var onMic: Drawable? = null
    var offSpeaker: Drawable? = null
    var onSpeaker: Drawable? = null
    var hold: Drawable? = null
    var holdSelected: Drawable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewFragmentCall = inflater.inflate(R.layout.fragment_call, container, false)
        pause = resources.getDrawable(R.drawable.pause)
        resume = resources.getDrawable(R.drawable.resume)
        muteMic = resources.getDrawable(R.drawable.mute_mic)
        onMic = resources.getDrawable(R.drawable.on_mic)
        hold = resources.getDrawable(R.drawable.shape_hold)
        holdSelected = resources.getDrawable(R.drawable.shape_hold_selected)
        offSpeaker = resources.getDrawable(R.drawable.speaker_off)
        onSpeaker = resources.getDrawable(R.drawable.speaker_on)
        timer = Timer()
        tvNumbPhone = viewFragmentCall?.findViewById(R.id.tv_numb_phone)
        tvDuration = viewFragmentCall?.findViewById(R.id.timing)
        btnAccept = viewFragmentCall?.findViewById(R.id.btn_answer)
        optToggleSpeaker = viewFragmentCall?.findViewById(R.id.opt_toggle_speaker)
        optToggleMic = viewFragmentCall?.findViewById(R.id.opt_toggle_mic)
        btnEnd = viewFragmentCall?.findViewById(R.id.btn_end_call)
        grOpt = viewFragmentCall?.findViewById(R.id.gr_opt)
        optToggle = viewFragmentCall?.findViewById(R.id.opt_toggle_pause_or_resume)
        optTransfer = viewFragmentCall?.findViewById(R.id.opt_transfer)
        optToggle?.setOnClickListener(View.OnClickListener { v: View? ->
            isPaused = !isPaused
            optToggle?.setBackground(if (isPaused) holdSelected else hold)
            optToggle?.setTextColor(Color.parseColor(if (isPaused) "#FF000000" else "#FFFFFFFF"))
            if (isPaused) Factory.holdCall() else Factory.resumCall()
        })
        optToggleMic?.setOnClickListener(View.OnClickListener { v: View? ->
            isMuteMic = !isMuteMic
            optToggleMic?.setImageDrawable(if (isMuteMic) muteMic else onMic)
            val audioManager = requireActivity().getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.mode = AudioManager.MODE_IN_CALL
            audioManager.isMicrophoneMute = isMuteMic
        })
        optToggleSpeaker?.setOnClickListener(View.OnClickListener { v: View? ->
            isOnSpeaker = !isOnSpeaker
            optToggleSpeaker?.setImageDrawable(if (isOnSpeaker) onSpeaker else offSpeaker)
            val audioManager = requireActivity().getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.mode = AudioManager.MODE_IN_CALL
            audioManager.isSpeakerphoneOn = isOnSpeaker
        })
        optTransfer?.setOnClickListener(View.OnClickListener { v: View? ->
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.addToBackStack(null)
            transaction.add(R.id.frame_fragment, TransferFragment(), "TRANSFER_FRAGMENT")
            transaction.commit()
        })
        Log.d(MainActivity.TAG, "onCreateView: $mType")
        if (mNumberPhone == null) {
            tvNumbPhone?.setText(Factory.getNumbPhoneCallIn())
        }
        if (mType == TYPE_CONNECTED) {
            grOpt?.setVisibility(View.VISIBLE)
            timer!!.schedule(object : TimerTask() {
                override fun run() {
                    Handler(Looper.getMainLooper()).post { tvDuration?.setText(convertTimeToString(++countTime)) }
                }
            }, 0, 1000)
            btnAccept?.setVisibility(View.GONE)
        } else if (mType == TYPE_CALL_OUT) {
            Factory.makeCall(mNumberPhone)
            tvNumbPhone?.setText(mNumberPhone)
            btnAccept?.setVisibility(View.GONE)
        }
        btnAccept?.setOnClickListener(View.OnClickListener { v: View? ->
            Factory.acceptCall()
            btnAccept?.setVisibility(View.GONE)
            timer!!.schedule(object : TimerTask() {
                override fun run() {
                    Handler(Looper.getMainLooper()).post { tvDuration?.setText(convertTimeToString(++countTime)) }
                }
            }, 0, 1000)
        })
        btnEnd?.setOnClickListener(View.OnClickListener { v: View? -> Factory.cancelCall() })
        return viewFragmentCall
    }

    companion object {
        var TYPE_INCOMING = 0
        var TYPE_CALL_OUT = 1
        var TYPE_CONNECTED = 2
    }
}