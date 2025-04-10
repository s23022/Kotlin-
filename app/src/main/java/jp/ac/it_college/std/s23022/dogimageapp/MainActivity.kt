package jp.ac.it_college.std.s23022.dogimageapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.*
import java.net.URL
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogImageApp()
        }
    }
}

@Composable
fun DogImageApp() {
    var imageUrl by remember { mutableStateOf<String?>(null) }

    // 起動時に一度だけ画像を取得
    LaunchedEffect(Unit) {
        imageUrl = fetchRandomDogImage()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        imageUrl?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Random Dog",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                imageUrl = fetchRandomDogImage()
            }
        }) {
            Text("もっと見る！")
        }
    }
}

fun fetchRandomDogImage(): String? {
    return try {
        val json = URL("https://dog.ceo/api/breeds/image/random").readText()
        val obj = JSONObject(json)
        obj.getString("message")
    } catch (e: Exception) {
        null
    }
}
