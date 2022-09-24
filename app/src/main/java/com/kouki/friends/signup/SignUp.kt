package com.kouki.friends.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kouki.friends.R
import java.io.StringReader

@Composable
@Preview
fun SignUp() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Create an account")
        OutlinedTextField(
            value = "",
            label = {
                Text(text = stringResource(id = R.string.email))
            },
            onValueChange = {}
        )
        OutlinedTextField(
            value = "",
            label = {
                Text(text = stringResource(id = R.string.password))
            },
            onValueChange = {}
        )
        Button(onClick = { }) {
            Text(text = stringResource(id = R.string.signUp))
        }
    }
}