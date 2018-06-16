package com.jacobarau.shakelight

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ShakeDetectorListener {
    override fun onDirectionChanged(isPositive: Boolean) {
        println("Direction changed")
        textView2.setBackgroundColor(if (isPositive) Color.GREEN else Color.RED)
    }

    var shakeDetector : ShakeDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shakeDetector = ShakeDetector(this, this)
    }

    override fun onResume() {
        super.onResume()
        shakeDetector?.start()
    }

    override fun onPause() {
        super.onPause()
        shakeDetector?.stop()
    }
}
