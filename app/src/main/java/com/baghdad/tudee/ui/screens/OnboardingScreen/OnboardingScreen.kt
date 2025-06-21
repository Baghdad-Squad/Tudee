package com.baghdad.tudee.ui.screens.OnboardingScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.ui.composable.ProgressBar
import com.baghdad.tudee.ui.composable.TudeeCard
import com.baghdad.tudee.ui.composable.button.ButtonDefaults
import com.baghdad.tudee.ui.composable.button.FloatingActionButton
import com.baghdad.tudee.ui.composable.button.TextButton
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onNavigateToHome: () -> Unit
) {
    val pagerState = rememberPagerState {
        3
    }
    val scope = rememberCoroutineScope()
    val titleList = listOf(
        stringResource(id = R.string.title_0),
        stringResource(id = R.string.title_1),
        stringResource(id = R.string.title_2)
    )

    val descriptionList = listOf(
        stringResource(id = R.string.desc_0),
        stringResource(id = R.string.desc_1),
        stringResource(id = R.string.desc_2)
    )

    val robotImage = listOf(
        R.drawable.img_welcome_robot,
        R.drawable.img_angry_robot,
        R.drawable.img_smile_robot
    )


    OnboardingBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {

            HorizontalPager(
                state = pagerState,
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.1f)
                    ) {
                        SkipButton(
                            pagerState = pagerState,
                            onClick = {
                                onNavigateToHome()
                            },
                            modifier = Modifier
                                .align(Alignment.TopStart)
                        )
                    }

                    OnboardingImg(
                        imgRes = robotImage[pagerState.currentPage],
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.3f)
                    )

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .weight(0.2f)
                            .padding(bottom = 48.dp)
                    ) {
                        TudeeCard(
                            title = titleList[pagerState.currentPage],
                            description = descriptionList[pagerState.currentPage],
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )

                        NextButton(
                            imgRes = R.drawable.arrow_right_double,
                            onClick = {
                                if (pagerState.currentPage < titleList.size - 1) {
                                    scope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                    }
                                } else {
                                    onNavigateToHome()
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .offset(y = 10.dp)
                        )
                    }

                    ProgressIndicator(
                        currentScreen = pagerState.currentPage + 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SkipButton(
    pagerState: PagerState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        if (pagerState.currentPage < 2) {
            TextButton(
                label = "Skip",
                onClick = onClick,
                isEnabled = true,
                contentPadding = PaddingValues(
                    vertical = 0.dp,
                    horizontal = 16.dp
                )
            )
        } else {
            Spacer(modifier = Modifier.height(ButtonDefaults.defaultHeight))
        }
    }
}

@Composable
fun OnboardingImg(
    imgRes: Int,

    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = imgRes),
        contentDescription = "Onboarding Image",
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp)
    )
}

@Composable
private fun NextButton(
    imgRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        painter = painterResource(id = imgRes),
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
private fun ProgressIndicator(
    currentScreen: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        ProgressBar(currentScreen = currentScreen)
    }
}
