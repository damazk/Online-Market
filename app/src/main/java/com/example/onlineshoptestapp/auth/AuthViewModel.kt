package com.example.onlineshoptestapp.auth

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshoptestapp.domain.model.User
import com.example.onlineshoptestapp.domain.repository.OnlineShopRepository
import com.example.onlineshoptestapp.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UiState(val user: User? = null)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val onlineShopRepository: OnlineShopRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    suspend fun createUser(firstName: String, lastName: String, email: String) {
        _state.update {
            it.copy(user = null)
        }
        viewModelScope.async {
            onlineShopRepository.createUser(firstName, lastName, email).collect { response ->
                when (response) {
                    is Response.Error -> {}
                    is Response.Loading -> {}
                    is Response.Success -> {
                        _state.update {
                            it.copy(user = response.data)
                        }
                        Log.d("AuthViewModel", "User created!")
                    }
                }
            }
        }.await()
    }

    suspend fun getUserByFirstName(firstName: String) {
        viewModelScope.async {
            onlineShopRepository.getUserByFirstName(firstName).collect { response ->
                when (response) {
                    is Response.Error -> {
                        Log.d("AuthViewModel", "Failed: ${response.message}")
                    }
                    is Response.Loading -> {}
                    is Response.Success -> {
                        _state.update {
                            it.copy(user = response.data)
                        }
                        Log.d("AuthViewModel", "Got user ${state.value.user}")
                    }
                }
            }
        }.await()
    }

    fun logInUser(sharedPref: SharedPreferences?, user: User?) {
        viewModelScope.launch {
            onlineShopRepository.logInUser(sharedPref, user)
        }
    }

    suspend fun resetUser() {
        viewModelScope.async {
            _state.update {
                it.copy(user = null)
            }
        }.await()
    }

    override fun onCleared() {
        super.onCleared()
        _state.update {
            it.copy(user = null)
        }
    }
}