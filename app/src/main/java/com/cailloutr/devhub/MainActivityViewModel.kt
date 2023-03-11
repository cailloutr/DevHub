package com.cailloutr.devhub

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cailloutr.devhub.network.Resource
import com.cailloutr.devhub.network.model.User
import com.cailloutr.devhub.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
) : ViewModel() {

    var profileUiState = mutableStateOf<Resource<User>>(Resource.loading(null))
        private set

    init {
        getUser("cailloutr")
    }

    private fun getUser(username: String) {
        viewModelScope.launch {
            profileUiState.value = (Resource.loading(null))
            try {
                val response = githubRepository.getUser(username)
                if (response.isSuccessful) {
                    profileUiState.value = (Resource.success(response.body()))
                } else {
                    profileUiState.value = (Resource.error(response.message(), data = null))
                }
            } catch (e: java.lang.Exception) {
                profileUiState.value = (Resource.error(e.message.toString(), data = null))
            }
        }
    }
}