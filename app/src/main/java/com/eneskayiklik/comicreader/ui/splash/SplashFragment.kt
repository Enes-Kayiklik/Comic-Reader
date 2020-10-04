package com.eneskayiklik.comicreader.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eneskayiklik.comicreader.R

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private lateinit var countDownTimer: CountDownTimer
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countDownTimer = object : CountDownTimer(1800L, 1000L) {
            override fun onFinish() {
                findNavController().navigate(R.id.action_splashFragment_to_bookShelfFragment)
            }

            override fun onTick(p0: Long) {}
        }
    }

    override fun onStart() {
        super.onStart()
        countDownTimer.start()
    }

    override fun onPause() {
        super.onPause()
        countDownTimer.cancel()
    }
}