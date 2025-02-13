package com.example.uilibrary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class ButtonSize { Small, Large }
enum class ButtonType { Text, IconOnly, WithIcon }
enum class ButtonStyle { Primary, Secondary, Tertiary }
enum class ButtonState { Default, Hover, Pressed, Focus, Disabled }

private object CustomButtonDefaults {
    // Primary style colors
    val primaryBackgroundDefault = Color(0.8f, 0.0f, 0.0f, 1.0f) // e.g. from token "8961:12568"
    val primaryBackgroundHover   = Color(0.75f, 0.0f, 0.0f, 1.0f)
    val primaryBackgroundPressed = Color(0.6f, 0.0f, 0.0f, 1.0f)  // e.g. token "8961:12662"
    val primaryContentColor      = Color.White
    val primaryDisabledBackground = Color(0.9f, 0.9f, 0.9f, 1.0f)
    val primaryDisabledContent    = Color.Gray

    // Secondary style colors (for text buttons on a white background)
    val secondaryBackgroundDefault = Color.White
    val secondaryContentDefault    = Color(0.925f, 0.0f, 0.0f, 1.0f) // from token "VariableID:90f4a6a4..."
    val secondaryBackgroundHover   = Color(0.95f, 0.95f, 0.95f, 1.0f)
    val secondaryBackgroundPressed = Color(0.9f, 0.9f, 0.9f, 1.0f)
    val secondaryDisabledBackground = Color(0.8f, 0.8f, 0.8f, 1.0f)
    val secondaryDisabledContent    = Color.Gray

    // Tertiary style colors (often transparent or minimal background)
    val tertiaryContentColor = Color(0.051f, 0.318f, 0.333f, 1.0f) // from token "VariableID:9936bf027..."
    val tertiaryBackgroundDefault = Color.Transparent
    val tertiaryBackgroundHover   = Color(0.95f, 0.95f, 0.95f, 1.0f)
    val tertiaryBackgroundPressed = Color(0.9f, 0.9f, 0.9f, 1.0f)
    val tertiaryDisabledContent   = Color.Gray

    // Shape (all buttons use a very high corner radius per JSON: cornerRadius:50)
    val buttonShape: Shape = RoundedCornerShape(50.dp)

    // Elevation (example value for raised effect)
    val buttonElevation = 6.dp
}

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    state: ButtonState = ButtonState.Default,
    type: ButtonType = ButtonType.Text,
    style: ButtonStyle = ButtonStyle.Primary,
    size: ButtonSize = ButtonSize.Large,
    icon: Painter? = null
) {
    // Determine button dimensions based on size
    val buttonHeight = when (size) {
        ButtonSize.Large -> 48.dp
        ButtonSize.Small -> 32.dp
    }
    val horizontalPadding = when (size) {
        ButtonSize.Large -> 20.dp
        ButtonSize.Small -> 16.dp
    }
    val contentPadding = PaddingValues(horizontal = horizontalPadding)

    // Choose colors based on style and state
    val (backgroundColor, contentColor) = when (style) {
        ButtonStyle.Primary -> when (state) {
            ButtonState.Default -> Pair(CustomButtonDefaults.primaryBackgroundDefault, CustomButtonDefaults.primaryContentColor)
            ButtonState.Hover   -> Pair(CustomButtonDefaults.primaryBackgroundHover, CustomButtonDefaults.primaryContentColor)
            ButtonState.Pressed -> Pair(CustomButtonDefaults.primaryBackgroundPressed, CustomButtonDefaults.primaryContentColor)
            ButtonState.Focus   -> Pair(CustomButtonDefaults.primaryBackgroundDefault, CustomButtonDefaults.primaryContentColor)
            ButtonState.Disabled-> Pair(CustomButtonDefaults.primaryDisabledBackground, CustomButtonDefaults.primaryDisabledContent)
        }
        ButtonStyle.Secondary -> when (state) {
            ButtonState.Default -> Pair(CustomButtonDefaults.secondaryBackgroundDefault, CustomButtonDefaults.secondaryContentDefault)
            ButtonState.Hover   -> Pair(CustomButtonDefaults.secondaryBackgroundHover, CustomButtonDefaults.secondaryContentDefault)
            ButtonState.Pressed -> Pair(CustomButtonDefaults.secondaryBackgroundPressed, CustomButtonDefaults.secondaryContentDefault)
            ButtonState.Focus   -> Pair(CustomButtonDefaults.secondaryBackgroundDefault, CustomButtonDefaults.secondaryContentDefault)
            ButtonState.Disabled-> Pair(CustomButtonDefaults.secondaryDisabledBackground, CustomButtonDefaults.secondaryDisabledContent)
        }
        ButtonStyle.Tertiary -> when (state) {
            ButtonState.Default -> Pair(CustomButtonDefaults.tertiaryBackgroundDefault, CustomButtonDefaults.tertiaryContentColor)
            ButtonState.Hover   -> Pair(CustomButtonDefaults.tertiaryBackgroundHover, CustomButtonDefaults.tertiaryContentColor)
            ButtonState.Pressed -> Pair(CustomButtonDefaults.tertiaryBackgroundPressed, CustomButtonDefaults.tertiaryContentColor)
            ButtonState.Focus   -> Pair(CustomButtonDefaults.tertiaryBackgroundDefault, CustomButtonDefaults.tertiaryContentColor)
            ButtonState.Disabled-> Pair(CustomButtonDefaults.tertiaryBackgroundDefault, CustomButtonDefaults.tertiaryDisabledContent)
        }
    }

    // Use our shape from tokens
    val shape = CustomButtonDefaults.buttonShape

    // Build the button
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(buttonHeight)
            .fillMaxWidth(), // adjust as needed
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            disabledBackgroundColor = backgroundColor,
            disabledContentColor = contentColor
        ),
        shape = shape,
        contentPadding = contentPadding,
        elevation = ButtonDefaults.elevation(defaultElevation = CustomButtonDefaults.buttonElevation)
    ) {
        // Arrange the content horizontally with spacing (the JSON specifies itemSpacing: 4)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // If an icon is provided and the type is IconOnly or WithIcon, display it.
            if (type == ButtonType.IconOnly || type == ButtonType.WithIcon) {
                icon?.let {
                    Icon(
                        painter = it,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            // If the button type includes text, display the label.
            if (type == ButtonType.Text || type == ButtonType.WithIcon) {
                Text(
                    text = text,
                    style = TextStyle(
                        fontSize = 16.sp,  // from JSON: fontSize ~16.0
                        lineHeight = 24.sp // from JSON: lineHeight ~24.0
                        // You could add fontFamily and fontStyle here using your token values.
                    )
                )
            }
        }
    }
}