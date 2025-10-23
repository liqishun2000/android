package com.example.android.training.audio

import android.media.MediaPlayer
import android.media.audiofx.DynamicsProcessing
import android.media.audiofx.DynamicsProcessing.Limiter
import android.media.audiofx.DynamicsProcessing.Mbc
import android.media.audiofx.Equalizer
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RawRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.android.R

data class EqPreset(
    val id: Int,
    val name: String,
    val desc: String,
    val eqGains: List<Float>, // 10个频段的增益值 (SDK>=28)
    val eqGains5Band: List<Float>, // 5个频段的增益值 (SDK<28)
    val code: String,
    val iconRes: Int = R.mipmap.ic_launcher,
)
val useDynamicsProcessing = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

val presetList =
    listOf(
        // 经典系列预设
        EqPreset(
            16, "古典", "突出弦乐和管乐的音色",
            listOf(0f, 0f, 0f, 0f, 0f, 0f, -6f, -6f, -6f, -7f), // 10段
            listOf(0f, 0f, 0f, -6f, -7f), // 5段（与原应用低版本一致）
            "CLA"
        ),

        EqPreset(
            17, "舞曲", "增强低频和高频，适合电子舞曲",
            listOf(8f, 6f, 2f, 0f, 0f, -4f, -6f, -6f, 0f, 0f),
            listOf(8f, 2f, 0f, -6f, 0f),
            "DAN"
        ),

        EqPreset(
            18, "平直", "原音重现，无任何增强或衰减",
            listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f),
            listOf(0f, 0f, 0f, 0f, 0f),
            "FLT"
        ),

        EqPreset(
            19, "爵士", "温暖清晰，适合萨克斯和钢琴",
            listOf(3f, 3f, 1f, 2f, -1f, -1f, 0f, 1f, 2f, 4f),
            listOf(3f, 1f, -1f, 1f, 4f),
            "JAZ"
        ),

        EqPreset(
            20, "流行", "突出人声和节奏感",
            listOf(-1f, 0f, 0f, 1f, 4f, 3f, 1f, 0f, -1f, 1f),
            listOf(0f, 0f, 4f, 1f, 1f),
            "POP"
        ),

        EqPreset(
            21, "摇滚", "增强鼓点和吉他音色",
            listOf(5f, 2f, -3f, -6f, -3f, 3f, 6f, 8f, 8f, 8f),
            listOf(2f, -3f, 3f, 8f, 8f),
            "RCK"
        ),

        EqPreset(
            22, "现场", "模拟现场演出效果",
            listOf(-4f, 0f, 5f, 6f, 7f, 6f, 3f, 2f, 1f, 0f),
            listOf(0f, 5f, 7f, 2f, 0f),
            "OST"
        ),

        EqPreset(
            23, "低音", "大幅增强低频",
            listOf(6f, 4f, 6f, 2f, 0f, 0f, 0f, 0f, 0f, 0f),
            listOf(6f, 6f, 0f, 0f, 0f),
            "BAS"
        ),

        EqPreset(
            24, "高音", "大幅增强高频",
            listOf(9f, 9f, 9f, 5f, 0f, 4f, 11f, 11f, 11f, 11f),
            listOf(9f, 0f, 4f, 11f, 11f),
            "TRE"
        ),

        EqPreset(
            25, "重金属", "重金属音乐专用",
            listOf(-2f, 5f, 4f, -2f, -2f, -1f, 2f, 3f, 1f, 4f),
            listOf(5f, 4f, -2f, 2f, 4f),
            "HMT"
        ),

        EqPreset(
            26, "强劲", "强劲有力的音效",
            listOf(10f, 10f, 5f, -5f, -3f, 2f, 8f, 10f, 11f, 12f),
            listOf(10f, 5f, 2f, 10f, 12f),
            "STG"
        ),

        EqPreset(
            27, "柔和", "柔和细腻的音效",
            listOf(2f, 0f, -2f, -4f, -2f, 2f, 5f, 7f, 8f, 9f),
            listOf(0f, -2f, 2f, 8f, 9f),
            "GEN"
        ),

        EqPreset(
            28, "蓝调", "蓝调音乐优化",
            listOf(2f, 6f, 4f, 0f, -2f, -1f, 2f, 2f, 1f, 3f),
            listOf(6f, 4f, -1f, 2f, 3f),
            "BLU"
        ),

        EqPreset(
            29, "民谣", "民谣和原声音乐",
            listOf(0f, 3f, 0f, 0f, 1f, 4f, 5f, 3f, 0f, 1f),
            listOf(3f, 0f, 1f, 3f, 1f),
            "FLK"
        ),

        EqPreset(
            30, "聚会", "聚会场景专用",
            listOf(6f, 6f, 0f, 0f, 0f, 0f, 0f, 0f, 6f, 6f),
            listOf(6f, 0f, 0f, 0f, 6f),
            "GTH"
        ),

        // 特殊效果预设
        EqPreset(
            1, "环绕声", "环绕声效果",
            listOf(2f, 2f, 1f, 0f, 0f, 2f, 4f, 6f, 5f, 5f),
            listOf(2f, 1f, 0f, 3f, 5f),
            "WSR"
        ),

        EqPreset(
            2, "影院", "影院效果",
            listOf(6f, 5f, 3f, 1f, 0f, 1f, 2f, 4f, 5f, 4f),
            listOf(5f, 2f, 0f, 1f, 4f),
            "HTR"
        ),

        EqPreset(
            3, "音乐厅", "音乐厅效果",
            listOf(3f, 2f, 1f, 0f, 0f, 1f, 3f, 5f, 4f, 4f),
            listOf(3f, 1f, 1f, 2f, 3f),
            "CTH"
        ),

        EqPreset(
            4, "地铁", "地铁环境优化",
            listOf(5f, 5f, 4f, 2f, 0f, -1f, -2f, -3f, -3f, -4f),
            listOf(6f, 2f, 0f, -2f, -4f),
            "SBH"
        ),

        EqPreset(
            5, "室内", "室内环境优化",
            listOf(-2f, -1f, 0f, 0f, 1f, 2f, 2f, 1f, 0f, -1f),
            listOf(-3f, -1f, 1f, 1f, -2f),
            "SID"
        ),

        EqPreset(
            6, "夜间驾驶", "夜间驾驶优化",
            listOf(6f, 5f, 3f, 0f, -2f, -2f, 0f, 3f, 5f, 4f),
            listOf(4f, 2f, 0f, 0f, 1f),
            "NDR"
        ),

        EqPreset(
            7, "专注", "专注模式",
            listOf(-3f, -3f, -2f, 0f, 1f, 1f, 0f, -2f, -3f, -4f),
            listOf(-5f, -2f, 2f, 1f, -3f),
            "FCM"
        ),

        EqPreset(
            8, "浴室", "浴室环境效果",
            listOf(2f, 2f, 1f, 0f, 0f, 1f, 3f, 5f, 5f, 4f),
            listOf(2f, 1f, 0f, 3f, 4f),
            "SHE"
        ),

        EqPreset(
            9, "低保真", "低保真效果",
            listOf(-4f, -2f, 0f, 1f, 1f, 2f, -2f, -4f, -5f, -5f),
            listOf(-3f, -2f, 2f, 1f, -1f),
            "LFF"
        ),

        EqPreset(
            10, "动漫", "动漫音效优化",
            listOf(-2f, -1f, 0f, 1f, 2f, 3f, 4f, 5f, 5f, 4f),
            listOf(0f, 2f, 2f, 3f, 5f),
            "AOS"
        ),

        EqPreset(
            11, "男声", "男声增强",
            listOf(4f, 4f, 3f, 2f, 1f, 0f, -2f, -3f, -4f, -4f),
            listOf(4f, 3f, 1f, 0f, -1f),
            "MVP"
        ),

        EqPreset(
            12, "女声", "女声增强",
            listOf(-4f, -3f, -2f, 0f, 1f, 2f, 4f, 6f, 5f, 3f),
            listOf(-1f, 0f, 2f, 4f, 6f),
            "DFM"
        ),

        EqPreset(
            13, "强力摇滚", "强力摇滚",
            listOf(3f, 3f, 2f, 0f, 1f, 2f, 3f, 4f, 2f, 0f),
            listOf(6f, 3f, 1f, 2f, 4f),
            "RKP"
        ),

        EqPreset(
            14, "阳光", "阳光明媚的效果",
            listOf(-1f, 0f, 1f, 2f, 3f, 4f, 5f, 4f, 3f, 2f),
            listOf(1f, 2f, 2f, 3f, 2f),
            "SUM"
        ),


        EqPreset(
            15, "破碎", "破碎音效",
            listOf(-5f, -4f, -3f, -2f, 2f, 3f, 1f, -2f, -4f, -5f),
            listOf(-2f, 0f, 3f, 1f, -1f),
            "BRH"
        ),
        EqPreset(
            31, "test", "test",
            listOf(-15f, -15f, -15f, -15f, -15f, -15f, -15f, -15f, -15f, -15f),
            listOf(-15f, -15f, -15f, -15f, -15f),
            "test"
        )


    )

class EqualizerTrainingActivity : ComponentActivity() {
    private var mediaPlayer: MediaPlayer? = null

    // SDK>=28 使用 DynamicsProcessing
    private var mDynamicsProcessing: DynamicsProcessing? = null
    private var mDynamicsProcessingEq: DynamicsProcessing.Eq? = null
    private var mDynamicsProcessingMbc: Mbc? = null
    private var mDynamicsProcessingLimiter: Limiter? = null

    // SDK<28 使用传统 Equalizer
    private var mEqualizer: Equalizer? = null


    // 频段中心频率
    private val bandFrequencies10Band =
        floatArrayOf(31f, 62f, 125f, 250f, 500f, 1000f, 2000f, 4000f, 8000f, 16000f)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface {
                EqualizerDemoScreen(
                    onPlay = {
                        startPlayback(R.raw.sample_audio)
                    },
                    onSelectPreset = { preset ->
                        applyEqPreset(preset)
                    },
                    onReset = {
                        resetEqualizer()
                    }
                )
            }
        }
    }

    private fun startPlayback(@RawRes resId: Int) {
        try {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(this, resId)
            mediaPlayer?.let { mp ->
                val sessionId = mp.audioSessionId

                if (useDynamicsProcessing) {
                    // SDK>=28 使用 DynamicsProcessing
                    setupDynamicsProcessing(sessionId)
                } else {
                    // SDK<28 使用传统 Equalizer
                    setupTraditionalEqualizer(sessionId)
                }

                mp.setOnCompletionListener { stopPlayback() }
                mp.isLooping = true
                mp.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun setupDynamicsProcessing(sessionId: Int) {
        try {
            // 使用与原应用完全一致的配置：10段PreEq + 10段MBC + 10段PostEq + Limiter
            val config = DynamicsProcessing.Config.Builder(
                DynamicsProcessing.VARIANT_FAVOR_FREQUENCY_RESOLUTION,
                1,  // 单声道
                true, bandFrequencies10Band.size,  // PreEq: 启用, 10段
                true, bandFrequencies10Band.size,  // MBC: 启用, 10段多段压缩
                true, bandFrequencies10Band.size,  // PostEq: 启用, 10段
                true      // Limiter: 启用
            ).build()

            mDynamicsProcessing = DynamicsProcessing(Int.MAX_VALUE, sessionId, config)
            mDynamicsProcessing?.enabled = true


            mDynamicsProcessingEq = DynamicsProcessing.Eq(true, true, bandFrequencies10Band.size)
            mDynamicsProcessingEq?.isEnabled = true

            mDynamicsProcessingMbc = Mbc(true, true, bandFrequencies10Band.size)
            mDynamicsProcessingMbc?.isEnabled = true

            mDynamicsProcessingLimiter = Limiter(
                true,
                true,
                0,
                1f,
                60f,
                10f,
                -2f,
                0f
            )
            mDynamicsProcessingLimiter?.isEnabled = true

            for (i in bandFrequencies10Band.indices) {
                mDynamicsProcessingEq?.getBand(i)?.cutoffFrequency = bandFrequencies10Band[i]
                // TODO: 初始化音效
                mDynamicsProcessingMbc?.getBand(i)?.cutoffFrequency = bandFrequencies10Band[i]
            }
            mDynamicsProcessing?.setPreEqAllChannelsTo(mDynamicsProcessingEq)
            mDynamicsProcessing?.setMbcAllChannelsTo(mDynamicsProcessingMbc)
            mDynamicsProcessing?.setPostEqAllChannelsTo(mDynamicsProcessingEq)
            mDynamicsProcessing?.setLimiterAllChannelsTo(mDynamicsProcessingLimiter)

            Log.d("Equalizer", "DynamicsProcessing initialized for SDK>=28")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupTraditionalEqualizer(sessionId: Int) {
        try {
            mEqualizer = Equalizer(Int.MAX_VALUE, sessionId)
            mEqualizer?.enabled = true
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Equalizer", "Failed to initialize traditional Equalizer")
        }
    }

    private fun applyEqPreset(preset: EqPreset) {
        if (useDynamicsProcessing) {
            applyDynamicsProcessingPreset(preset)
        } else {
            applyTraditionalEqualizerPreset(preset)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun applyDynamicsProcessingPreset(preset: EqPreset) {
        val dp = mDynamicsProcessing ?: return
        dp.enabled = true

        for (i in preset.eqGains.indices) {
            setDynamicsProcessingBandGain(i, preset.eqGains[i])
        }

        Log.d("Equalizer", "Applied DynamicsProcessing preset: ${preset.name}")
    }

    private fun applyTraditionalEqualizerPreset(preset: EqPreset) {
        val equalizer = mEqualizer ?: return
        equalizer.enabled = true

        val numberOfBands = equalizer.numberOfBands
        val gainsToApply = preset.eqGains5Band

        for (i in gainsToApply.indices) {
            if (i < numberOfBands) {
                // 传统Equalizer使用毫贝单位，需要乘以100
                val gainInMillibel = (gainsToApply[i] * 100).toInt().toShort()
                try {
                    equalizer.setBandLevel(i.toShort(), gainInMillibel)
                } catch (e: Exception) {
                    Log.e("Equalizer", "Error setting band $i level: ${e.message}")
                }
            }
        }

        Log.d(
            "Equalizer",
            "Applied Traditional Equalizer preset: ${preset.name} with $numberOfBands bands"
        )
    }
    @RequiresApi(Build.VERSION_CODES.P)
    private fun setDynamicsProcessingBandGain(band: Int, gain: Float) {
        val mDynamicsProcessingEq = mDynamicsProcessingEq ?: return
        val mDynamicsProcessing = mDynamicsProcessing ?: return
        runCatching {
            mDynamicsProcessingEq.getBand(band).isEnabled = true
            mDynamicsProcessingEq.getBand(band).gain = gain
            mDynamicsProcessing.setPreEqBandAllChannelsTo(
                band,
                mDynamicsProcessingEq.getBand(band)
            )
            mDynamicsProcessing.setPostEqBandAllChannelsTo(
                band,
                mDynamicsProcessingEq.getBand(band)
            )
        }.onFailure {
            it.printStackTrace()
        }
    }

    private fun resetEqualizer() {
        if (useDynamicsProcessing) {
            mDynamicsProcessing?.enabled = false
        } else {
            mEqualizer?.enabled = false
        }

        Log.d("Equalizer", "Equalizer reset")
    }

    private fun stopPlayback() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null

            if (useDynamicsProcessing) {
                mDynamicsProcessing?.release()
                mDynamicsProcessing = null
            } else {
                mEqualizer?.release()
                mEqualizer = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        stopPlayback()
        super.onDestroy()
    }
}

@Composable
fun EqualizerDemoScreen(
    onPlay: () -> Unit,
    onSelectPreset: (EqPreset) -> Unit,
    onReset: () -> Unit
) {

    // 显示当前使用的均衡器类型
    val equalizerType = remember {
        if (useDynamicsProcessing) {
            "DynamicsProcessing (10段)"
        } else {
            "传统Equalizer (${android.media.audiofx.Equalizer(0, 0).numberOfBands}段)"
        }
    }

    var selectedPreset by remember { mutableStateOf<Int?>(null) }
    var isPlaying by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(24.dp))

        // 标题栏
        Text("均衡器效果", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))

        // 显示当前使用的均衡器类型
        Text(
            "当前使用: $equalizerType",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )

        // 频段信息显示
        Text("频段分布", style = MaterialTheme.typography.titleMedium)
        if (useDynamicsProcessing) {
            Text("31-62-125-250-500-1k-2k-4k-8k-16k Hz", style = MaterialTheme.typography.bodySmall)
        } else {
            Text("60-230-910-3600-14000 Hz", style = MaterialTheme.typography.bodySmall)
        }

        Spacer(Modifier.height(16.dp))

        // 控制按钮组
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = {
                    onPlay()
                    isPlaying = true
                },
                enabled = !isPlaying
            ) {
                Text("开始播放")
            }

            Button(
                onClick = {
                    onReset()
                    selectedPreset = null
                }
            ) {
                Text("重置均衡器")
            }
        }

        Spacer(Modifier.height(24.dp))

        // 当前选择的预设信息
        selectedPreset?.let { id-> presetList.find { it.id == id } }?.let { preset ->
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("当前应用预设:", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(8.dp))
                    Text("名称: ${preset.name}", style = MaterialTheme.typography.bodyLarge)
                    Text("代码: ${preset.code}", style = MaterialTheme.typography.bodyMedium)
                    Text("描述: ${preset.desc}", style = MaterialTheme.typography.bodySmall)

                    Spacer(Modifier.height(8.dp))
                    Text("增益设置:", style = MaterialTheme.typography.titleSmall)
                    if (useDynamicsProcessing) {
                        Text(
                            preset.eqGains.joinToString(" dB, ") + " dB",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    } else {
                        Text(
                            preset.eqGains5Band.joinToString(" dB, ") + " dB",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
        }

// 预设列表
        Text("选择均衡器预设", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        LazyColumn(Modifier.weight(1f)) {
            // 经典系列
            item {
                Text("经典系列", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
            }
            items(15, key = { presetList[it].id }) { idx ->
                val preset = presetList[idx]
                EqPresetItem(
                    preset = preset,
                    isSelected = selectedPreset == preset.id,
                    onClick = {
                        selectedPreset = preset.id
                        onSelectPreset(preset)
                    }
                )
            }

            // 特殊效果
            item {
                Spacer(Modifier.height(16.dp))
                Text("特殊效果", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
            }
            items(16,key = { presetList[it+15].id }) { idx ->
                val preset = presetList[idx + 15]
                key(preset.id) {
                    EqPresetItem(
                        preset = preset,
                        isSelected = selectedPreset == preset.id,
                        onClick = {
                            selectedPreset = preset.id
                            onSelectPreset(preset)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EqPresetItem(
    preset: EqPreset,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(if (isSelected) 8.dp else 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(preset.iconRes),
                contentDescription = preset.name,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(Modifier.width(16.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    preset.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    preset.desc,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(4.dp))

//                // 显示增益值
//                if (useDynamicsProcessing) {
//                    Text(
//                        "增益: " + preset.eqGains.joinToString(" dB, ") + " dB",
//                        style = MaterialTheme.typography.labelSmall,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant
//                    )
//                } else {
//                    Text(
//                        "增益: " + preset.eqGains5Band.joinToString(" dB, ") + " dB",
//                        style = MaterialTheme.typography.labelSmall,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant
//                    )
//                }

                // 显示频段影响描述
                Text(
                    getPresetEffectDescription(preset),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                if (isSelected) "✓ 已应用" else "点击应用",
                style = MaterialTheme.typography.labelMedium,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// 获取预设效果描述
fun getPresetEffectDescription(preset: EqPreset): String {
    return when (preset.code) {
        "CLA" -> "衰减高频，突出弦乐和管乐"
        "DAN" -> "增强低频和高频，适合电子舞曲"
        "FLT" -> "平直响应，原音重现"
        "JAZ" -> "温暖清晰，适合萨克斯和钢琴"
        "POP" -> "突出人声和节奏感"
        "RCK" -> "增强鼓点和吉他音色"
        "OST" -> "模拟现场演出效果"
        "BAS" -> "大幅增强低频"
        "TRE" -> "大幅增强高频"
        "HMT" -> "重金属音乐专用"
        "STG" -> "强劲有力的音效"
        "GEN" -> "柔和细腻的音效"
        "BLU" -> "蓝调音乐优化"
        "FLK" -> "民谣和原声音乐"
        "GTH" -> "聚会场景专用"
        "WSR" -> "环绕声效果"
        "HTR" -> "影院效果"
        "CTH" -> "音乐厅效果"
        "SBH" -> "地铁环境优化"
        "SID" -> "室内环境优化"
        "NDR" -> "夜间驾驶优化"
        "FCM" -> "专注模式"
        "SHE" -> "浴室环境效果"
        "LFF" -> "低保真效果"
        "AOS" -> "动漫音效优化"
        "MVP" -> "男声增强"
        "DFM" -> "女声增强"
        "RKP" -> "强力摇滚"
        "SUM" -> "阳光明媚的效果"
        "BRH" -> "破碎音效"
        else -> "自定义音效"
    }
}