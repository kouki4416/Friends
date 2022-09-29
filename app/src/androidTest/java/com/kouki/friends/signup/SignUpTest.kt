package com.kouki.friends.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.kouki.friends.MainActivity
import org.junit.Rule
import org.junit.Test

class SignUpTest {

    @get:Rule
    val signUpTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun performSignUp() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("pank@friends.app")
            typePassword("Pass2!rd")
            submit()
        } verify {
            timelineScreenIsPresent()
        }
    }

}