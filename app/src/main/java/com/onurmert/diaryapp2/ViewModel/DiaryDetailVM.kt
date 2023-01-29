package com.onurmert.diaryapp2.ViewModel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.onurmert.diaryapp2.Model.DiaryModel
import com.onurmert.diaryapp2.Utils.Constantss

class DiaryDetailVM : ViewModel() {

    private lateinit var firestore: FirebaseFirestore

    private lateinit var auth: FirebaseAuth

    val diaryModel = MutableLiveData<DiaryModel>()

    fun getOneDiary(id : String, activity: Activity){

        firestore = FirebaseFirestore.getInstance()

        auth = FirebaseAuth.getInstance()

        getData(id, activity)
    }

    private fun getData(id : String, activity : Activity){

        firestore.collection(auth.currentUser!!.email!!)
            .whereEqualTo(Constantss.idKey, id)
            .get()
            .addOnCompleteListener(activity){
                    result->
                for (documents in result.result){
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
            .addOnFailureListener(activity){
                    exception->
                println("getOneDiary error: " + exception.localizedMessage)
            }
    }
}