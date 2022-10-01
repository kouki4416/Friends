package com.kouki.friends.domain.user

import com.kouki.friends.domain.exceptions.BackendException
import com.kouki.friends.domain.exceptions.ConnectionUnavailableException
import com.kouki.friends.domain.exceptions.DuplicationAccountException
import com.kouki.friends.signup.SignUpState
import java.sql.Connection

class UserRepository(
    private val userCatalog: UserCatalog
) {

    fun signUp(
        email: String,
        password: String,
        about: String
    ): SignUpState {
        return try {
            val user = userCatalog.createUser(email, password, about)
            SignUpState.SignedUp(user)
        } catch (duplicateAccount: DuplicationAccountException) {
            SignUpState.DuplicateAccount
        } catch (backendException: BackendException){
            SignUpState.BackendError
        } catch (offlineException: ConnectionUnavailableException){
            SignUpState.Offline
        }
    }
}