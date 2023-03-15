package com.cailloutr.devhub.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cailloutr.devhub.R
import com.cailloutr.devhub.model.GithubRepositoryModel
import com.cailloutr.devhub.network.Status
import com.cailloutr.devhub.ui.MainActivityViewModel
import com.cailloutr.devhub.ui.ProfileUiState
import com.cailloutr.devhub.ui.theme.DevHubTheme

private const val TAG = "ProfileScreen"

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: MainActivityViewModel = viewModel(),
) {
    val uiState = viewModel.uiState
    LaunchedEffect(null) {
        viewModel.getUser("cailloutr")
        Log.i(TAG, "ProfileScreen: $uiState")
    }
    if (uiState?.status == Status.SUCCESS) {
        uiState.data?.let {
            LazyColumn() {
                item {
                    Profile(state = it, modifier = modifier)
                }

                item {
                    Text(
                        text = "Repositórios",
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                items(uiState.data.repositories) {
                    RepositoryItem(githubUserRepositories = it)
                }
            }
            Log.i(TAG, "User: ${uiState.data}")
        }
    } else {
        Text(text = uiState?.message ?: "Error")
    }
}

@Composable
fun Profile(
    modifier: Modifier = Modifier,
    state: ProfileUiState,
) {
    Column(modifier = modifier) {

        val boxHeight = remember {
            150.dp
        }

        val imageHeight = remember {
            boxHeight
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
                )
                .height(boxHeight)
        ) {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(state.profileImage)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.profile_picture_ct),
                placeholder = painterResource(id = R.drawable.ic_user),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .offset(y = imageHeight / 2)
                    .size(imageHeight)
                    .align(Alignment.BottomCenter)
                    .clip(CircleShape)
                    .border(
                        BorderStroke(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.secondary
                        ),
                        CircleShape
                    )
                    .background(
                        MaterialTheme.colorScheme.secondary
                    )
            )
        }
        Spacer(modifier = Modifier.height(imageHeight / 2))

        Column(
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = state.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
            )
            Text(
                text = state.username,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
            )
        }

        Text(
            text = state.bio,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryItem(
    githubUserRepositories: GithubRepositoryModel,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = githubUserRepositories.name,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.titleSmall
                )
            }
            if (githubUserRepositories.description.isNotEmpty()) {
                Text(
                    text = githubUserRepositories.description,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun ProfileScreenPreview() {
    DevHubTheme {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            ProfileScreen()
        }
    }
}

@Preview
@Composable
fun RepositoryItemWithDescriptionPreview() {
    DevHubTheme {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            RepositoryItem(
                githubUserRepositories =
                GithubRepositoryModel(
                    name = "Teste",
                    description = "Teste123",
                    url = "www.teste.com"
                )
            )
        }
    }
}

@Preview
@Composable
fun RepositoryItemWithoutDescriptionPreview() {
    DevHubTheme {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            RepositoryItem(
                githubUserRepositories =
                GithubRepositoryModel(
                    name = "Teste",
                    description = "",
                    url = "www.teste.com"
                )
            )
        }
    }
}

@Preview
@Composable
fun ProfilePreview() {
    DevHubTheme {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Profile(
                state = ProfileUiState(
                    username = "cailloutr",
                    id = 11111,
                    profileImage = "https://avatars.githubusercontent.com/u/49699297?v=4",
                    name = "Caio",
                    bio = "Estudante"
                )
            )
        }
    }
}
