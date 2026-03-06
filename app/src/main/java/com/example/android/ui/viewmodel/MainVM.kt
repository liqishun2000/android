package com.example.android.ui.viewmodel

import android.app.Activity
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.android.training.room.dao.RecordDao
import com.example.android.training.room.database.RecordDatabase
import com.example.android.training.room.model.RecordInfo
import com.example.android.ui.viewmodel.base.BaseViewModel
import com.example.core.ktx.log
import kotlinx.coroutines.launch

class MainVM:BaseViewModel<MainVM.State,Unit>(State()) {

    fun click(activity: Activity) = viewModelScope.launch {
//        runCatching {
//            PermissionUtils.requestNotification(activity)
//        }.onSuccess {
//            val random = Random.nextInt(0, 100)
//            NotificationUtils.postNotification(activity, "test notification:$random")
//        }
//        activity.startActivity(Intent(activity, EqualizerTrainingActivity::class.java))
    }

    fun observe(activity: Activity){
        viewModelScope.launch {
            getRecordDao(activity).getAllListFlow().collect{
                log(it.toString())
            }
        }
    }

    fun clickAdd(activity: Activity){
        num.floatValue += 1
    }

    fun clickDelete(activity: Activity){

    }

    fun clickUpdate(activity: Activity){

    }

    fun clickQuery(activity: Activity){

    }

    private fun getRecordDao(activity: Activity):RecordDao{
        return RecordDatabase.getDatabase(activity).recordDao()
    }

    fun updateShake(shake: Boolean){
        updateState {
            it.copy(shake = shake)
        }
    }

    fun updateStepCount(stepCount: Int){
    }

    var num = mutableFloatStateOf(0f)
    init {
        updateState {
            it.copy(stepCount = { num.floatValue })
        }
    }

    data class State(
        val shake:Boolean = false,
        val stepCount: ()-> Float = { 0.5f  },
    )
}