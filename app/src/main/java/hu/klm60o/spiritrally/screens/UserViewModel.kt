package hu.klm60o.spiritrally.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import hu.klm60o.spiritrally.screens.ui.theme.SpiritRallyTheme

class UserViewModel : ViewModel() {
    var isLoggedIn: Boolean = false;

    fun setIsLoggedIn(isLoggedIn: Boolean) {
        this.isLoggedIn = isLoggedIn;
    }
}