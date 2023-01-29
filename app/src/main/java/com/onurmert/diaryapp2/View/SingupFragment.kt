package com.onurmert.diaryapp2.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.onurmert.diaryapp2.ViewModel.SingupVM
import com.onurmert.diaryapp2.databinding.FragmentSingupBinding
import com.onurmert.retro4fitt.Utils.InternetControl

class SingupFragment : Fragment() {

    private lateinit var binding: FragmentSingupBinding

    private lateinit var auth : FirebaseAuth

    private lateinit var viewModel: SingupVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSingupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(SingupVM::class.java)

        binding.singupBtn.setOnClickListener {
            save(it)
        }
    }

    private fun save(view: View){

        val email = binding.emailEdit.text.toString().trim()
        val password = binding.passwordEdit.text.toString().trim()

        if(!email.equals("")){
            if(!password.equals("")){
               netControl(email, password)
            }else{
                binding.passwordEdit.setError("Fill in the blanks")
                Toast.makeText(requireContext(),"Fill in the blanks", Toast.LENGTH_SHORT).show()
            }
        }else{
            binding.emailEdit.setError("Fill in the blanks")
            Toast.makeText(requireContext(),"Fill in the blanks", Toast.LENGTH_SHORT).show()
        }
    }
    private fun netControl(email : String, password : String){
        if (InternetControl.connectionControl(requireContext()) == true){
            viewModel.singUp(email, password, requireActivity(), requireView())
            binding.progressBar.visibility = View.VISIBLE
            binding.singupBtn.isClickable = false
        }else{
            Toast.makeText(requireContext(),"Check your internet connection", Toast.LENGTH_SHORT).show()
        }
    }
}