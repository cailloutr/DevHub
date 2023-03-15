package com.cailloutr.devhub.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cailloutr.devhub.R
import com.cailloutr.devhub.network.Resource
import com.cailloutr.devhub.network.Status
import com.cailloutr.devhub.ui.MainActivityViewModel
import com.cailloutr.devhub.ui.ProfileUiState
import com.cailloutr.devhub.ui.theme.DevHubTheme

private const val TAG = "SearchScreen"

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: MainActivityViewModel = viewModel(),
) {
    val uiState: Resource<ProfileUiState>? = viewModel.uiState

    var text by rememberSaveable {
        mutableStateOf("")
    }

    DevHubTheme {
        Surface(modifier = modifier) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(top = 60.dp))
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 50.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(top = 60.dp))
                SearchBar(
                    text = text,
                    onChangeValue = { newText ->
                        text = newText
                    },
                    onSearch = { text ->
                        viewModel.getUser(text)
                    }
                )
                Spacer(modifier = Modifier.padding(top = 32.dp))
                if (uiState != null) {
                    Log.i(TAG, "SearchScreen: uiState: $uiState")
                    SearchResult(state = uiState)
                    if (text.isEmpty()) {
                        viewModel.uiState = null
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    text: String = "",
    onChangeValue: (String) -> Unit,
    onSearch: (String) -> Unit,
) {
    OutlinedTextField(
        value = text,
        onValueChange = { newText -> onChangeValue(newText) },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(30.dp),
        placeholder = {
            Text(text = "Search")
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(text)
            }
        ),
        maxLines = 1,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .heightIn(56.dp)
    )
}


@Composable
fun SearchResult(
    modifier: Modifier = Modifier,
    state: Resource<ProfileUiState>,
) {
    when (state.status) {
        Status.SUCCESS -> {
            state.data?.let { ProfileSearchCard(state = it, modifier = modifier) }
        }
        Status.LOADING -> {
            LoadingSearchCard()
        }
        Status.ERROR -> {
            ErrorSearchCard(modifier = modifier)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSearchCard(
    state: ProfileUiState,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(state.profileImage)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.profile_picture_ct),
                placeholder = painterResource(id = R.drawable.ic_user),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = state.username,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = state.bio,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorSearchCard(
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.error.copy(
                alpha = 0.4f
            )
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.heightIn(80.dp)
        ) {
            Image(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(start = 16.dp)
            )

            Text(
                text = "No users found",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingSearchCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .clip(CircleShape)
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun LoadingSearchCardPreview() {
    DevHubTheme {
        LoadingSearchCard()
    }
}


@Preview
@Composable
fun ProfileSearchCardPreview() {
    DevHubTheme {
        ProfileSearchCard(
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

@Preview
@Composable
fun ErrorSearchCardPreview() {
    DevHubTheme {
        ErrorSearchCard()
    }
}

@Preview
@Composable
fun SearchBarPreview(
    modifier: Modifier = Modifier,
) {
    DevHubTheme {
        SearchBar(modifier = modifier, onChangeValue = {}, onSearch = {})
    }
}

@Preview(heightDp = 320)
@Composable
fun SearchScreenPreview() {
    DevHubTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SearchScreen()
        }
    }
}