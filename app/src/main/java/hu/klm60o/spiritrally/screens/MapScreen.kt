package hu.klm60o.spiritrally.screens

import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.utsman.osmandcompose.rememberMarkerState
import hu.klm60o.spiritrally.R
import hu.klm60o.spiritrally.data.CurrentRaceData
import hu.klm60o.spiritrally.data.UserViewModel
import io.ticofab.androidgpxparser.parser.GPXParser
import org.osmdroid.util.GeoPoint
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream


@Composable
fun MapScreenComposable(navController: NavController, viewModel: UserViewModel) {

    val raceDataFromGpx = CurrentRaceData()
    raceDataFromGpx.distance = 320
    raceDataFromGpx.start_point = com.google.firebase.firestore.GeoPoint(47.35725982798458, 18.85715482108147)
    raceDataFromGpx.end_point = com.google.firebase.firestore.GeoPoint(46.138127802247205, 18.1175994573563)
    raceDataFromGpx.intermediate_points = emptyList()



    val context = LocalContext.current
    val parser = GPXParser()
    val geoPoints: MutableList<GeoPoint> = ArrayList<GeoPoint>()
    try {
        val input: InputStream = context.assets.open("tabaliget.gpx")
        val parsedGpx = parser.parse(input)
        parsedGpx?.let {
            parsedGpx.tracks.forEach { track ->
                track.trackSegments.forEach { trackSegment ->
                    trackSegment.trackPoints.forEach { trackPoint ->
                        geoPoints.add(GeoPoint(trackPoint.latitude, trackPoint.longitude))
                    }
                }
            }
        } ?: {

        }
    } catch (e: IOException) {
        // do something with this exception
        e.printStackTrace()
    } catch (e: XmlPullParserException) {
        // do something with this exception
        e.printStackTrace()
    }


    val redIcon: Drawable? by remember {
        mutableStateOf(context.getDrawable(R.drawable.map_marker_red))
    }

    val greenIcon: Drawable? by remember {
        mutableStateOf(context.getDrawable(R.drawable.map_marker_green))
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

            var cameraState by remember {
                mutableStateOf(
                    CameraState(
                        CameraProperty(
                            geoPoint = GeoPoint(47.3645399756769, 18.863085071980695),
                            zoom = 12.0
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
                viewModel.racePoints?.forEach { racePoint ->
                    if (racePoint.timeStamp != null) {
                        Marker(
                            state = rememberMarkerState(geoPoint = GeoPoint(racePoint.location!!.latitude, racePoint.location!!.longitude)),
                            icon = greenIcon
                        )
                    }
                    else {
                        Marker(
                            state = rememberMarkerState(geoPoint = GeoPoint(racePoint.location!!.latitude, racePoint.location!!.longitude)),
                            icon = redIcon
                        )
                    }
                }

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