package com.onurmert.diaryapp2.ViewModel

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.onurmert.diaryapp2.View.ForgotPasswordFragmentDirections

class ForgotPasswordVM() : ViewModel() {

    private lateinit var auth: FirebaseAuth

    fun resetEmail(email : String, activity: Activity, view : View){

        auth = FirebaseAuth.getInstance()

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(activity){
                if (it.isSuccessful){
                    Toast.makeText(activity.applicationContext, "Check your gmail account!", Toast.LENGTH_SHORT).show()
                    direciton(view)
                }
            }
            .addOnFailureListener(activity){
                println("reset email error: " + it.localizedMessage)
                Toast.makeText(activity.applicationContext, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }

    private fun direciton(view: View){
        val direction1 = ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginEmailFragment()
        Navigation.findNavController(view).navigate(direction1)
    }
}