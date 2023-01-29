package com.onurmert.diaryapp2.ViewModel

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.onurmert.diaryapp2.View.SingupFragmentDirections

class SingupVM : ViewModel(){

    private lateinit var auth: FirebaseAuth

    fun singUp(email : String, password  : String, activity: Activity, view : View){

        auth = FirebaseAuth.getInstance()

        val direction = SingupFragmentDirections.actionSingupFragmentToCurrentFragment()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity){
                    task ->
                if (task.isSuccessful){
                    Toast.makeText(activity.applicationContext, "User registered", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(view).navigate(direction)
                }else{
                    println("user save error")
                }
            }
            .addOnFailureListener(activity){
                    error->
                println("user save error: " + error.localizedMessage)
                Toast.makeText(activity.applicationContext, "Error occurred while saving", Toast.LENGTH_SHORT).show()
            }
    }
}