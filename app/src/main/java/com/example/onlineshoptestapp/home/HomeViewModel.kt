package com.example.onlineshoptestapp.home

import android.app.Activity
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.onlineshoptestapp.domain.model.Good
import com.example.onlineshoptestapp.domain.model.User
import com.example.onlineshoptestapp.domain.repository.OnlineShopRepository
import com.example.onlineshoptestapp.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class UiState(
    val latestGoods: List<Good>? = null,
    val flashGoods: List<Good>? = null,
    val user: User? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val onlineShopRepository: OnlineShopRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    suspend fun getLatestGoods() {
        onlineShopRepository.getLatestGoods().collect { response ->
            when(response) {
                is Response.Error -> {
                    // Snackbar
                }
                is Response.Loading -> {
                    Log.d("HomeViewModel", "Loading")
                }
                is Response.Success -> {
                    _state.update { currentState ->
                        currentState.copy(latestGoods = response.data)
                    }
                }
            }
        }
    }

    suspend fun getFlashGoods() {
        onlineShopRepository.getFlashGoods().collect { response ->
            when(response) {
                is Response.Error -> {
                    // Snackbar
                }
                is Response.Loading -> {
                    Log.d("HomeViewModel", "Loading")
                }
                is Response.Success -> {
                    _state.update { currentState ->
                        currentState.copy(flashGoods = response.data)
                    }
                }
            }
        }
    }

    suspend fun getUserByFirstName(firstName: String) {
        onlineShopRepository.getUserByFirstName(firstName).collect { response ->
            when (response) {
                is Response.Error -> {}
                is Response.Loading -> {}
                is Response.Success -> {
                    _state.update {
                        it.copy(user = response.data)
                    }
                }
            }
        }
    }

    suspend fun getCurrentUserData(sharedPref: SharedPreferences?, dataType: String): Any? {
        return onlineShopRepository.getCurrentUserData(sharedPref, dataType)
    }

    suspend fun logOutUser(sharedPref: SharedPreferences?) {
        onlineShopRepository.logOutUser(sharedPref)
    }
}