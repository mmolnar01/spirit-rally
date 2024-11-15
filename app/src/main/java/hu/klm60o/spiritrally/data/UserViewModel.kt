package hu.klm60o.spiritrally.data

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.osmdroid.util.GeoPoint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserViewModel : ViewModel() {
    var teamName: String? = null
    var distance: Int? = null
    var racePoints: List<RacePoint>? = null
    var achievedRacePoints: Int = 0
    //var averageSpeed: Double = 0.0

    //@get:Exclude
    //lateinit var navController: NavController
    //var racePoints = mutableStateListOf<RacePoint>()

    //var test by mutableStateOf("")

    /*var test = remember {
        mutableStateListOf<RacePoint>()
    }*/

    //var racePointsMutable = mutableStateListOf<RacePoint>()
    fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= 5
    }

    fun validatePasswordRepeat(password: String, passwordRepeat: String): Boolean {
        return password.equals(passwordRepeat)
    }

    fun registerUser(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }
    }

    fun loginUSer(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }
    }

    fun showToast(context: Context, msg: String) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun getUserDataFromFirestore(currentUser: FirebaseUser, context: Context) {
        //Lekérdezzük a bejlentkezett felhasználó eredményeit
        val userDocumentReference = Firebase.firestore.collection("race_results").document(currentUser.uid)
            .collection("my_race_results").document("my_race_results_current")

        userDocumentReference.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result

                //Ha létezik a dokumentum, akkor beállítjuk a viewModel mezőit és megyünk a NewsScreen-re
                if (document.exists()) {
                    val userRaceData = document.toObject(UserViewModel::class.java)
                    distance = userRaceData?.distance
                    racePoints = userRaceData?.racePoints
                    //viewModel.racePointsMutable.addAll(userRaceData?.racePoints!!)
                    teamName = userRaceData?.teamName
                    achievedRacePoints = userRaceData!!.achievedRacePoints
                    showToast(context, "Adatok betöltve!")

                    //Ha nem létezik a dokumentum, akkor letöltjük a verseny részleteit, és létrehozzuk a dokumentumot
                } else {
                    var currentRaceData: CurrentRaceData?
                    Firebase.firestore.collection("race_data").document("current_race_data_test").get()
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful) {
                                val raceDocument = task.result
                                //Ha sikerül és létezik, betöltjük a viewModel-be és megyünk a NewsScreen-re
                                if(raceDocument.exists()) {
                                    currentRaceData = raceDocument.toObject(CurrentRaceData::class.java)

                                    distance = currentRaceData?.distance
                                    val racePoints = mutableListOf<RacePoint>()

                                    racePoints.add(RacePoint(1, currentRaceData?.start_point, null))

                                    var i = 2
                                    for (geoPoint in currentRaceData?.intermediate_points!!) {
                                        racePoints.add(RacePoint(i, geoPoint, null))
                                        i++
                                    }

                                    racePoints.add(RacePoint(i, currentRaceData?.end_point, null))

                                    this.racePoints = racePoints

                                    teamName = currentUser.displayName

                                    userDocumentReference.set(this).addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Log.d(ContentValues.TAG, "Created user document: Success")
                                            showToast(context, "A dokumentum sikeresen létrehozva")
                                        } else {
                                            Log.d(ContentValues.TAG, "Created user document: Failure")
                                            showToast(context, "A dokumentum létrehozása sikertelen")
                                        }
                                    }
                                }
                            }
                        }
                }
            }
        }
    }

    fun saveCurrentRaceDataToFirestore(currentUser: FirebaseUser, context: Context) {
        if (currentUser != null) {
            val userDocumentReference = Firebase.firestore.collection("race_results").document(currentUser.uid)
                .collection("my_race_results").document("my_race_results_current")

            userDocumentReference.set(this).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "Created user document: Success")
                    showToast(context, "A dokumentum sikeresen létrehozva")
                }
                else {
                    Log.d(ContentValues.TAG, "Created user document: Failure")
                    showToast(context, "A dokumentum létrehozása sikertelen")
                }
            }
        }
    }

    /*fun saveRaceDataToFirestore(currentUser: FirebaseUser, context: Context, data: CurrentRaceData) {
        val userDocumentReference = Firebase.firestore.collection("race_data").document("current_race_data_test")


        userDocumentReference.set(data).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(ContentValues.TAG, "Created user document: Success")
                showToast(context, "A dokumentum sikeresen létrehozva")
            }
            else {
                Log.d(ContentValues.TAG, "Created user document: Failure")
                showToast(context, "A dokumentum létrehozása sikertelen")
            }
        }
    }*/

    fun setDisplayName(name: String) {
        val user = Firebase.auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                }
            }
    }
}
