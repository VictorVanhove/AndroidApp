package com.example.dikkeploaten.Services

import android.util.Log
import com.example.dikkeploaten.Models.Album
import com.example.dikkeploaten.Models.User
import com.example.dikkeploaten.Models.UserAlbum
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class API {

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val storageRef =  FirebaseStorage.getInstance("gs://dikke-ploaten.appspot.com").reference

    val cache = Cache()

    companion object {
        private val TAG = "show"
        val shared = API()
    }

    init {}

    fun getUserCollection(callback: (ArrayList<Album>) -> Unit) {
        var albums = arrayListOf<Album>()
        var userAlbums = arrayListOf<UserAlbum>()

        val userPlates = db.collection("users").document(auth.currentUser!!.uid).collection("platen")
        userPlates.get().addOnSuccessListener { result ->
            for (document in result) {
                var userAlbum = document.toObject(UserAlbum::class.java)
                userAlbums.add(userAlbum)
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
            cache.user.plates = userAlbums
        }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun getUserWantlist(callback: (ArrayList<Album>) -> Unit) {
        var albums = arrayListOf<Album>()
        var userAlbums = arrayListOf<UserAlbum>()

        val userWantlist = db.collection("users").document(auth.currentUser!!.uid).collection("wantList")
        userWantlist.get().addOnSuccessListener { result ->
            for (document in result) {
                var userAlbum = document.toObject(UserAlbum::class.java)
                userAlbums.add(userAlbum)
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
            cache.user.wantList = userAlbums
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
                cache.albums = albums
                callback(albums)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }


    }

    fun addCollectionAlbum(albumId: String) {
        var userAlbum = UserAlbum(albumID = albumId)
        cache.user.plates.add(userAlbum)
        db.collection("users").document(auth.currentUser!!.uid).collection("platen")
            .add(userAlbum)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}") }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun addWantlistAlbum(albumId: String) {
        var userAlbum = UserAlbum(albumID = albumId)
        db.collection("users").document(auth.currentUser!!.uid).collection("wantList")
            .add(userAlbum)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                cache.user.wantList.add(userAlbum)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun deleteCollectionAlbum(albumId: String) {
        db.collection("users").document(auth.currentUser!!.uid).collection("platen")
            .whereEqualTo("albumID", albumId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    cache.user.plates.remove(document.toObject(UserAlbum::class.java))

                    db.collection("users").document(auth.currentUser!!.uid).collection("platen")
                        .document(document.id).delete()
                        .addOnSuccessListener {
                            Log.d(TAG, "DocumentSnapshot successfully deleted!")
                        }
                        .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun deleteWantlistAlbum(albumId: String) {
        db.collection("users").document(auth.currentUser!!.uid).collection("wantList")
            .whereEqualTo("albumID", albumId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    cache.user.wantList.remove(document.toObject(UserAlbum::class.java))


                    db.collection("users").document(auth.currentUser!!.uid).collection("wantList")
                        .document(document.id).delete()
                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                        .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun getUser(callback: (user: User) -> Unit) {
        db.collection("users").document(auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")

                    val user = document.toObject(User::class.java)
                    cache.user.username = user!!.username
                    cache.user.email = user!!.email
                    callback(user)
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun getProfileImage(callback: (String) -> Unit) {
        val profileRef = storageRef.child("images/profile/${auth.currentUser!!.uid}.jpg")

        profileRef.downloadUrl.addOnSuccessListener { uri ->
            var imageURL = uri.toString();
            callback(imageURL)
        }.addOnFailureListener {
            // Handle any errors
        }
    }

    fun getProfileCover(callback: (String) -> Unit) {
        val coverRef = storageRef.child("images/cover/${auth.currentUser!!.uid}.jpg")

        coverRef.downloadUrl.addOnSuccessListener { uri ->
            var imageURL = uri.toString();
            callback(imageURL)
        }.addOnFailureListener {
            // Handle any errors
        }
    }

    fun updateUsername(username: String) {
        db.collection("users").document(auth.currentUser!!.uid)
            .update("username", username)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }

            cache.user.username = username
    }

    fun updatePassword(newPassword: String) {
        auth.currentUser?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User password updated.")
                }
            }
    }

}