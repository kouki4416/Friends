package com.kouki.friends.timeline

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.kouki.friends.R

@Composable
fun Timeline() {
    Text(
        text = stringResource(id = R.string.timeline),
        color = Color.Black
    )
}