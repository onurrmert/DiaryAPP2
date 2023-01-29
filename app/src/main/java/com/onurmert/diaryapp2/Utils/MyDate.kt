package com.onurmert.diaryapp2.Utils

import android.content.Context
import java.util.*

class MyDate {
    companion object{
         fun myDate(context: Context) : String{
            val date = Date().time
            val dateFormat = android.text.format.DateFormat.getDateFormat(context)
            return dateFormat.format(date)
        }
    }
}