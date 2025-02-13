package com.example.uilibrary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class ActionLinkState { Default, Pressed, Disabled }
enum class ActionLinkStyle { Default, Alternative, Inverted }

private object ActionLinkDefaults {
    val defaultTextColor = Color(0xFF127277)
    val defaultPressedColor = Color(0xFF0D5155)
    val defaultDisabledColor = Color(0xFF222222).copy(alpha = 0.23f)

    val alternativeTextColor = Color(0xFFEC0000)
    val alternativePressedColor = Color(0xFFCC0000)
    val alternativeDisabledColor = Color(0xFF222222).copy(alpha = 0.23f)

    val invertedTextColor = Color.White
    val invertedBackgroundDefault = Color(0xFF127277)
    val invertedBackgroundPressed = Color(0xFF0C4C4D)
    val invertedDisabledColor = Color.White.copy(alpha = 0.3f)

    val defaultFontSize = 14.sp
    val defaultFontWeight = FontWeight.Medium
    val defaultPadding = 8.dp
}

@Composable
fun ActionLink(
    text: String,
    modifier: Modifier = Modifier,
    state: ActionLinkState = ActionLinkState.Default,
    style: ActionLinkStyle = ActionLinkStyle.Default,
    onClick: () -> Unit
) {
    val (textColor) = getActionLinkColors(style, state)

    Text(
        text = text,
        color = textColor,
        fontSize = ActionLinkDefaults.defaultFontSize,
        fontWeight = ActionLinkDefaults.defaultFontWeight,
        modifier = modifier
            .clickable(
                enabled = state != ActionLinkState.Disabled,
                onClick = onClick
            )
            .padding(horizontal = ActionLinkDefaults.defaultPadding, vertical = 4.dp)
            .semantics {
                role = Role.Button
            }
    )
}

@Composable
fun getActionLinkColors(
    style: ActionLinkStyle,
    state: ActionLinkState
): Pair<Color, Color> {
    return when (style) {
        ActionLinkStyle.Default -> when (state) {
            ActionLinkState.Default -> Pair(ActionLinkDefaults.defaultTextColor, Color.Transparent)
            ActionLinkState.Pressed -> Pair(
                ActionLinkDefaults.defaultPressedColor,
                Color.Transparent
            )

            ActionLinkState.Disabled -> Pair(
                ActionLinkDefaults.defaultDisabledColor,
                Color.Transparent
            )
        }

        ActionLinkStyle.Alternative -> when (state) {
            ActionLinkState.Default -> Pair(
                ActionLinkDefaults.alternativeTextColor,
                Color.Transparent
            )

            ActionLinkState.Pressed -> Pair(
                ActionLinkDefaults.alternativePressedColor,
                Color.Transparent
            )

            ActionLinkState.Disabled -> Pair(
                ActionLinkDefaults.alternativeDisabledColor,
                Color.Transparent
            )
        }

        ActionLinkStyle.Inverted -> when (state) {
            ActionLinkState.Default -> Pair(
                ActionLinkDefaults.invertedTextColor,
                ActionLinkDefaults.invertedBackgroundDefault
            )

            ActionLinkState.Pressed -> Pair(
                ActionLinkDefaults.invertedTextColor,
                ActionLinkDefaults.invertedBackgroundPressed
            )

            ActionLinkState.Disabled -> Pair(
                ActionLinkDefaults.invertedDisabledColor,
                ActionLinkDefaults.invertedBackgroundDefault
            )
        }
    }
}
