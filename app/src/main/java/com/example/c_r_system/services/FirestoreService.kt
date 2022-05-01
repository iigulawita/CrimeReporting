package com.example.c_r_system.services

import android.annotation.SuppressLint
import android.util.Log
import com.example.c_r_system.models.AppUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



@SuppressLint("LogConditional")
class FirestoreService {

    private val db = Firebase.firestore

    fun addUser(callback: UserCallback, appUser: AppUser) {
        db.collection(USER_COLLECTION)
            .whereEqualTo("email", appUser.email).get()
            .addOnCompleteListener { task ->
                var docRef = ""
                val userExists: Int

                if (task.isSuccessful) {
                    for (document in task.result) {
                        if (document.exists()) {
                            Log.d(TAG, document.id + " => " + document.data)
                            docRef = document.id
                        }
                    }
                    userExists = if (docRef.isNotEmpty()) 1 else 0

                    if (userExists == 0) {
                        db.collection(USER_COLLECTION)
                            .document(appUser.uid.toString())
                            .set(appUser)
                            .addOnSuccessListener {
                                Log.d(TAG, "User document added!")
                                callback.onPostExecute(docRef)
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding User document", e)
                            }
                    } else
                        Log.d(TAG, "User already exists!")
                } else {
                    Log.d(TAG, "Error getting documents: ", task.exception)
                }
            }
    }

    fun readUser(callbackReadUser: UserCallback, uid: String) {
        db.collection(USER_COLLECTION).document(uid).get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    val document = task.result
                    val name = document.getString("name")
                    val email = document.getString("email")

                    val isProfileComplete = document.getBoolean("profileComplete")



                    val user = isProfileComplete?.let {
                        AppUser(
                            null,
                            name,
                            email!!,
                            it,

                        )
                    }
                    user?.let { callbackReadUser.onPostExecute(it) }
                    Log.d(TAG, "profileComplete = $isProfileComplete")
                } else {
                    Log.d(TAG, "Reading user failed: ${task.exception}")
                }

            }
    }

    fun updateUser(uid: String, appUser: AppUser) {
        db.collection(USER_COLLECTION)
            .document(uid)
            .update(
                "name", appUser.name,


                "profileComplete", appUser.profileComplete,
                "profileComplete", appUser.profileComplete,


            )
            .addOnSuccessListener { Log.d(TAG, "User DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating User document", e) }
    }









    companion object {
        const val TAG: String = "FirebaseService"
        const val USER_COLLECTION: String = "Users"

    }
}


