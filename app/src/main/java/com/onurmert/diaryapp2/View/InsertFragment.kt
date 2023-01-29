package com.onurmert.diaryapp2.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.onurmert.diaryapp2.ViewModel.InsertVM
import com.onurmert.diaryapp2.databinding.FragmentInsertBinding
import com.onurmert.retro4fitt.Utils.InternetControl

class InsertFragment : Fragment() {

    private lateinit var binding: FragmentInsertBinding

    private lateinit var viewModel: InsertVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInsertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(InsertVM::class.java)

        binding.insertBtn.setOnClickListener {
            insert()
        }
    }

    private fun insert(){

        val title = binding.titleEdit.text.toString().trim()
        val diary = binding.diaryEdit.text.toString().trim()

        if (!title.equals("")){
            if (!diary.equals("")){
                netControl(title, diary)
            }else{
                binding.diaryEdit.setError("Fill in the blanks")
                Toast.makeText(requireContext(),"Fill in the blanks", Toast.LENGTH_SHORT).show()
            }
        }else{
            binding.titleEdit.setError("Fill in the blanks")
            Toast.makeText(requireContext(),"Fill in the blanks", Toast.LENGTH_SHORT).show()
        }
    }

    private fun netControl(title : String, diary : String){
        if (InternetControl.connectionControl(requireContext()) == true){
            viewModel.insert(title, diary, requireActivity(), requireView())
            binding.insertBtn.isClickable = false
            binding.progressBar.visibility = View.VISIBLE
        }else{
            Toast.makeText(requireContext(),"Check your internet connection", Toast.LENGTH_SHORT).show()
        }
    }
}