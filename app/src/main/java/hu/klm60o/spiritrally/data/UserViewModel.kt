package hu.klm60o.spiritrally.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.osmdroid.util.GeoPoint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class UserViewModel : ViewModel() {
    var teamName: String? = null
    var distance: Int? = null
    var racePoints: List<RacePoint>? = null

    //var test by mutableStateOf("")

    /*var test = remember {
        mutableStateListOf<RacePoint>()
    }*/

    //var racePointsMutable = mutableStateListOf<RacePoint>()
}
