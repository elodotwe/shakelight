package com.jacobarau.shakelight

import android.content.Context
import android.hardware.*
import android.hardware.SensorManager.SENSOR_DELAY_GAME
import java.lang.Math.abs

class ShakeDetector(private val listener: ShakeDetectorListener, context: Context) : SensorEventListener{
    private var mSensorManager: SensorManager? = null
    private var mSensor: Sensor? = null
    private val shakeThreshold = 10.0
    private var lastDirectionWasPositive = false

    init {
        mSensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    fun start() {
        mSensorManager?.registerListener(this, mSensor, SENSOR_DELAY_GAME)
    }

    fun stop() {
        mSensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        val sensorX = event?.values?.get(0) ?: return
        val exceeds = abs(sensorX) > shakeThreshold
        val isPositive = sensorX > 0

        if (exceeds) {
            if (isPositive != lastDirectionWasPositive) {
                listener.onDirectionChanged(isPositive)
                lastDirectionWasPositive = isPositive
            }
        }
    }
}