package hu.klm60o.spiritrally.useful

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase
import hu.klm60o.spiritrally.data.UserViewModel

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

fun saveCurrentRaceDataToFirestore(viewModel: UserViewModel) {
    val currentUser = Firebase.auth.currentUser
    var documentReference: DocumentReference

    if (currentUser != null) {

    }
}

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