package com.example.onlineshoptestapp.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PatternMatcher
import android.util.Log
import android.util.Patterns
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
import com.example.onlineshoptestapp.databinding.FragmentSignInPageBinding
import com.example.onlineshoptestapp.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInPageFragment : Fragment() {

    private var _binding: FragmentSignInPageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInPageBinding.inflate(inflater, container, false)
        val view = binding.root


        binding.logInTv.setOnClickListener {
            it.findNavController().navigate(R.id.action_signInPageFragment_to_loginFragment)
        }

        binding.signInBtn.setOnClickListener {
            val firstName = binding.firstNameEt.text.toString()
            val lastName = binding.lastNameEt.text.toString()
            val email = binding.emailEt.text.toString()
            if (firstName.isBlank()) {
                Toast.makeText(context, "Please enter your first name and try again",
                    Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(context, "Email is badly formatted. Please check your email validity and try again",
                    Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            viewModel.viewModelScope.launch {
                viewModel.getUserByFirstName(binding.firstNameEt.text.toString())
                if (viewModel.state.value.user == null) {
                    Log.d("SignInPageFragment", "${viewModel.state.value.user}")
                    viewModel.createUser(firstName, lastName, email)
                    val sharedPref = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                    viewModel.logInUser(sharedPref, viewModel.state.value.user)
                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                } else {
                    viewModel.resetUser()
                    Toast.makeText(context, "User with name ${binding.firstNameEt.text} already exist. Please enter another name and try again",
                        Toast.LENGTH_LONG).show()
                }
            }
        }

        return view
    }
}