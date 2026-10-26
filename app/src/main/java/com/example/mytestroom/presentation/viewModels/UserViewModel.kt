package com.example.mytestroom.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytestroom.data.models.UserData
import com.example.mytestroom.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Boolean
import kotlin.time.Duration.Companion.milliseconds

data class UserUiState(
    val isSaving: Boolean = false,
    val editUserId: Long? = null,
    val users: List<UserData> = emptyList(),
    val error: String? = null,
    val userName: String = "",
    val userLocation: String = ""
)

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())

    val uiState = _uiState.asStateFlow()

    init {
        observeUsers()
    }

    fun observeUsers() {
        viewModelScope.launch {
            try {
                userRepository.getUsers().collect { userData ->
                    _uiState.update {
                        it.copy(users = userData)
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    fun updateUserName(strName: String) {
        _uiState.update {
            it.copy(
                userName = strName,
                error = null
            )
        }
    }

    fun updateUserLocation(strLocation: String) {
        _uiState.update {
            it.copy(
                userLocation = strLocation,
                error = null
            )
        }
    }

    fun clearUserData() {

        _uiState.update {
            it.copy(
                isSaving = false,
                editUserId = null,
                error = null,
                userName = "",
                userLocation = ""
            )
        }
    }

    fun saveUser() {
        val currState = _uiState.value

        val strName = currState.userName.trim()
        val strLocation = currState.userLocation.trim()

        if (strName.isEmpty()) {
            _uiState.update {
                it.copy(
                    error = "Please enter user name"
                )
            }
            return
        }

        if (strLocation.isEmpty()) {
            _uiState.update {
                it.copy(
                    error = "Please enter user location"
                )
            }
            return
        }

        viewModelScope.launch {
            try {
                val userData = UserData()
                userData.userName = strName
                userData.userLocation = strLocation
                if (currState.editUserId != null) {
                    userData.id = currState.editUserId

                    userRepository.updateUser(userData)
                } else {
                    userRepository.insertUser(userData)
                }
                delay(500.milliseconds)
                clearUserData()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Failed to save"
                    )
                }
            }
        }
    }

    fun deleteUser(userData: UserData) {
        viewModelScope.launch {
            try {
                userRepository.deleteUser(userData)
            } catch (e: Exception) {
            }
        }
    }

    fun editUser(userData: UserData) {
        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(
                        userLocation = userData.userLocation,
                        userName = userData.userName,
                        error = null,
                        editUserId = userData.id
                    )
                }
            } catch (e: Exception) {
            }
        }
    }

}