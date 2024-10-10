package com.example.android.senser

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.lifecycle.MutableLiveData
import kotlin.math.sqrt

/**
 * 摇一摇监听
 * */
object ShakeObserver : SensorEventListener {

    val mShakeLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private var isShaking = false

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    @JvmStatic
    fun init(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    private const val STOP = 8888
    private const val SHAKE = 9999

    private val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                STOP -> {
                    mShakeLiveData.value = false
                }

                SHAKE -> {
                    mShakeLiveData.value = true
                }
            }
        }
    }


    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val acceleration = sqrt((x * x + y * y + z * z).toDouble())

        if (acceleration > 11) {
            if (!isShaking) {
                isShaking = true
                mHandler.removeMessages(STOP)
                mHandler.sendEmptyMessage(SHAKE)
            }
        } else {
            if (isShaking) {
                isShaking = false
                mHandler.sendEmptyMessageDelayed(STOP, 500)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {


    }


}