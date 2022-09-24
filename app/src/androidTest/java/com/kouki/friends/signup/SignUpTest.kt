package com.kouki.friends.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kouki.friends.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class SignUpTest {

    @get:Rule
    val signUpTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun performSignUp() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("pank@friends.app")
            typePassword("password")
            submit()
        } verify {
            timelineScreenIsPresent()
        }
    }

}