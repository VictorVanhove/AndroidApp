package com.example.dikkeploaten.Services

import android.graphics.Bitmap
import android.util.Log
import com.example.dikkeploaten.Models.Album
import com.example.dikkeploaten.Models.User
import com.example.dikkeploaten.Models.UserAlbum
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

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

    /**
     * Gets the user's collection with all the albums.
     */
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

    /**
     * Gets the user's wantlist with all the albums.
     */
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

    /**
     * Gets the whole list of albums out of the database.
     */
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

    /**
     * Adds album equal to parameter albumId to user's collection.
     */
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

    /**
     * Adds album equal to parameter albumId to user's wantlist.
     */
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

    /**
     * Deletes album equal to parameter albumId from user's collection.
     */
    fun deleteCollectionAlbum(albumId: String, callback: () -> Unit) {
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
                            callback()
                        }
                        .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    /**
     * Deletes album equal to parameter albumId from user's wantlist.
     */
    fun deleteWantlistAlbum(albumId: String, callback: () -> Unit) {
        db.collection("users").document(auth.currentUser!!.uid).collection("wantList")
            .whereEqualTo("albumID", albumId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    cache.user.wantList.remove(document.toObject(UserAlbum::class.java))


                    db.collection("users").document(auth.currentUser!!.uid).collection("wantList")
                        .document(document.id).delete()
                        .addOnSuccessListener {
                            Log.d(TAG, "DocumentSnapshot successfully deleted!")
                            callback()
                        }
                        .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    /**
     * Creates user with given credentials.
     */
    fun createUser(username: String, email: String, password: String, callback: () -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(object: OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful)
                    {
                        db.collection("users").document(auth.currentUser!!.uid)
                            .set(hashMapOf(
                                "username" to username,
                                "email" to email
                            ))
                            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

                        cache.user = User(username, email)
                        callback()
                    }
                }
            })
    }

    /**
     * Gets the current user from the database.
     */
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

    /**
     * Checks if user is logged in.
     */
    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    /**
     * Gets the profile image of the current user.
     */
    fun getProfileImage(callback: (String) -> Unit) {
        val profileRef = storageRef.child("images/profile/${auth.currentUser!!.uid}.jpg")

        profileRef.downloadUrl.addOnSuccessListener { uri ->
            var imageURL = uri.toString();
            callback(imageURL)
        }.addOnFailureListener {
            // Handle any errors
        }
    }

    /**
     * Gets the profile cover of the current user.
     */
    fun getProfileCover(callback: (String) -> Unit) {
        val coverRef = storageRef.child("images/cover/${auth.currentUser!!.uid}.jpg")

        coverRef.downloadUrl.addOnSuccessListener { uri ->
            var imageURL = uri.toString();
            callback(imageURL)
        }.addOnFailureListener {
            // Handle any errors
        }
    }

    /**
     * Updates the user's username.
     */
    fun updateUsername(username: String) {
        db.collection("users").document(auth.currentUser!!.uid)
            .update("username", username)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }

            cache.user.username = username
    }

    /**
     * Updates the user's password.
     */
    fun updatePassword(newPassword: String) {
        auth.currentUser?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User password updated.")
                }
            }
    }

    /**
     * Updates the user's profile image.
     */
    fun uploadProfileImage(bitmap: Bitmap) {
        val itemId = auth.currentUser!!.uid
        var imagePath = "images/profile/${itemId}.jpg"
        val imageRef = storageRef.child(imagePath)

        // Get the data from an ImageView as bytes
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener {
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }

    }

    /**
     * Updates the user's profile cover.
     */
    fun uploadCoverImage(bitmap: Bitmap) {
        val itemId = auth.currentUser!!.uid
        var imagePath = "images/cover/${itemId}.jpg"
        val imageRef = storageRef.child(imagePath)

        // Get the data from an ImageView as bytes
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener {
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }

    }


}