package hu.klm60o.spiritrally.data

import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    var teamName: String? = null
    var distance: Int? = null
    var racePoints: List<RacePoint>? = null
}