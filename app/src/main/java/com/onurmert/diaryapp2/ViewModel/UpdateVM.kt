package com.onurmert.diaryapp2.ViewModel

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.onurmert.diaryapp2.Model.DiaryModel
import com.onurmert.diaryapp2.Utils.Constantss
import com.onurmert.diaryapp2.Utils.MyDate
import com.onurmert.diaryapp2.View.UpdateFragmentDirections
import kotlin.collections.HashMap

class UpdateVM : ViewModel() {

    val diaryModel = MutableLiveData<DiaryModel>()

    private lateinit var firestore: FirebaseFirestore

    private lateinit var auth: FirebaseAuth

    fun getOneDiary(id : String, activity : Activity){

        firestore = FirebaseFirestore.getInstance()

        auth = FirebaseAuth.getInstance()

        firestore.collection(auth.currentUser!!.email!!)
            .whereEqualTo(Constantss.idKey, id)
            .get()
            .addOnSuccessListener {
                    result->
                for (documents in result){
                    diaryModel.value = DiaryModel(
                        documents.get(Constantss.idKey) as String,
                        documents.get(Constantss.titleKey) as String,
                        documents.get(Constantss.diaryKey) as String,
                        documents.get(Constantss.emailKey) as String,
                        documents.get(Constantss.historyKey) as String,
                        documents.get(Constantss.timeStampKey) as Any
                    )
                }
            }
            .addOnFailureListener {
                println("update getOneDiary error :" + it.localizedMessage)
                Toast.makeText(activity.applicationContext, "Error occurred while diary reading", Toast.LENGTH_SHORT).show()
            }
    }

    fun update(id : String, title : String, diary : String, activity: Activity, view : View){

        val diaryMap = HashMap<String, Any>()

        diaryMap.put(Constantss.idKey, id)
        diaryMap.put(Constantss.titleKey, title)
        diaryMap.put(Constantss.diaryKey, diary)
        diaryMap.put(Constantss.emailKey, auth.currentUser!!.email!!.toString())
        diaryMap.put(Constantss.historyKey, MyDate.myDate(activity.applicationContext))
        diaryMap.put(Constantss.timeStampKey, FieldValue.serverTimestamp())

        firestore.collection(auth.currentUser!!.email!!)
            .document(id)
            .update(diaryMap)
            .addOnCompleteListener(activity){
                if (it.isSuccessful){
                    val direction = UpdateFragmentDirections.actionUpdateFragmentToCurrentFragment()
                    Navigation.findNavController(view).navigate(direction)
                }
            }
            .addOnFailureListener {
                println("update error: " + it.localizedMessage)
                Toast.makeText(activity.applicationContext, "Error occurred while updating", Toast.LENGTH_SHORT).show()
            }
    }
}