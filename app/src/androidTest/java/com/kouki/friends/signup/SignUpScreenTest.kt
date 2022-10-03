package com.kouki.friends.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.kouki.friends.MainActivity
import com.kouki.friends.domain.exceptions.BackendException
import com.kouki.friends.domain.exceptions.ConnectionUnavailableException
import com.kouki.friends.domain.user.InMemoryUserCatalog
import com.kouki.friends.domain.user.User
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

    private val signUpModule = module {
        factory<UserCatalog> { InMemoryUserCatalog() }
    }

    @Before
    fun setUp(){
        loadKoinModules(signUpModule)
    }

    @Test
    fun displayBadEmailError(){
        launchSignUpScreen(signUpTestRule){
            typeEmail("email")
            submit()
        } verify {
            badEmailErrorIsShown()
        }
    }

    @Test
    fun displayBadPasswordError(){
        launchSignUpScreen(signUpTestRule){
            typeEmail("jov@friends.com")
            typePassword("password")
            submit()
        } verify {
            badPasswordErrorIsShown()
        }
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
        val signUpUserPassword = "@lice1Pass#"
        replaceUserCatalogWith(InMemoryUserCatalog().apply {
            createUser(signUpUserEmail, signUpUserPassword, "")
        })

        launchSignUpScreen(signUpTestRule){
            typeEmail(signUpUserEmail)
            typePassword(signUpUserPassword)
            submit()
        }verify {
            duplicateAccountErrorIsShown()
        }
    }

    @Test
    fun displayBackendError() {
        // Arrange
        replaceUserCatalogWith(UnavailableUserCatalog())

        // Assert
        launchSignUpScreen(signUpTestRule){
            typeEmail("joe@friends.com")
            typePassword("Jo3@Paass")
            submit()
        }verify {
            backEndErrorIsShown()
        }
    }

    @Test
    fun displayOfflineError(){
        // Arrange
        replaceUserCatalogWith(OfflineUserCatalog())

        // Assert
        launchSignUpScreen(signUpTestRule){
            typeEmail("joe@friends.com")
            typePassword("Jo3@Paass")
            submit()
        }verify {
            offlineErrorIsShown()
        }
    }

    @After
    fun tearDown(){
        // Without this step, replaced module can be used for multiple tests
        replaceUserCatalogWith(InMemoryUserCatalog())
    }

    // With Koin swapping DI only for this test is possible
    private fun replaceUserCatalogWith(userCatalog: UserCatalog) {
        val replaceModule = module {
            factory { userCatalog }
        }
        loadKoinModules(replaceModule)
    }

    class OfflineUserCatalog: UserCatalog {
        override fun createUser(email: String, password: String, about: String): User {
            throw ConnectionUnavailableException()
        }

    }

    class UnavailableUserCatalog: UserCatalog {
        override fun createUser(email: String, password: String, about: String): User {
            throw BackendException()
        }
    }
}

