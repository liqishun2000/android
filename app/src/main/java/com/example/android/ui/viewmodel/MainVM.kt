package com.example.android.ui.viewmodel

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.example.android.training.room.dao.RecordDao
import com.example.android.training.room.database.RecordDatabase
import com.example.android.training.room.model.RecordInfo
import com.example.android.training.xml.RecycleViewTraining
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
        viewModelScope.launch {
            getRecordDao(activity).insert(RecordInfo(name = "1"))
        }
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
        updateState { it.copy(stepCount = stepCount) }
    }

    data class State(
        val shake:Boolean = false,
        val stepCount:Int = 0,
    )
}