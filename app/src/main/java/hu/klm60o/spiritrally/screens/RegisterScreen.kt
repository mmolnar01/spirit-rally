package hu.klm60o.spiritrally.screens

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.klm60o.spiritrally.LoginScreen
import hu.klm60o.spiritrally.R
import hu.klm60o.spiritrally.assets.ErrorIcon
import hu.klm60o.spiritrally.ui.theme.SpiritRallyTheme
import hu.klm60o.spiritrally.useful.validateEmail
import hu.klm60o.spiritrally.useful.validatePassword
import hu.klm60o.spiritrally.useful.validatePasswordRepeat

@Composable
fun RegisterScreenComposable(navController: NavController) {
    var validEmail = true
    var validPaswword = true
    var validPasswordRepeat = true
    val navController = navController
    Surface {
        //Változók a felhasználói input elátrolására
        val userEmail = remember {
            mutableStateOf("")
        }

        val userTeamName = remember {
            mutableStateOf("")
        }

        val userPassword = remember {
            mutableStateOf("")
        }

        val userPasswordRepeat = remember {
            mutableStateOf("")
        }

        //Oszlop a UI elemekhez
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .padding(20.dp)) {

            //Kép
            Image(painter = painterResource(id = R.drawable.spirit_rally),
                contentDescription = "image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp))

            //Üdvözlő üzenet
            Text(text = "Üdvözöl a Spirit Rally!", fontSize = 25.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp, 0.dp, 0.dp),
                textAlign = TextAlign.Center
            )

            //Email bemeneti mező
            OutlinedTextField(value = userEmail.value, onValueChange = {
                userEmail.value = it
                validEmail = validateEmail(userEmail.value)
            },
                supportingText = {
                    if(!validEmail) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Érvénytelen Email",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if(!validEmail) {
                        Icon(ErrorIcon,"error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "email")
                },
                label = {
                    Text(text = "Email")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp, 0.dp, 0.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            //Csapatnév bemeneti mező
            OutlinedTextField(value = userTeamName.value, onValueChange = {
                userTeamName.value = it
            },
                supportingText = {

                },
                leadingIcon = {
                    Icon(Icons.Default.Info, contentDescription = "teamname")
                },
                label = {
                    Text(text = "Csapatnév")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
            )

            //Jelszó bemeneti mező
            OutlinedTextField(value = userPassword.value, onValueChange = {
                userPassword.value = it
                validPaswword = validatePassword(userPassword.value)
            },
                supportingText = {
                    if(!validPaswword) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "A jelszó legyen min. 5 karakteres",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if(!validPaswword) {
                        Icon(ErrorIcon,"error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = "password")
                },
                label = {
                    Text(text = "Jelszó")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp, 0.dp, 0.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            //Jelszó újra bemeneti mező
            OutlinedTextField(value = userPasswordRepeat.value, onValueChange = {
                userPasswordRepeat.value = it
                validPasswordRepeat = validatePasswordRepeat(userPassword.value, userPasswordRepeat.value)
            },
                supportingText = {
                    if(!validPasswordRepeat) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "A jelszavak nem egyeznek",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if(!validPasswordRepeat) {
                        Icon(ErrorIcon,"error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = "passwordrepeat")
                },
                label = {
                    Text(text = "Jelszó újra")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp, 0.dp, 0.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            //Regisztrálás gomb
            ElevatedButton (onClick = { /*TODO*/ },
                elevation = ButtonDefaults.elevatedButtonElevation(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp, 0.dp, 0.dp)) {
                Text(text = "Regisztráció",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }

            //A bejelentkezéshez
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                Text(text = "Már regisztráltál?",
                    modifier = Modifier
                        .padding(5.dp))
                Text(text = "Bejelentkezés",
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable {
                            navController.navigate(LoginScreen)
                        },
                    fontWeight = FontWeight.Bold
                )
            }


        }
    }




}

fun validateEmailTest(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}


@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    SpiritRallyTheme {
        RegisterScreenComposable(
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreviewDark() {
    SpiritRallyTheme(darkTheme = true) {
        RegisterScreenComposable(
            navController = rememberNavController()
        )
    }
}