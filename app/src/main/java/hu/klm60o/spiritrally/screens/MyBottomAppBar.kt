package hu.klm60o.spiritrally.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.klm60o.spiritrally.MapScreen
import hu.klm60o.spiritrally.NewsScreen
import hu.klm60o.spiritrally.ProfileScreen
import hu.klm60o.spiritrally.ResultScreen
import hu.klm60o.spiritrally.assets.QrCode
import hu.klm60o.spiritrally.screens.ui.theme.SpiritRallyTheme

@Composable
fun MyBottomAppbarComposable(navController: NavController) {
    val navController = navController
    BottomAppBar(
        actions = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp)
            ) {
                IconButton(onClick = {
                    navController.navigate(NewsScreen)
                },) {
                    Icon(Icons.Filled.Home, contentDescription = "News screen")
                }
                Text(text = "Hírek",
                    modifier = Modifier
                        .padding(0.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp)
            ) {
                IconButton(onClick = {
                    navController.navigate(MapScreen)
                }) {
                    Icon(Icons.Filled.Info, contentDescription = "Info screen")
                }
                Text(text = "Térkép",
                    modifier = Modifier
                        .padding(0.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp)
            ) {
                IconButton(onClick = {
                    navController.navigate(ResultScreen)
                }) {
                    Icon(Icons.Filled.CheckCircle, contentDescription = "Results screen")
                }
                Text(text = "Eredmények",
                    modifier = Modifier
                        .padding(0.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp)
            ) {
                IconButton(onClick = {
                    navController.navigate(ProfileScreen)
                }) {
                    Icon(Icons.Filled.Person, contentDescription = "Profile screen")
                }
                Text(text = "Profil",
                    modifier = Modifier
                        .padding(0.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                containerColor = BottomAppBarDefaults.containerColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(10.dp, 15.dp, 15.dp,15.dp)
            ) {
                Icon(QrCode, contentDescription = "Read QR code")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MyBottomAppBarPreview() {
    hu.klm60o.spiritrally.ui.theme.ui.theme.SpiritRallyTheme {
        MyBottomAppbarComposable(
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyBottomAppBarPreviewDark() {
    hu.klm60o.spiritrally.ui.theme.SpiritRallyTheme(darkTheme = true) {
        MyBottomAppbarComposable(
            navController = rememberNavController()
        )
    }
}