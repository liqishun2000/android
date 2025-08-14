package com.example.android.training.xml

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.android.databinding.ActivityClipTrainingBinding

/**
 *
 * 不裁剪子View，子View的子View可以超出绘制
 * android:clipChildren="false"
 *
 * 是否可以在padding区域绘制
 * android:clipToPadding="false"
 *
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