package hu.klm60o.spiritrally.data

import com.google.firebase.firestore.GeoPoint

data class CurrentRaceData(
    var start_point: GeoPoint? = null,
    var end_point: GeoPoint? = null,
    var intermediate_points: List<GeoPoint>? = null,
    var distance: Int? = null
)
