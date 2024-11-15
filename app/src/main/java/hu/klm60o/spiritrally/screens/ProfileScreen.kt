package hu.klm60o.spiritrally.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hu.klm60o.spiritrally.LoginScreen
import hu.klm60o.spiritrally.data.UserViewModel

@Composable
fun ProfileScreenComposable(navController: NavController, viewModel: UserViewModel) {
    val navController = navController
    //val viewModel = viewModel
    Scaffold(
        bottomBar = { MyBottomAppbarComposable(navController) }
    ) {
        innerPadding ->
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .padding(innerPadding)
        ) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(20.dp)
            ) {
                Icon(Icons.Filled.Person, contentDescription = "Profile Icon",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .size(75.dp))

                OutlinedTextField(value = Firebase.auth.currentUser?.email.toString(), onValueChange = {},
                    label = {
                        Text("Email cím")
                    },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                OutlinedTextField(value = Firebase.auth.currentUser?.displayName.toString(), onValueChange = {},
                    label = {
                        Text("Csapatnév")
                    },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                OutlinedButton(onClick = {
                    Firebase.auth.signOut()
                    navController.navigate(LoginScreen) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)) {
                    Text(text = "Kijelentkezés",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    hu.klm60o.spiritrally.ui.theme.ui.theme.SpiritRallyTheme {
        ProfileScreenComposable(
            navController = rememberNavController(),
            viewModel =  UserViewModel()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreviewDark() {
    hu.klm60o.spiritrally.ui.theme.SpiritRallyTheme(darkTheme = true) {
        ProfileScreenComposable(
            navController = rememberNavController(),
            viewModel =  UserViewModel()
        )
    }
}