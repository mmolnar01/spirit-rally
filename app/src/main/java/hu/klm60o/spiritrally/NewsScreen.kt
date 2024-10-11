package hu.klm60o.spiritrally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.klm60o.spiritrally.ui.theme.SpiritRallyTheme


enum class SpiritRallyScreen() {
    Login,
    Register,
    News,
    Map,
    User
}


@Composable
fun SpiritRallyAppBar(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar {

    }
}


@Composable
fun SpiritRallyApp(name: String, modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SpiritRallyTheme {
        SpiritRallyApp("Android")
    }
}