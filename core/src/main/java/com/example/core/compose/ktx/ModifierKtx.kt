package com.example.core.compose.ktx

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role

fun Modifier.noEffectClick(
    interactionSource: MutableInteractionSource? = null,
    indication: Indication? = null,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    openAntiShake: Boolean = true,
    shakeTime:Long = 300L,
    onClick: () -> Unit,
) = this.composed {

    var lastClickTime by remember { mutableLongStateOf(0L) }

    clickable(
        interactionSource = interactionSource ?: remember { MutableInteractionSource() },
        indication = indication,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = {
            if(openAntiShake){
                if (lastClickTime != 0L && System.currentTimeMillis() - lastClickTime < shakeTime) {
                    return@clickable
                }
                lastClickTime = System.currentTimeMillis()
                onClick.invoke()
            }else{
                onClick.invoke()
            }
        },
    )
}