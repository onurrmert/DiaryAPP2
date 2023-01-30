package com.onurmert.diaryapp2.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.onurmert.diaryapp2.ViewModel.LoginEmailVM
import com.onurmert.diaryapp2.databinding.FragmentLoginEmailBinding
import com.onurmert.retro4fitt.Utils.InternetControl

class LoginEmailFragment : Fragment() {

    private lateinit var binding : FragmentLoginEmailBinding

    private lateinit var viewModel: LoginEmailVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(LoginEmailVM::class.java)

        viewModel.userControl()

        userControl(view)

        btnClick()
    }

    private fun btnClick(){

        binding.singupBtn.setOnClickListener {
            singUpDirection(it)
        }

        binding.loginBtn.setOnClickListener {
            login()
        }

        binding.resetPaswordText.setOnClickListener {
            resetPasswordDirection(it)
        }
    }

    private fun userControl(view: View){

        val direciton = LoginEmailFragmentDirections.actionLoginEmailFragmentToCurrentFragment()

        viewModel.isUserControl.observe(viewLifecycleOwner, Observer {
            run {
                if (it == true){
                    Navigation.findNavController(view).navigate(direciton)
                }
            }
        })
    }

    private fun login(){

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
            viewModel.login(email, password, requireActivity(), requireView())
            binding.loginBtn.isClickable = false
            binding.singupBtn.isClickable = false
            binding.progressBar.visibility = View.VISIBLE
            isLogin1()
        }else{
            Toast.makeText(requireContext(),"Check your internet connection", Toast.LENGTH_SHORT).show()
        }
    }
    private fun isLogin1(){
        viewModel.isLogin.observe(viewLifecycleOwner, Observer {
            kotlin.run {
                if (it == true){
                    binding.loginBtn.isClickable = true
                    binding.singupBtn.isClickable = true
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun singUpDirection(view: View){
        val direction = LoginEmailFragmentDirections.actionLoginEmailFragmentToSingupFragment()
        Navigation.findNavController(view).navigate(direction)
    }

    private fun resetPasswordDirection(view: View){
        val direction = LoginEmailFragmentDirections.actionLoginEmailFragmentToForgotPasswordFragment()
        Navigation.findNavController(view).navigate(direction)
    }
}