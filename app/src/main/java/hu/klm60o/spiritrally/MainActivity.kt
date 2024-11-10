package hu.klm60o.spiritrally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import hu.klm60o.spiritrally.data.UserViewModel
import hu.klm60o.spiritrally.screens.MapScreenComposable
import hu.klm60o.spiritrally.screens.NewsScreenComposable
import hu.klm60o.spiritrally.screens.ProfileScreenComposable
import hu.klm60o.spiritrally.screens.RegisterScreenComposable
import hu.klm60o.spiritrally.screens.ResultScreenComposable
import hu.klm60o.spiritrally.ui.theme.SpiritRallyTheme
import hu.klm60o.spiritrally.useful.getUserDataFromFirestore
import hu.klm60o.spiritrally.useful.saveCurrentRaceDataToFirestore
import hu.klm60o.spiritrally.useful.showToast
import kotlinx.serialization.Serializable
import java.util.Calendar

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<UserViewModel>()

    private var textResult = mutableStateOf("")

    private val currentUser = Firebase.auth.currentUser

    fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Olvass be egy QR kódot")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setOrientationLocked(false)

        barCodeLauncher.launch(options)
    }

    private val barCodeLauncher = registerForActivityResult(ScanContract()) {
            result ->
        if (result.contents == null) {
            showToast(this, "Beolvasás megszakítve")
        }
        else {
            //Beolvassuk QR kódot, Int-té alakítjuk és megpróbáljuk beírni a Timestamp-be
            textResult.value = result.contents
            val textResultInteger = textResult.value.toString().toIntOrNull()
            if (viewModel.racePoints != null
                && textResultInteger != null
                && currentUser != null
                && viewModel.racePoints!!.size >= textResultInteger
                && viewModel.racePoints!![textResultInteger].timeStamp == null) {
                    var calendar = Calendar.getInstance()
                    viewModel.racePoints!![textResultInteger].timeStamp = Timestamp(calendar.time)
                    //viewModel.racePointsMutable[textResultInteger].timeStamp = Timestamp(calendar.time)
                    saveCurrentRaceDataToFirestore(currentUser, viewModel, this)
            }
        }
    }

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        isGranted ->
        if (isGranted) {
            showCamera()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startDestination: Any
        if(currentUser != null) {
            startDestination = NewsScreen
        } else {
            startDestination = LoginScreen
        }
        setContent {
            SpiritRallyTheme {
                //NavController létrehozása
                //val ViewModelSaver = Saver<UserViewModel, Any>(save = { it.Any }, restore = { UserViewModel() })
                //var test by rememberSaveable { mutableStateOf(UserViewModel()) }
                //var viewModel = UserViewModel()
                //val currentUser = Firebase.auth.currentUser
                if (currentUser != null) {
                    getUserDataFromFirestore(
                        currentUser = currentUser,
                        viewModel = viewModel,
                        context = LocalContext.current
                    )
                }
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = startDestination) {
                    composable<LoginScreen> {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            LoginScreenComposable(navController = navController, viewModel = viewModel)
                        }
                    }
                    composable<RegisterScreen> {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            RegisterScreenComposable(navController = navController, viewModel = viewModel)
                        }
                    }
                    composable<NewsScreen> {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            NewsScreenComposable(navController = navController, viewModel = viewModel)
                        }
                    }
                    composable<MapScreen> {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            MapScreenComposable(navController = navController, viewModel = viewModel)
                        }
                    }
                    composable<ResultScreen> {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            ResultScreenComposable(navController = navController, viewModel = viewModel)
                        }
                    }
                    composable<ProfileScreen> {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            ProfileScreenComposable(navController = navController, viewModel = viewModel)
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