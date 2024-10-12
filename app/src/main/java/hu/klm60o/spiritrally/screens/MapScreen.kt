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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.klm60o.spiritrally.screens.ui.theme.SpiritRallyTheme

@Composable
fun MapScreenComposable() {
    Scaffold(
        bottomBar = { MyBottomAppbarComposable() }
    ) {
        innerPadding ->
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Text("Ez itt a térkép képernyő")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MapPreview() {
    hu.klm60o.spiritrally.ui.theme.ui.theme.SpiritRallyTheme {
        MapScreenComposable()
    }
}

@Preview(showBackground = true)
@Composable
fun MapPreviewDark() {
    hu.klm60o.spiritrally.ui.theme.SpiritRallyTheme(darkTheme = true) {
        MapScreenComposable()
    }
}