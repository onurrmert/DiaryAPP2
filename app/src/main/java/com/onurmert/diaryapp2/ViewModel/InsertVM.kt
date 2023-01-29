package com.onurmert.diaryapp2.ViewModel

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.onurmert.diaryapp2.Utils.Constantss
import com.onurmert.diaryapp2.Utils.MyDate
import com.onurmert.diaryapp2.View.InsertFragmentDirections
import java.util.UUID

class InsertVM : ViewModel() {

    private val diaryMap = hashMapOf<String, Any>()

    private lateinit var firestore: FirebaseFirestore

    private lateinit var auth: FirebaseAuth

    fun insert(title : String, diary : String, activity: Activity, view: View){

        firestore = FirebaseFirestore.getInstance()

        auth = FirebaseAuth.getInstance()

        diaryMap.put(Constantss.idKey, UUID.randomUUID().toString())
        diaryMap.put(Constantss.titleKey, title)
        diaryMap.put(Constantss.diaryKey, diary)
        diaryMap.put(Constantss.emailKey, auth.currentUser!!.email!!.toString())
        diaryMap.put(Constantss.historyKey, MyDate.myDate(activity.applicationContext))
        diaryMap.put(Constantss.timeStampKey, FieldValue.serverTimestamp())

        insert1(diaryMap, activity, view)
    }

    private fun insert1(diaryMap: HashMap<String, Any>, activity: Activity, view: View){

        val direction = InsertFragmentDirections.actionInsertFragment2ToCurrentFragment()

        firestore.collection(diaryMap.get(Constantss.emailKey).toString())
            .document(diaryMap.get(Constantss.idKey).toString())
            .set(diaryMap)
            .addOnCompleteListener(activity){
                    task->
                        if (task.isSuccessful){
                            Navigation.findNavController(view).navigate(direction)
                        }else{
                            println("insert error1")
                        }
            }
            .addOnFailureListener(activity){
                    exception->
                println("insert error2: " + exception.localizedMessage)
                Toast.makeText(activity.applicationContext, "Error occurred while saving", Toast.LENGTH_SHORT).show()
            }
    }
}