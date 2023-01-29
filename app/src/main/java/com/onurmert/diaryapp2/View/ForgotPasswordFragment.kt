package com.onurmert.diaryapp2.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.onurmert.diaryapp2.ViewModel.ForgotPasswordVM
import com.onurmert.diaryapp2.databinding.FragmentForgotPasswordBinding
import com.onurmert.retro4fitt.Utils.InternetControl

class ForgotPasswordFragment : Fragment() {

    private lateinit var binding : FragmentForgotPasswordBinding

    private lateinit var viewModel : ForgotPasswordVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ForgotPasswordVM::class.java)

        binding.resetPasswordBtn.setOnClickListener {
            getEmail()?.let {
                netControl()
            }
        }
    }

    private fun netControl(){
        if (InternetControl.connectionControl(requireContext()) == true){
            viewModel.resetEmail(getEmail()!!, requireActivity(), requireView())
            binding.resetPasswordBtn.isClickable = false
            binding.progressBar.visibility = View.VISIBLE
        }else{
            Toast.makeText(requireContext(),"Check your internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getEmail() : String? {
        val email = binding.emailEdit.text.toString().trim()
        if (!email.equals("")){
            return email
        }else{
            binding.emailEdit.setError("Fill in the blanks")
            Toast.makeText(requireContext(),"Fill in the blanks", Toast.LENGTH_SHORT).show()
            return null
        }
    }
}