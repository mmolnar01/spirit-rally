package hu.klm60o.spiritrally.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.utsman.osmandcompose.CameraProperty
import com.utsman.osmandcompose.CameraState
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.rememberCameraState
import hu.klm60o.spiritrally.data.UserViewModel
import hu.klm60o.spiritrally.screens.ui.theme.SpiritRallyTheme
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@Composable
fun MapScreenComposable(navController: NavController, viewModel: UserViewModel) {
    val navController = navController
    val viewModel = viewModel
    /*val cameraState = rememberCameraState {
        geoPoint = GeoPoint(47.46274418232391, 19.041552309632305)
        zoom = 12.0 // optional, default is 5.0
    }*/
    Scaffold(
        bottomBar = { MyBottomAppbarComposable(navController) }
    ) {
        innerPadding ->
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            // define camera state
            /*val cameraState = rememberCameraState {
                geoPoint = GeoPoint(47.46274418232391, 19.041552309632305)
                zoom = 12.0 // optional, default is 5.0
            }*/

            var cameraState by remember {
                mutableStateOf(
                    CameraState(
                        CameraProperty(
                            geoPoint = GeoPoint(47.498333, 19.040833),
                            zoom = 14.0
                        )
                    )
                )
            }

            LaunchedEffect(cameraState.zoom) {
                val zoom = cameraState.zoom
                val geoPoint = cameraState.geoPoint
                cameraState = CameraState(
                    CameraProperty(
                        geoPoint = geoPoint,
                        zoom = zoom
                    )
                )
            }

            // add node
            OpenStreetMap(
                modifier = Modifier.fillMaxSize(),
                cameraState = cameraState
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MapPreview() {
    hu.klm60o.spiritrally.ui.theme.ui.theme.SpiritRallyTheme {
        MapScreenComposable(
            navController = rememberNavController(),
            viewModel =  UserViewModel()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MapPreviewDark() {
    hu.klm60o.spiritrally.ui.theme.SpiritRallyTheme(darkTheme = true) {
        MapScreenComposable(
            navController = rememberNavController(),
            viewModel =  UserViewModel()
        )
    }
}