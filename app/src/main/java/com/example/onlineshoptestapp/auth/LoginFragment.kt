package com.example.onlineshoptestapp.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.onlineshoptestapp.R
import com.example.onlineshoptestapp.databinding.FragmentLoginBinding
import com.example.onlineshoptestapp.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE )

        val state = viewModel.state

        binding.logInBtn.setOnClickListener {
            val firstName = binding.firstNameEt.text.toString()
            if (firstName.isBlank()) {
                Toast.makeText(context, "Please enter your first name and try again",
                    Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            viewModel.viewModelScope.launch {
                viewModel.getUserByFirstName(firstName)
                if (state.value.user != null) {
                    Toast.makeText(requireContext(), "Welcome back, $firstName!", Toast.LENGTH_LONG).show()
                    val sharedPref = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                    viewModel.logInUser(sharedPref, viewModel.state.value.user)
                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "There's no user with name $firstName. Please enter another name, or register new account.",
                        Toast.LENGTH_LONG).show()
                }
            }
        }

        return view
    }
}