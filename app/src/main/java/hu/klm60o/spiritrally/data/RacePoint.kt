package hu.klm60o.spiritrally.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class RacePoint(
    var id: Int? = null,
    var location: GeoPoint? = null,
    var timeStamp: Timestamp? = null
)
