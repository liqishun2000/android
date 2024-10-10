package com.example.android.senser

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.MutableLiveData

/**
 * 计步器 需要android.Manifest.permission.ACTIVITY_RECOGNITION权限
 * */
object StepCountSensor : SensorEventListener {
    val mStepCountLiveData: MutableLiveData<Int> = MutableLiveData()

    private var stepCount = 0

    private lateinit var sensorManager: SensorManager
    private var stepCounterSensor: Sensor? = null

    @JvmStatic
    fun init(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type === Sensor.TYPE_STEP_COUNTER) {
            stepCount = event.values[0].toInt()
            mStepCountLiveData.value = stepCount
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {


    }

}