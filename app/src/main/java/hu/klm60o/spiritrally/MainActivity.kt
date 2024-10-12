package hu.klm60o.spiritrally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.klm60o.spiritrally.screens.MapScreenComposable
import hu.klm60o.spiritrally.screens.NewsScreenComposable
import hu.klm60o.spiritrally.screens.ProfileScreenComposable
import hu.klm60o.spiritrally.screens.RegisterScreenComposable
import hu.klm60o.spiritrally.screens.ResultScreenComposable
import hu.klm60o.spiritrally.ui.theme.SpiritRallyTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpiritRallyTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = LoginScreen) {
                    composable<LoginScreen> {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            LoginScreenComposable(navController = navController)
                        }
                    }
                    composable<RegisterScreen> {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            RegisterScreenComposable(navController = navController)
                        }
                    }
                    composable<NewsScreen> {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            NewsScreenComposable(navController = navController)
                        }
                    }
                    composable<MapScreen> {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            MapScreenComposable(navController = navController)
                        }
                    }
                    composable<ResultScreen> {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            ResultScreenComposable(navController = navController)
                        }
                    }
                    composable<ProfileScreen> {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            ProfileScreenComposable(navController = navController)
                        }
                    }
                }
                // A surface container using the 'background' color from the theme
                /*Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreenComposable()
                }*/
            }
        }
    }
}

@Serializable
object LoginScreen

@Serializable
object RegisterScreen

@Serializable
object NewsScreen

@Serializable
object MapScreen

@Serializable
object ResultScreen

@Serializable
object ProfileScreen