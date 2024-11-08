package hu.klm60o.spiritrally.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hu.klm60o.spiritrally.data.RacePoint
import hu.klm60o.spiritrally.data.UserViewModel
import hu.klm60o.spiritrally.useful.getUserDataFromFirestore

@Composable
fun ResultScreenComposable(navController: NavController, viewModel: UserViewModel) {
    val navController = navController
    val viewModel = viewModel
    val currentUser = Firebase.auth.currentUser

    /*if (currentUser != null) {
        getUserDataFromFirestore(
            currentUser = currentUser,
            viewModel = viewModel,
            context = LocalContext.current
        )
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
            //Text("Ez itt az eredmények képernyő")
            if (viewModel.racePoints != null) {
                RacePointListComposable(viewModel.racePoints!!)
            }
            //RacePointListComposable(viewModel.racePoints!!)
        }

    }
}

@Composable
fun RacePointListComposable(racePoints: List<RacePoint>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        items(racePoints, key = { racePoint -> racePoint.id!! }) { racePoint ->
            RacePointComposable(racePoint, racePoints.size)
        }
    }
}

@Composable
fun RacePointComposable(racePoint: RacePoint, size: Int) {
    Row(
        modifier = Modifier
            .padding(5.dp)
    ) {
        if (racePoint.id == 1) {
            Icon(
                Icons.Filled.Place, contentDescription = "Start Point Icon"
            )
            Text(
                "Rajt"
            )
        } else if (racePoint.id == size) {
            Icon(
                Icons.Filled.Place, contentDescription = "End Point Icon"
            )
            Text(
                "Cél"
            )
        } else {
            val racePointNumber = racePoint.id?.minus(1)
            Icon(
                Icons.Filled.Place, contentDescription = "Intermediate Point Icon"
            )
            Text(
                "$racePointNumber. ellenörző pont"
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ResultPreview() {
    hu.klm60o.spiritrally.ui.theme.ui.theme.SpiritRallyTheme {
        ResultScreenComposable(
            navController = rememberNavController(),
            viewModel =  UserViewModel()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResultPreviewDark() {
    hu.klm60o.spiritrally.ui.theme.SpiritRallyTheme(darkTheme = true) {
        ResultScreenComposable(
            navController = rememberNavController(),
            viewModel =  UserViewModel()
        )
    }
}