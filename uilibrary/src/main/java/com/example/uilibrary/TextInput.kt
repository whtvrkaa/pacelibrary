package com.example.uilibrary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class TextInputState { Enabled, Disabled, Error, Success, ReadOnly }

@Composable
fun CustomTextInput(
    label: String,
    placeholder: String = "",
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    state: TextInputState = TextInputState.Enabled,
    helperText: String? = null,
    errorText: String? = null,
    successText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        imeAction = ImeAction.Done,
        keyboardType = KeyboardType.Text
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val isError = state == TextInputState.Error
    val isSuccess = state == TextInputState.Success
    val isDisabled = state == TextInputState.Disabled
    val isReadOnly = state == TextInputState.ReadOnly

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )

        OutlinedTextField(
            value = value,
            onValueChange = { if (!isDisabled && !isReadOnly) onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = !isDisabled,
            readOnly = isReadOnly,
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
            placeholder = { Text(text = placeholder, fontSize = 14.sp, color = Color.Gray) },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = if (isDisabled) Color.LightGray.copy(alpha = 0.3f) else Color.White,
                textColor = if (isDisabled) Color.Gray else Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = if (isError) Color.Red else if (isSuccess) Color.Green else Color.Blue,
                unfocusedBorderColor = Color.Gray
            )
        )

        if (isError && errorText != null) {
            Text(text = "⚠️ $errorText", fontSize = 12.sp, color = Color.Red, modifier = Modifier.padding(top = 4.dp))
        } else if (isSuccess && successText != null) {
            Text(text = "✅ $successText", fontSize = 12.sp, color = Color.Green, modifier = Modifier.padding(top = 4.dp))
        } else if (helperText != null) {
            Text(text = helperText, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
        }
    }
}
