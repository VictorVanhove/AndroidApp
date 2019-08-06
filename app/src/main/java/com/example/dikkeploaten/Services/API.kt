package com.example.dikkeploaten.Services

import android.util.Log
import com.example.dikkeploaten.Models.Album
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class API {

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    companion object {
        private val TAG = "show"
        fun shared() = API()
    }

    fun getUserCollection(callback: (Array<Album>) -> Unit) {
        val userPlates = db.collection("users").document(auth.currentUser!!.uid).collection("platen")
        userPlates.get().addOnSuccessListener { result ->
            for (document in result) {
                Log.d("ALBUMSUCCESS", "${document.id} => ${document.data}")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("ALBUMFAIL", "Error getting documents: ", exception)
            }

    }

    fun getAlbumList(callback: (ArrayList<Album>) -> Unit) {
        var albums = arrayListOf<Album>()
        db.collection("platen")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var album = document.toObject(Album::class.java)
                    album.id = document.id
                    albums.add(album)
                }
                callback(albums)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }


    }



}