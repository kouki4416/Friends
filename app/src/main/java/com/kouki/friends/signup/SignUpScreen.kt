package com.kouki.friends.signup

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kouki.friends.MainActivity
import com.kouki.friends.R
import com.kouki.friends.signup.state.SignUpScreenState

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel,
    navController: NavHostController
) {
    val coroutineScope = rememberCoroutineScope()
    val screenState by remember { mutableStateOf(SignUpScreenState(coroutineScope)) }
    val signUpState by signUpViewModel.signUpState.observeAsState()


    when (signUpState) {
        is SignUpState.BadEmail -> {
            screenState.isBadEmail = true
        }
        is SignUpState.BadPassword -> {
            screenState.isBadPassword = true
        }
        is SignUpState.DuplicateAccount -> {
            screenState.toggleInfoMessage(R.string.duplicateAccountError)
        }
        is SignUpState.BackendError -> {
           screenState.toggleInfoMessage(R.string.createAccountError)
        }
        is SignUpState.Offline -> {
            screenState.toggleInfoMessage(R.string.offlineError)
        }
        is SignUpState.Loading -> {
            BlockingLoading()
        }
        else -> {}
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ScreenTitle(R.string.createAnAccount)
            Spacer(modifier = Modifier.height(16.dp))
            EmailField(
                value = screenState.email,
                isError = screenState.showBadEmail,
                onValueChanged = { screenState.email = it }
            )
            PasswordField(
                value = screenState.password,
                isError = screenState.showBadPassword,
                onValueChange = { screenState.password = it },
            )
            Spacer(modifier = Modifier.height(16.dp))
            AboutField(
                value = screenState.about,
                onValueChange = { screenState.about = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    signUpViewModel.createAccount(screenState.email, screenState.password, screenState.about)
                    if (signUpState is SignUpState.SignedUp) {
                        navController.navigate(MainActivity.TIMELINE)
                    }
                    screenState.resetUiState()
                }
            ) {
                Text(text = stringResource(id = R.string.signUp))
            }
        }
        InfoMessage(
            stringResource = screenState.currentInfoMessage,
            isVisible = screenState.isInfoMessageShowing
        )
    }
}

@Composable
fun BlockingLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface.copy(alpha = 0.7f))
            .testTag(stringResource(id = R.string.loading)),
        contentAlignment = Alignment.Center
    ){

    }
}


@Composable
fun InfoMessage(@StringRes stringResource: Int, isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = {fullHeight ->  -fullHeight},
            animationSpec = tween(durationMillis = 150, easing = FastOutLinearInEasing)
        ),
        exit = fadeOut(
            targetAlpha = 0f,
            animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing)
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.error,
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(id = stringResource),
                    color = MaterialTheme.colors.onError
                )

            }
        }
    }
}


@Composable
private fun ScreenTitle(@StringRes resource: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(resource),
            style = TextStyle(fontSize = integerResource(id = R.integer.h4).sp)
        )
    }
}

@Composable
private fun EmailField(
    value: String,
    isError: Boolean,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(stringResource(id = R.string.email)),
        value = value,
        isError = isError,
        label = {
            val resource = if (isError) R.string.badEmailError else R.string.email
            Text(text = stringResource(id = resource))
        },
        onValueChange = onValueChanged
    )
}

@Composable
private fun PasswordField(
    value: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
) {
    var isVisible by remember { mutableStateOf(false) }
    val visualTransformation = if (isVisible) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(stringResource(id = R.string.password)),
        value = value,
        isError = isError,
        trailingIcon = {
            VisibilityToggle(isVisible) {
                isVisible = !isVisible
            }
        },
        visualTransformation = visualTransformation,
        label = {
            val resource = if (isError) R.string.badPasswordError else R.string.password
            Text(text = stringResource(id = resource))
        },
        onValueChange = onValueChange
    )
}

@Composable
private fun VisibilityToggle(
    isVisible: Boolean,
    onToggle: () -> Unit
) {
    IconButton(
        onClick = onToggle
    ) {
        val resource = if (isVisible) R.drawable.ic_visible else R.drawable.ic_invisible
        Icon(
            painter = painterResource(id = resource),
            contentDescription = stringResource(id = R.string.toggleVisivility)
        )
    }
}

@Composable
fun AboutField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        label = {
            Text(text = stringResource(id = R.string.about))
        },
        onValueChange = onValueChange
    )
}