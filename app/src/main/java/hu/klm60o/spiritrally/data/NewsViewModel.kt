package hu.klm60o.spiritrally.data

import android.content.ClipData.Item
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class NewsViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    val itemList: MutableState<List<News>> = mutableStateOf(emptyList())

    init {
        getNews()
    }

    private fun getNews() {
        firestore.collection("race_news")
            .get()
            .addOnSuccessListener { documents ->
                val items = mutableListOf<News>()
                for (document in documents) {
                    val data = document.data

                    val item = News(
                        data["title"] as String,
                        data["content"] as String,
                        data["important"] as Boolean,
                        data["id"] as Long
                    )
                    items.add(item)
                }
                itemList.value = items
            }
            .addOnFailureListener { exception ->
                    Log.e("Firestore", "Dokumentumok lekérdezése sikertelen: ", exception)
            }
    }
}