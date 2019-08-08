package com.example.dikkeploaten.Services

import android.util.Log
import com.example.dikkeploaten.Models.Album
import com.example.dikkeploaten.Models.UserAlbum
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class API {

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    companion object {
        private val TAG = "show"
        fun shared() = API()
    }

    fun getUserCollection(callback: (ArrayList<Album>) -> Unit) {
        var albums = arrayListOf<Album>()

        val userPlates = db.collection("users").document(auth.currentUser!!.uid).collection("platen")
        userPlates.get().addOnSuccessListener { result ->
            for (document in result) {
                var userAlbum = document.toObject(UserAlbum::class.java)
                db.collection("platen").document(userAlbum.albumID).get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            var album = document.toObject(Album::class.java)
                            album!!.id = document.id
                            albums.add(album)

                            callback(albums)
                        } else {
                            Log.d(TAG, "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }
            }
        }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

    }

    fun getUserWantlist(callback: (ArrayList<Album>) -> Unit) {
        var albums = arrayListOf<Album>()

        val userWantlist = db.collection("users").document(auth.currentUser!!.uid).collection("wantList")
        userWantlist.get().addOnSuccessListener { result ->
            for (document in result) {
                var userAlbum = document.toObject(UserAlbum::class.java)
                db.collection("platen").document(userAlbum.albumID).get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            var album = document.toObject(Album::class.java)
                            album!!.id = document.id
                            albums.add(album)

                            callback(albums)
                        } else {
                            Log.d(TAG, "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }
            }
        }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
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

    fun addCollectionAlbum(albumId: String) {
        var userAlbum = UserAlbum(albumID = albumId)
        db.collection("users").document(auth.currentUser!!.uid).collection("platen")
            .add(userAlbum)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
        }


}