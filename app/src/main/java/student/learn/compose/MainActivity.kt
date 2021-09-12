package student.learn.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coil.compose.rememberImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainViewModel : ViewModel() {
    var images = MutableLiveData(listOf(
        "https://images.pexels.com/photos/212286/pexels-photo-212286.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/163016/crash-test-collision-60-km-h-distraction-163016.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/1366944/pexels-photo-1366944.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/5878501/pexels-photo-5878501.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/3846022/pexels-photo-3846022.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
    ))
}

class MainActivity : ComponentActivity() {
    private val model by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(model)
        }
    }
}

@Composable
fun MainScreen(model: MainViewModel) {
    val images: List<String> by model.images.observeAsState(listOf())

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        images?.take(2).reversed().forEach {
            Card(url = it) {
                val d = model.images.value?.toMutableList()
                d?.let {
                    it.removeFirst()
                    model.images.value = it
                }
            }
        }
    }
}


@Composable
fun Card(
    url: String,
    advance: ()-> Unit = {},
){
    val coroutineScope = rememberCoroutineScope()
    var offsetX = remember(url) { Animatable(0f) }

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), 0) }
            .fillMaxSize()
            .background(color = Color.White)
            .clickable {
                coroutineScope.launch {
                    offsetX.animateTo(
                        targetValue = 3000F
                    )
                }
                coroutineScope.launch {
                    delay(400)
                    advance()
                }
            }
    ) {
        Image(
            painter = rememberImagePainter(
                data = url,
            ),
            contentDescription = null,
            modifier = Modifier
                .size(400.dp, 400.dp)
                .aspectRatio(0.8f)
        )
    }
}
