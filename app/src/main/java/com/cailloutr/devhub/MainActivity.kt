package com.cailloutr.devhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cailloutr.devhub.model.User
import com.cailloutr.devhub.ui.theme.DevHubTheme

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
                    MyApp(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    Surface(modifier = modifier) {
        Profile(
            user = User(
                name = "Caio Trócilo Canazarro",
                user = "cailloutr",
                bio = "Estudante de Sistemas de Informação IFF - Fluminense, Itaperuna - RJ",
                image = R.drawable.profile_picture,
                pronouns = "he/him"
            )
        )
    }
}

@Composable
fun Profile(user: User) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Image(
                painter = painterResource(id = user.image),
                contentDescription = stringResource(
                    R.string.profile_picture
                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(
                        BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
                        CircleShape
                    )
            )
        }

        Row {
            Text(
                text =
                user.name,
                modifier = Modifier.padding(vertical = 4.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Row {
            Text(
                text = "${user.user} - ${user.pronouns}",
                modifier = Modifier.padding(vertical = 4.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row {
            Text(
                text = user.bio,
                modifier = Modifier.padding(vertical = 4.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DevHubTheme {
        MyApp()
    }
}