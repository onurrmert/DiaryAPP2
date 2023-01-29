package com.onurmert.diaryapp2.ViewModel

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.onurmert.diaryapp2.View.LoginEmailFragmentDirections

class LoginEmailVM : ViewModel() {

    private lateinit var auth : FirebaseAuth

    var isUserControl = MutableLiveData<Boolean>()

    var isLogin = MutableLiveData<Boolean>()

    fun userControl(){

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null){
            isUserControl.value = true
        }else{
            isUserControl.value = false
        }
    }

    fun login(email : String, password : String, activity: Activity, view : View){

        auth = FirebaseAuth.getInstance()

        login1(email, password, activity, view)
    }

    private fun login1(email : String, password  : String, activity: Activity, view : View){

        val direction = LoginEmailFragmentDirections.actionLoginEmailFragmentToCurrentFragment()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity){
                    task ->
                if (task.isSuccessful){
                    isLogin.value = false
                    Toast.makeText(activity.applicationContext, "Welcome!", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(view).navigate(direction)
                }else{
                    isLogin.value = true
                    println("user signIn error1")
                }
            }.addOnFailureListener(activity){
                    error->
                Toast.makeText(activity.applicationContext, "Error occurred while login", Toast.LENGTH_SHORT).show()
                println("user signIn error2: " + error.localizedMessage)
            }
    }
}