package com.baghdad.tudee.ui.composable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme
import kotlinx.coroutines.launch

@Composable
fun CustomModalBottomSheet(
    show: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetBackgroundColor: Color =Theme.color.surfaceColor.surface,
    scrimColor: Color =  Color(0x99000000),
    sheetHeightFraction: Float = 0.5f,
    content: @Composable ColumnScope.() -> Unit
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val density = LocalDensity.current
    val sheetHeightPx = with(density) { (screenHeight * sheetHeightFraction).toPx() }
    val scope = rememberCoroutineScope()
    val visibleState = remember { MutableTransitionState(false) }
    val offsetY = remember { Animatable(0f) }

    LaunchedEffect(show) {
        visibleState.targetState = show
        if (show) {
            offsetY.snapTo(0f)
        }
    }

    LaunchedEffect(visibleState.currentState, visibleState.targetState) {
        if (!visibleState.currentState && !visibleState.targetState) {
            onDismissRequest()
        }
    }

    if (visibleState.currentState || visibleState.targetState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(scrimColor)
            )

            AnimatedVisibility(
                visibleState = visibleState,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(300)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(300)
                ),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Surface(
                    modifier = modifier
                        .offset { IntOffset(0, offsetY.value.toInt()) }
                        .draggable(
                            orientation = Orientation.Vertical,
                            state = rememberDraggableState { delta ->
                                scope.launch {
                                    val newOffset = (offsetY.value + delta).coerceAtLeast(0f)
                                    offsetY.snapTo(newOffset)
                                }
                            },
                            onDragStopped = {
                                scope.launch {
                                    if (offsetY.value > sheetHeightPx * 0.3f) {
                                        offsetY.animateTo(
                                            targetValue = sheetHeightPx,
                                            animationSpec = tween(200)
                                        )
                                        visibleState.targetState = false
                                    } else {
                                        offsetY.animateTo(
                                            targetValue = 0f,
                                            animationSpec = tween(200)
                                        )
                                    }
                                }
                            }
                        )
                        .fillMaxWidth()
                        .height(screenHeight * sheetHeightFraction),
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    color = sheetBackgroundColor
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp)
                    ) {
                        // Drag handle
                        Box(
                            modifier = Modifier
                                .width(32.dp)
                                .height(4.dp)
                                .background(color = Theme.color.textColor.body.copy(0.3f), shape = CircleShape)
                                .align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        content()
                    }
                }
            }
        }
    }
}






@Composable
fun ExampleScreen() {
    var showSheet by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { showSheet = true }) {
            Text("Open Sheet")
        }

        CustomModalBottomSheet(
            show = showSheet,
            onDismissRequest = { showSheet = false }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .background(Color.White)
                ,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
            ) {
                item{
                    Text(
                        text = "Add Task",
                        style = Theme.typography.title.large
                    )
                }
                item{
                    var text by remember { mutableStateOf("") }

                    TudeeTextField(
                        value = text,
                        onValueChange = { text = it },
                        hint = "Task title",
                        //  leadingIcon = painterResource(id = R.drawable.ic_black_note),
                        height = 150,
                        maxLines = 10
                    )
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun CustomModalBottomSheetPreview() {
    ExampleScreen()
}