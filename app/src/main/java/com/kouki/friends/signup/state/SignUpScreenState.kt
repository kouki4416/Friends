package com.kouki.friends.signup.state

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpScreenState(
    private val coroutineScope: CoroutineScope
){
    var email by mutableStateOf("")
    var isBadEmail by mutableStateOf(false)
    var password by mutableStateOf("")
    var isBadPassword by mutableStateOf(false)
    var about by mutableStateOf("")
    var currentInfoMessage by mutableStateOf(0)
    var isInfoMessageShowing by mutableStateOf(false)
    var isLoading by mutableStateOf(false)

    private var lastSubmittedEmail by mutableStateOf("")
    private var lastSubmittedPassword by mutableStateOf("")

    val showBadEmail: Boolean
        get() = isBadEmail && (lastSubmittedEmail == email)
    val showBadPassword: Boolean
        get() = isBadPassword && (lastSubmittedPassword == password)

    fun showBadEmail(){
        isBadEmail = true
    }

    fun showBadPassword(){
        isBadPassword = true
    }

    fun toggleInfoMessage(@StringRes message: Int) = coroutineScope.launch {
        isLoading = false
        if(currentInfoMessage != message){
            currentInfoMessage = message
            if (!isInfoMessageShowing) {
                isInfoMessageShowing = true
                delay(1500)
                isInfoMessageShowing = false
            }
        }
    }

    fun resetUiState() {
        currentInfoMessage = 0
        lastSubmittedEmail = email
        lastSubmittedPassword = password
        isInfoMessageShowing = false
        isBadEmail = false
        isLoading = false
    }

    fun toggleLoading() {
        isLoading = true
    }
}