package com.example.android.training.compose.base.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
private fun Preview() {

    val (content,input) = remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        TextFieldTraining()

        SimpleTextField(content,input)

        SimpleTextField("test",input)
    }
}

@Composable
private fun SimpleTextField(
    searchContent: String,
    inputString: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(1f)
            .height(40.dp)
            .background(Color.DarkGray, RoundedCornerShape(50))
            .border(1.dp, Color.Gray, RoundedCornerShape(50))
            .padding(horizontal = 13.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        BasicTextField(
            value = searchContent,
            onValueChange = inputString,
            modifier = Modifier.weight(1f),
            textStyle = LocalTextStyle.current.copy(
                color = Color.White,
                fontSize = 14.sp
            ),
            decorationBox = { innerTextField ->
                if (searchContent.isEmpty()) {
                    Text(
                        text = "Search",
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )
                }
                innerTextField()
            }
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }

}

@Composable
private fun TextFieldTraining(
    modifier: Modifier = Modifier,
) {
    val (inputString, onEdit) = remember {
        mutableStateOf("")
    }

    val colors = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
    )

    TextFieldWithContentPadding(
        value = inputString,
        onValueChange = onEdit,
        contentPaddingValues = PaddingValues(horizontal = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(top = 30.dp)
            .height(28.dp)
            .clip(RoundedCornerShape(4.dp))
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(4.dp)
            ),
        singleLine = true,
        placeholder = {
            Text(
                text = "hint",
                fontSize = 12.sp,
                color = Color.Gray
            )
        },
        textStyle = TextStyle(
            color = Color.Red
        ),
        colors = colors
    )

    Button(onClick = {}, modifier = Modifier.padding(top = 200.dp)) {
        Text(text = "点击")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TextFieldWithContentPadding(
    value: String,
    onValueChange: (String) -> Unit,
    contentPaddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.shape,
    colors: TextFieldColors = TextFieldDefaults.colors()
) {
    BasicTextField(
        value = value,
        modifier = modifier
            .defaultMinSize(
                minWidth = TextFieldDefaults.MinWidth,
                minHeight = TextFieldDefaults.MinHeight
            ),
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        textStyle = textStyle,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = placeholder,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                prefix = prefix,
                suffix = suffix,
                supportingText = supportingText,
                shape = shape,
                singleLine = singleLine,
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
                contentPadding = contentPaddingValues,
                colors = colors
            )
        }
    )
}