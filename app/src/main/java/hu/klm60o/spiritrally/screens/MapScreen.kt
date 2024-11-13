package hu.klm60o.spiritrally.screens

import android.content.ContentValues
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.utsman.osmandcompose.CameraProperty
import com.utsman.osmandcompose.CameraState
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.Polyline
import com.utsman.osmandcompose.rememberMarkerState
import hu.klm60o.spiritrally.data.UserViewModel
import hu.klm60o.spiritrally.useful.showToast
import io.ticofab.androidgpxparser.parser.GPXParser
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Polyline
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream


@Composable
fun MapScreenComposable(navController: NavController, viewModel: UserViewModel) {
    val navController = navController
    //val viewModel = viewModel
    /*val cameraState = rememberCameraState {
        geoPoint = GeoPoint(47.46274418232391, 19.041552309632305)
        zoom = 12.0 // optional, default is 5.0
    }*/


    val testMarkerState = rememberMarkerState(
        geoPoint = GeoPoint(47.498333, 19.040833)
    )

    val context = LocalContext.current
    val parser = GPXParser()
    var geoPoints: MutableList<GeoPoint> = ArrayList<GeoPoint>()
    try {
        val input: InputStream = context.assets.open("test.gpx")
        val parsedGpx = parser.parse(input)
        parsedGpx?.let {
            showToast(context, "BLA")
            parsedGpx.tracks.forEach {
                it.trackSegments.forEach {
                    it.trackPoints.forEach {
                        //Log.d(ContentValues.TAG, it.latitude.toString())
                        geoPoints.add(GeoPoint(it.latitude, it.longitude))
                    }
                }
            }
            //parsedGpx.tracks[0].trackSegments[0].trackPoints[0].latitude
            //testMarkerState.geoPoint = GeoPoint(parsedGpx.wayPoints[1].latitude, parsedGpx.wayPoints[1].longitude)
        } ?: {

        }
    } catch (e: IOException) {
        // do something with this exception
        e.printStackTrace()
    } catch (e: XmlPullParserException) {
        // do something with this exception
        e.printStackTrace()
    }


    val depokIcon: Drawable? by remember {
        mutableStateOf(context.getDrawable(org.osmdroid.library.R.drawable.marker_default))
    }

    val geoPoint = remember {
        listOf(viewModel.racePoints)
    }

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
            ) {
                Marker(
                    state = testMarkerState,
                    icon = depokIcon
                )
                com.utsman.osmandcompose.Polyline(
                    geoPoints = geoPoints,
                    color = Color.Blue
                )
            }
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