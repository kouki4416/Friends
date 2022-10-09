package com.kouki.friends.app

import com.kouki.friends.domain.user.InMemoryUserCatalog
import com.kouki.friends.domain.user.UserCatalog
import com.kouki.friends.domain.user.UserRepository
import com.kouki.friends.domain.validation.RegexCredentialValidator
import com.kouki.friends.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module{
    single<UserCatalog> { InMemoryUserCatalog() }
    factory { RegexCredentialValidator() }
    factory { UserRepository(userCatalog = get()) }

    viewModel{
        SignUpViewModel(
            credentialsValidator = get(),
            userRepository = get(),
            dispatchers = TestDispatchers()
        )
    }
}