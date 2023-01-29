package com.onurmert.diaryapp2.View

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.onurmert.diaryapp2.ViewModel.CurrentVM
import com.onurmert.diaryapp2.ViewModel.MenuVM
import com.onurmert.diaryapp2.databinding.FragmentMenuBinding
import com.onurmert.retro4fitt.Utils.InternetControl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMenuBinding

    private lateinit var currentViewModel: CurrentVM

    private lateinit var menuViewModel: MenuVM

    private lateinit var alertDialog : AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentViewModel = ViewModelProvider(requireActivity()).get(CurrentVM::class.java)

        menuViewModel = ViewModelProvider(requireActivity()).get(MenuVM::class.java)

        alertDialog = AlertDialog.Builder(requireContext())
    }

    override fun onResume() {
        super.onResume()

        binding.deleteText.setOnClickListener {
            message()
        }
    }

    private fun message(){
        alertDialog.apply {
            this.setMessage("Do you want to delete?")
            this.setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->
                netControl()
            })
        }.show()
    }

    private fun netControl(){
        if (InternetControl.connectionControl(requireContext()) == true){
            menuViewModel.delete(getID(), requireActivity())
            currentViewModel.getDiary()
            findNavController().popBackStack()
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