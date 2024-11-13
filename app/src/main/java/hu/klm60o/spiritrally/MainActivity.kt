package hu.klm60o.spiritrally

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import hu.klm60o.spiritrally.data.UserViewModel
import hu.klm60o.spiritrally.screens.LoginScreenComposable
import hu.klm60o.spiritrally.screens.MapScreenComposable
import hu.klm60o.spiritrally.screens.NewsScreenComposable
import hu.klm60o.spiritrally.screens.ProfileScreenComposable
import hu.klm60o.spiritrally.screens.RegisterScreenComposable
import hu.klm60o.spiritrally.screens.ResultScreenComposable
import hu.klm60o.spiritrally.ui.theme.SpiritRallyTheme
import hu.klm60o.spiritrally.useful.showToast
import kotlinx.serialization.Serializable
import java.util.Calendar

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<UserViewModel>()

    private var textResult = mutableStateOf("")

    private val currentUser = Firebase.auth.currentUser

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private fun Location.checkIsInBound(radius: Double, center: Location): Boolean = this.distanceTo(center)<radius

    fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Olvass be egy QR kódot")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setOrientationLocked(false)

        barCodeLauncher.launch(options)
    }

    @SuppressLint("MissingPermission")
    private val barCodeLauncher = registerForActivityResult(ScanContract()) {
            result ->
        if (result.contents == null) {
            showToast(this, "Beolvasás megszakítve")
        }
        else {
            //Beolvassuk QR kódot, Int-té alakítjuk és megpróbáljuk beírni a Timestamp-be
            textResult.value = result.contents
            val textResultInteger = textResult.value.toString().toIntOrNull()

            if (viewModel.racePoints != null && textResultInteger != null && currentUser != null && viewModel.racePoints!!.size >= textResultInteger && viewModel.racePoints!![textResultInteger].timeStamp == null) {
                val currentLocation = fusedLocationClient.lastLocation
                currentLocation.addOnSuccessListener {
                    val racePointLocation = Location("Test")
                    racePointLocation.latitude = viewModel.racePoints!![textResultInteger].location?.latitude!!
                    racePointLocation.longitude = viewModel.racePoints!![textResultInteger].location?.longitude!!
                    if (it?.checkIsInBound(100.0, racePointLocation) == true) {
                        showToast(this, "Jó helyen vagy :)")
                        var calendar = Calendar.getInstance()
                        viewModel.racePoints!![textResultInteger].timeStamp = Timestamp(calendar.time)
                        viewModel.saveCurrentRaceDataToFirestore(currentUser, this)
                    }
                    else {
                        showToast(this, "Sajnos rossz helyen vagy :(")
                    }
                }
            }
        }
    }

    /*val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        isGranted ->
        if (isGranted) {
            showCamera()
        }
    }*/

    val requestPermissionLauncher2 = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        permissions ->
        if (!permissions.containsValue(false)) {
            showCamera()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

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
                    viewModel.getUserDataFromFirestore(
                        currentUser = currentUser,
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