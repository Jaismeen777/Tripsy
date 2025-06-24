package com.igdtuw.travelbuddy.utils
import com.google.firebase.firestore.FirebaseFirestore
fun fetchPlacesByType(
    type: String,
    onSuccess: (List<String>) -> Unit,
    onFailure: (Exception) -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    db.collection("destinations")
        .document(type)
        .collection("places")
        .get()
        .addOnSuccessListener { result ->
            val names = result.documents.mapNotNull { it.id }
            onSuccess(names)
        }
        .addOnFailureListener { exception ->
            onFailure(exception)
        }
}
