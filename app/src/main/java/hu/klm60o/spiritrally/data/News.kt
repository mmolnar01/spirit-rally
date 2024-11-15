package hu.klm60o.spiritrally.data

data class News(
    val title: String,
    val content: String,
    val important: Boolean,
    val id: Long
)
