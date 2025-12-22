package com.example.android.training.xml

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.android.databinding.ActivityClipTrainingBinding

/**
 *
 * 不裁剪子View，子View的子View可以超出绘制
 * android:clipChildren="false"
 *
 * 该View的子View是否可以在padding区域绘制，
 * 使用时需注意，如果该View有padding，父View就算设置了
 * android:clipChildren="false"，子View也不能超出，
 * 该View一定要设置android:clipToPadding="false".
 * android:clipToPadding="false"
 *
 * android:clipToOutline="true"
 * View设置了这个属性，同时背景drawable有圆角，
 * 那么子View不能超过父View的边界
 *
 * */
class ClipTrainingActivity: ComponentActivity() {

    private val mBinding by lazy { ActivityClipTrainingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(mBinding.root)

        mBinding.tvContent.post {
            mBinding.tvContent.animate()
                .scaleX(5f)
                .scaleY(5f)
                .setDuration(3000)
                .start()
        }

    }
}