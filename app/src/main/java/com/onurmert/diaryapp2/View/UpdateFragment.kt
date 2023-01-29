package com.onurmert.diaryapp2.View

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.onurmert.diaryapp2.ViewModel.UpdateVM
import com.onurmert.diaryapp2.databinding.FragmentUpdateBinding
import com.onurmert.retro4fitt.Utils.InternetControl

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding

    private lateinit var updateViewModel: UpdateVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateViewModel = ViewModelProvider(requireActivity()).get(UpdateVM::class.java)

        updateViewModel.getOneDiary(getID(), requireActivity())

        getDiary()
    }

    override fun onResume() {
        super.onResume()

        binding.updateBtn.setOnClickListener {
            update()
        }
    }

    private fun getDiary(){
        updateViewModel.diaryModel.observe(viewLifecycleOwner, Observer {
            kotlin.run {
                init(it.title, it.diary)
            }
        })
    }

    private fun update(){
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
            binding.diaryEdit.setError("Fill in the blanks")
            Toast.makeText(requireContext(),"Fill in the blanks", Toast.LENGTH_SHORT).show()
        }
    }

    private fun init(title : String, diary : String){
        binding.diaryEdit.setText(diary)
        binding.titleEdit.setText(title)
    }

    private fun netControl(title : String, diary : String){
        if (InternetControl.connectionControl(requireContext()) == true){
            updateViewModel.update(getID(), title, diary, requireActivity(), requireView())
            binding.updateBtn.isClickable = false
            binding.progressBar.visibility = View.VISIBLE
        }else{
            Toast.makeText(requireContext(),"Check your internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getID() : String{
        val bundle = arguments
        val args = DiaryDetailFragmentArgs.fromBundle(bundle!!)
        return args.id
    }
}