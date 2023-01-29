package com.onurmert.diaryapp2.View

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.onurmert.diaryapp2.ViewModel.DiaryDetailVM
import com.onurmert.diaryapp2.databinding.FragmentDiaryDetailBinding

class DiaryDetailFragment : Fragment() {

    private lateinit var binding : FragmentDiaryDetailBinding

    private lateinit var viewModel : DiaryDetailVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDiaryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(DiaryDetailVM::class.java)

        viewModel.getOneDiary(getID(), requireActivity())

        getData()

        floatingBtn()
    }

    private fun floatingBtn(){
        binding.floatingActionButton.setOnClickListener {
            message()
        }
    }

    private fun message(){

        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            this.setMessage("Do you want to update?")
            this.setPositiveButton("Update", DialogInterface.OnClickListener { dialogInterface, i ->
                val direciton = DiaryDetailFragmentDirections.actionDiaryDetailFragmentToUpdateFragment(getID())
                Navigation.findNavController(requireView()).navigate(direciton)
            })
            this.show()
        }
    }

    private fun getData(){
        viewModel.diaryModel.observe(viewLifecycleOwner, Observer {
            kotlin.run {
                binding.titleText.setText(it.title)
                binding.diaryText.setText(it.diary)
            }
        })
    }

    private fun getID() : String{
        val bundle = arguments
        val args = DiaryDetailFragmentArgs.fromBundle(bundle!!)
        return args.id
    }
}