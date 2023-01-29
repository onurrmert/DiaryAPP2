package com.onurmert.diaryapp2.ViewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.onurmert.diaryapp2.Model.DiaryModel
import com.onurmert.diaryapp2.Utils.Constantss
import com.onurmert.diaryapp2.View.CurrentFragmentDirections

class CurrentVM : ViewModel() {

    val diaryList = MutableLiveData<ArrayList<DiaryModel>>()

    private lateinit var firestore: FirebaseFirestore

    private lateinit var auth: FirebaseAuth

    fun getDiary(){

        firestore = FirebaseFirestore.getInstance()

        auth = FirebaseAuth.getInstance()

        getData(auth.currentUser!!.email!!)
    }

    private fun getData(email : String){

        val liste = ArrayList<DiaryModel>()

        liste.clear()

        firestore.collection(email)
            .orderBy("timeStamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                    result->
                for (documents in result){
                    val diaryModel = DiaryModel(
                        documents.get(Constantss.idKey) as String,
                        documents.get(Constantss.titleKey) as String,
                        documents.get(Constantss.diaryKey) as String,
                        documents.get(Constantss.emailKey) as String,
                        documents.get(Constantss.historyKey) as String,
                        documents.get(Constantss.timeStampKey) as Any
                    )
                    liste.add(diaryModel)
                }
                diaryList.value = liste
            }
            .addOnFailureListener{
                error->
                println("error current page" + error.localizedMessage)
            }
    }

    fun singOut(view : View){
        auth.signOut()
        val direction = CurrentFragmentDirections.actionCurrentFragmentToLoginEmailFragment()
        Navigation.findNavController(view).navigate(direction)
    }
}