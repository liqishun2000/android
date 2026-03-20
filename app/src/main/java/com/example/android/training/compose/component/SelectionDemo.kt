package com.example.android.training.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Preview(showBackground = true)
@Composable
private fun PreviewSelectionDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SwitchSection()
        HorizontalDivider()
        CheckboxSection()
        HorizontalDivider()
        RadioButtonSection()
        HorizontalDivider()
        SliderSection()
    }
}

@Composable
private fun SwitchSection() {
    SectionTitle("Switch (开关)")
    var checked1 by remember { mutableStateOf(true) }
    var checked2 by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("WiFi")
        Switch(checked = checked1, onCheckedChange = { checked1 = it })
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("蓝牙")
        Switch(checked = checked2, onCheckedChange = { checked2 = it })
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("禁用状态")
        Switch(checked = false, onCheckedChange = null, enabled = false)
    }
}

@Composable
private fun CheckboxSection() {
    SectionTitle("Checkbox (复选框)")
    var checkedAll by remember { mutableStateOf(false) }
    var checked1 by remember { mutableStateOf(true) }
    var checked2 by remember { mutableStateOf(false) }
    var checked3 by remember { mutableStateOf(false) }

    val parentState = when {
        checked1 && checked2 && checked3 -> ToggleableState.On
        !checked1 && !checked2 && !checked3 -> ToggleableState.Off
        else -> ToggleableState.Indeterminate
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        TriStateCheckbox(
            state = parentState,
            onClick = {
                val newState = parentState != ToggleableState.On
                checked1 = newState
                checked2 = newState
                checked3 = newState
            }
        )
        Text("全选")
    }

    Column(modifier = Modifier.padding(start = 24.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = checked1, onCheckedChange = { checked1 = it })
            Text("选项 A")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = checked2, onCheckedChange = { checked2 = it })
            Text("选项 B")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = checked3, onCheckedChange = { checked3 = it })
            Text("选项 C")
        }
    }
}

@Composable
private fun RadioButtonSection() {
    SectionTitle("RadioButton (单选按钮)")
    val options = listOf("选项一", "选项二", "选项三")
    var selectedOption by remember { mutableStateOf(options[0]) }

    Column(modifier = Modifier.selectableGroup()) {
        options.forEach { text ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { selectedOption = text },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = (text == selectedOption), onClick = null)
                Text(text = text, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
    Text("已选择: $selectedOption", style = MaterialTheme.typography.bodySmall)
}

@Composable
private fun SliderSection() {
    SectionTitle("Slider (滑块)")
    var sliderValue by remember { mutableFloatStateOf(0.5f) }
    Text("基本 Slider: ${(sliderValue * 100).roundToInt()}%")
    Slider(value = sliderValue, onValueChange = { sliderValue = it })

    var stepsValue by remember { mutableFloatStateOf(0f) }
    Text("带步进 Slider: ${stepsValue.roundToInt()}")
    Slider(
        value = stepsValue,
        onValueChange = { stepsValue = it },
        valueRange = 0f..100f,
        steps = 9
    )

    SectionTitle("RangeSlider (范围滑块)")
    var rangeValue by remember { mutableStateOf(20f..80f) }
    Text("范围: ${rangeValue.start.roundToInt()} - ${rangeValue.endInclusive.roundToInt()}")
    RangeSlider(
        value = rangeValue,
        onValueChange = { rangeValue = it },
        valueRange = 0f..100f
    )
}
