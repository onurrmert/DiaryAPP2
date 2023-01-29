package com.onurmert.diaryapp2.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.onurmert.diaryapp2.Model.DiaryModel
import com.onurmert.diaryapp2.R
import com.onurmert.diaryapp2.View.CurrentFragmentDirections
import com.onurmert.diaryapp2.databinding.RecyclerRowsBinding

class DiaryAdapter (val diaryList : ArrayList<DiaryModel>) : RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>(){

    class DiaryViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val binding = RecyclerRowsBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {

        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recycler_rows, parent, false)

        return DiaryViewHolder(layout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {

        holder.binding.titleText.setText("${position + 1 }) " + diaryList.get(position).title)

        holder.binding.historyText.setText(diaryList.get(position).history.toString())

        val directionDetail = CurrentFragmentDirections
            .actionCurrentFragmentToDiaryDetailFragment(diaryList.elementAt(position).id)

        holder.binding.recyclerRow.setOnClickListener {
            Navigation.findNavController(it).navigate(directionDetail)
        }

        val directionMenu = CurrentFragmentDirections
            .actionCurrentFragmentToMenuFragment(diaryList.elementAt(position).id)

        holder.binding.imageView.setOnClickListener {
            Navigation.findNavController(it).navigate(directionMenu)
        }
    }

    override fun getItemCount(): Int {
        return diaryList.size
    }
}