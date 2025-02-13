package com.example.pacepocfigma


import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.uilibrary.CustomButton
import com.example.uilibrary.CustomTextInput
import com.example.uilibrary.ButtonState
import com.example.uilibrary.ButtonStyle
import com.example.uilibrary.TextInputState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UpdateContactForm()
        }
    }
}

@Composable
fun UpdateContactForm() {
    // Pre-filled user information
    var fullName by remember { mutableStateOf("John Doe") }
    var email by remember { mutableStateOf("johndoe@example.com") }
    var phoneNumber by remember { mutableStateOf("1234567890") }
    var homeAddress by remember { mutableStateOf("123 Main St, City") }

    // Store the initial values for detecting changes
    val initialFullName = "John Doe"
    val initialEmail = "johndoe@example.com"
    val initialPhoneNumber = "1234567890"
    val initialHomeAddress = "123 Main St, City"

    var isButtonEnabled by remember { mutableStateOf(false) }
    var buttonState by remember { mutableStateOf(ButtonState.Disabled) }

    LaunchedEffect(fullName, email, phoneNumber, homeAddress) {
        validateForm(fullName, email, phoneNumber, homeAddress) { isValid ->
            isButtonEnabled = isValid && (
                    fullName != initialFullName ||
                            email != initialEmail ||
                            phoneNumber != initialPhoneNumber ||
                            homeAddress != initialHomeAddress
                    )
            buttonState = if (isButtonEnabled) ButtonState.Default else ButtonState.Disabled
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Title
        Text(
            text = "Update Contact Information",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Full Name Input
        CustomTextInput(
            label = "Full Name",
            placeholder = "Enter your full name",
            state = if (fullName.isEmpty()) TextInputState.Enabled else TextInputState.Success,
            helperText = if (fullName.isEmpty()) "Required field" else "Valid name",
            value = fullName,
            onValueChange = { fullName = it }
        )

        // Email Input
        CustomTextInput(
            label = "Email Address",
            placeholder = "Enter your email",
            state = if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) TextInputState.Error else TextInputState.Success,
            helperText = if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Enter a valid email" else "Valid email",
            value = email,
            onValueChange = { email = it }
        )

        // Phone Number Input
        CustomTextInput(
            label = "Phone Number",
            placeholder = "Enter your phone number",
            state = if (phoneNumber.length < 10) TextInputState.Error else TextInputState.Success,
            helperText = if (phoneNumber.length < 10) "Enter a valid phone number" else "Valid number",
            value = phoneNumber,
            onValueChange = { phoneNumber = it }
        )

        // Home Address Input
        CustomTextInput(
            label = "Home Address",
            placeholder = "Enter your home address",
            state = if (homeAddress.isEmpty()) TextInputState.Enabled else TextInputState.Success,
            helperText = if (homeAddress.isEmpty()) "Required field" else "Valid address",
            value = homeAddress,
            onValueChange = { homeAddress = it }
        )

        // Save Changes Button
        CustomButton(
            text = "Save Changes",
            onClick = {
                isButtonEnabled = false
                buttonState = ButtonState.Disabled
            },
            enabled = isButtonEnabled,
            state = buttonState,
            style = ButtonStyle.Primary
        )
    }
}

private fun validateForm(
    fullName: String, email: String, phoneNumber: String, homeAddress: String,
    onValidationResult: (Boolean) -> Unit
) {
    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPhoneValid = phoneNumber.length >= 10
    val isFormValid = fullName.isNotEmpty() && isEmailValid && isPhoneValid && homeAddress.isNotEmpty()
    onValidationResult(isFormValid)
}
