package com.example.onlineshoptestapp.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.onlineshoptestapp.MainActivity
import com.example.onlineshoptestapp.R
import com.example.onlineshoptestapp.auth.AuthViewModel
import com.example.onlineshoptestapp.databinding.FragmentLoginBinding
import com.example.onlineshoptestapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        val sharedPref = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)

        viewModel.viewModelScope.launch {
            val firstName = viewModel.getCurrentUserData(sharedPref, "firstName")
            val lastName = viewModel.getCurrentUserData(sharedPref, "lastName")
            binding.usernameTv.text = "$firstName $lastName"
        }

        binding.backButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        binding.logoutImage.setOnClickListener {
            viewModel.viewModelScope.launch {
                sharedPref?.edit()?.clear()
                viewModel.logOutUser(sharedPref)
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }
        return view
    }
}