package com.cailloutr.devhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    DevHubTheme() {
        Profile(
            user = User(
                name = "Caio Trócilo Canazarro",
                user = "cailloutr",
                bio = "Estudante de Sistemas de Informação IFF - Fluminense, Itaperuna - RJ",
                image = "https://avatars.githubusercontent.com/u/49699297?v=4",
                pronouns = "he/him"
            ),
            modifier = Modifier
        )
    }
}

@Composable
fun Profile(
    user: User,
    modifier: Modifier = Modifier,
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
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.image)
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
            )
            Text(
                text = "${user.user} - ${user.pronouns}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
            )
        }

        Text(
            text = user.bio,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DevHubTheme {
        MyApp()
    }
}