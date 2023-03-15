package com.cailloutr.devhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cailloutr.devhub.ui.MainActivityViewModel
import com.cailloutr.devhub.ui.Screen
import com.cailloutr.devhub.ui.screens.ProfileScreen
import com.cailloutr.devhub.ui.screens.SearchScreen
import com.cailloutr.devhub.ui.theme.DevHubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevHubTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp(
    viewModel: MainActivityViewModel = viewModel(),
) {
    val screen = viewModel.screen
    DevHubTheme {
        if (screen.value == Screen.PROFILE) {
                ProfileScreen()
        } else {
            SearchScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    MyApp()
}