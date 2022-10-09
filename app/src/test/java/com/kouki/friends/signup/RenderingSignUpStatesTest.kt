package com.kouki.friends.signup

import com.kouki.friends.InstantTaskExecutorExtension
import com.kouki.friends.app.TestDispatchers
import com.kouki.friends.domain.user.InMemoryUserCatalog
import com.kouki.friends.domain.user.User
import com.kouki.friends.domain.user.UserRepository
import com.kouki.friends.domain.validation.RegexCredentialValidator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class RenderingSignUpStatesTest {
    private val userRepository = UserRepository(InMemoryUserCatalog())
    private val viewModel = SignUpViewModel(
        RegexCredentialValidator(),
        userRepository,
        TestDispatchers()
    )
    private val tom = User("tomId", "tom@friends.com", "about Tom")

    @Test
    fun uiStatesAreDeliveredInParticularOrder() {
        val deliveredStates = mutableListOf<SignUpState>()
        viewModel.signUpState.observeForever { deliveredStates.add(it) }

        viewModel.createAccount(tom.email, "P@ssWord1", tom.about)

        assertEquals(
            listOf(SignUpState.Loading, SignUpState.SignedUp(tom)),
            deliveredStates
        )
    }
}