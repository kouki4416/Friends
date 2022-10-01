package com.kouki.friends.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.kouki.friends.MainActivity
import com.kouki.friends.domain.user.InMemoryUserCatalog
import com.kouki.friends.domain.user.UserCatalog
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class SignUpScreenTest {

    @get:Rule
    val signUpTestRule = createAndroidComposeRule<MainActivity>()

    private val userCatalog = InMemoryUserCatalog()

    private val signUpModule = module {
        factory<UserCatalog> { userCatalog }
    }

    @Before
    fun setUp(){
        loadKoinModules(signUpModule)
    }

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

    @Test
    fun displayDuplicateAccountError(){
        val signUpUserEmail = "alice@friends.com"
        val signUpUserPassword = "@lice1Pass"
        createUserWith(signUpUserEmail, signUpUserPassword)
        launchSignUpScreen(signUpTestRule){
            typeEmail(signUpUserEmail)
            typePassword(signUpUserPassword)
            submit()
        }verify {
            duplicateAccountErrorIsShown()
        }
    }

    @After
    fun tearDown(){
        val resetModule = module {
            single{ InMemoryUserCatalog() }
        }
        loadKoinModules(resetModule)
    }

    private fun createUserWith(signUpUserEmail: String, signUpUserPassword: String) {
        userCatalog.createUser(signUpUserEmail, signUpUserPassword, "")
    }

}