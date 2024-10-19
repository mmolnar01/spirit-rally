package hu.klm60o.spiritrally.data

import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    var isLoggedIn: Boolean? = null
    //var currentRaceData: CurrentRaceData? = null
    var teamName: String? = null
    var distance: Int? = null
    var racePoints: List<RacePoint>? = null
}