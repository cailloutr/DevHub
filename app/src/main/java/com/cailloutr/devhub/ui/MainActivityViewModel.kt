package com.cailloutr.devhub.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cailloutr.devhub.model.GithubRepositoryModel
import com.cailloutr.devhub.network.Resource
import com.cailloutr.devhub.network.model.toGithubRepositoryModel
import com.cailloutr.devhub.network.model.toProfitleUiState
import com.cailloutr.devhub.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainActivityViewModel"

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
) : ViewModel() {

    var uiState by mutableStateOf<Resource<ProfileUiState>?>(null)
    var screen = mutableStateOf(Screen.SEARCH)

    fun setScreen(screen: Screen) {
        this.screen.value = screen
    }

    fun getUser(username: String) {
        viewModelScope.launch {
            uiState = Resource.loading(null)

            try {
                var profile: Resource<ProfileUiState>? = null
                var repositories: List<GithubRepositoryModel>? = null
                githubRepository.getUser(username).collect { response ->
                    if (response.isSuccessful) {
                        profile = Resource.success(response.body()?.toProfitleUiState())
                    }
                }
                githubRepository.getUsersRepositories(username).collect {
                    repositories = it.body()?.map { githubUserRepository ->
                        githubUserRepository.toGithubRepositoryModel()
                    }!!
                }

                uiState = Resource.success(
                    data = repositories?.let {
                        profile?.data?.copy(
                            repositories = it
                        )
                    }
                )
                Log.i(TAG, "getUser: $uiState")
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                uiState = Resource.error(e.toString(), data = null)
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
    var repositories: List<GithubRepositoryModel> = listOf(),
)

enum class Screen {
    PROFILE, SEARCH
}