package com.onurmert.diaryapp2.ViewModel

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MenuVM : ViewModel() {

    private lateinit var auth: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    fun delete(id : String, activity: Activity){

        auth = FirebaseAuth.getInstance()

        firestore = FirebaseFirestore.getInstance()

        firestore.collection(auth.currentUser!!.email!!)
            .document(id)
            .delete()
            .addOnCompleteListener(activity){
                    task->
                if (task.isSuccessful){
                    Toast.makeText(activity.applicationContext, "Deleted", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener(activity){
                println("delete error: " + it.localizedMessage)
                Toast.makeText(activity.applicationContext, "Error occurred while deleting", Toast.LENGTH_SHORT).show()
            }
    }
}