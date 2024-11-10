package hu.klm60o.spiritrally.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.klm60o.spiritrally.data.RacePoint
import hu.klm60o.spiritrally.data.UserViewModel
import java.util.Calendar

@Composable
fun ResultScreenComposable(navController: NavController, viewModel: UserViewModel) {
    val navController = navController
    //val viewModel = viewModel

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
        Column(verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            //Text("Ez itt az eredmények képernyő")
            if (viewModel.racePoints != null) {
                RacePointListComposable(viewModel.racePoints!!)
                Divider(
                    thickness = 2.dp,
                    modifier = Modifier
                        .padding(10.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text("Átlagsebesség: ")
                    }
                }
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
    var racePointCalendar = Calendar.getInstance()
    val seconds = racePoint.timeStamp?.seconds
    var timeText = "-"
    if (seconds != null) {
        racePointCalendar.timeInMillis = seconds * 1000

        //Lekérdezzük az időt órában és percben
        if (racePointCalendar.get(Calendar.MINUTE) < 10) {
            timeText = racePointCalendar.get(Calendar.HOUR_OF_DAY).toString() + ":" + "0" + racePointCalendar.get(Calendar.MINUTE).toString()
        } else {
            timeText = racePointCalendar.get(Calendar.HOUR_OF_DAY).toString() + ":" + racePointCalendar.get(Calendar.MINUTE).toString()
        }
    }
    Row(
        modifier = Modifier
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (racePoint.id == 1) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Icon(
                    Icons.Filled.Place, contentDescription = "Start Point Icon"
                )
            }
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Text(
                    "Rajt"
                )
            }

        } else if (racePoint.id == size) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Icon(
                    Icons.Filled.Place, contentDescription = "End Point Icon"
                )
            }
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Text(
                    "Cél"
                )
            }

        } else {
            val racePointNumber = racePoint.id?.minus(1)
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Icon(
                    Icons.Filled.Place, contentDescription = "Intermediate Point Icon"
                )
            }
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Text(
                    "$racePointNumber. ellenörző pont"
                )
            }

        }

        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            OutlinedTextField(
                value = timeText,
                onValueChange = {},
                enabled = false,
                modifier = Modifier
                    .width(100.dp),
                label = {
                    Text("Időpont")
                }
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