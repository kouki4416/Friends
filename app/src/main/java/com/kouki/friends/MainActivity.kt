package com.kouki.friends

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kouki.friends.signup.SignUp
import com.kouki.friends.timeline.Timeline
import com.kouki.friends.ui.theme.FriendsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            FriendsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    NavHost(navController = navController, startDestination = SIGN_UP){
                        composable(Companion.SIGN_UP){
                            SignUp(onSignedUp = { navController.navigate(TIMELINE) })
                        }
                        composable(TIMELINE){
                            Timeline()
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val SIGN_UP = "signUp"
        private const val TIMELINE = "timeline"
    }
}
