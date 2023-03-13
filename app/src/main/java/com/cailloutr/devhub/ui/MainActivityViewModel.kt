package com.cailloutr.devhub.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cailloutr.devhub.network.Resource
import com.cailloutr.devhub.network.model.toProfitleUiState
import com.cailloutr.devhub.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
) : ViewModel() {

    var profileUiState = MutableStateFlow<Resource<ProfileUiState>>(Resource.loading(null))
        private set

    init {
        getUser("cailloutr")
    }

    private fun getUser(username: String) {
        viewModelScope.launch {
            profileUiState.value = Resource.loading(null)
            try {
                githubRepository.getUser(username).collect { response ->
                    if (response.isSuccessful) {
                        profileUiState.value = Resource.success(response.body()?.toProfitleUiState())
                    } else {
                        profileUiState.value = Resource.error(response.message(), data = null)
                    }
                }
            } catch (e: java.lang.Exception) {
                profileUiState.value = Resource.error(e.toString(), data = null)
            }
        }
    }
}


data class ProfileUiState(
    val username: String,
    val id: Long,
    val profileImage: String,
    val name: String,
    val bio: String,
)