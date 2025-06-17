package com.baghdad.tudee.ui.component

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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
    val titleList = remember {
        listOf(
            "Overwhelmed with tasks?",
            "Uh-oh! Procrastinating again",
            "Let’s complete task and celebrate together."
        )
    }
    val descriptionList = remember {
        listOf(
            "Let’s bring some order to the chaos. Tudee is here to help you sort, plan, and breathe easier.",
            "Tudee not mad… just a little disappointed, Let’s get back on track together.",
            "Progress is progress. Tudee will celebrate you on for every win, big or small."
        )
    }
    val robotImage = remember {
        listOf(
            R.drawable.img_welcome_robot,
            R.drawable.img_angry_robot,
            R.drawable.img_smile_robot
        )
    }

    OnboardingBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.align(Alignment.Center)
            ) {
                HorizontalPager(
                    state = pagerState,

                    ) {
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            if (pagerState.currentPage < 2) {
                                TextButton(
                                    label = "Skip",
                                    onClick = {
                                        onNavigateToHome()
                                    },
                                    isEnabled = true,
                                    contentPadding = PaddingValues(
                                        vertical = 0.dp,
                                        horizontal = 16.dp
                                    )
                                )
                            } else {
                                Spacer(modifier = Modifier.height(ButtonDefaults.defaultHeight)) // Placeholder for alignment
                            }
                        }

                        Spacer(modifier = Modifier.height(153.dp))

                        Image(
                            painter = painterResource(robotImage[pagerState.currentPage]),
                            contentDescription = "Onboarding Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(260.dp)
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Box {
                            TudeeCard(
                                title = titleList[pagerState.currentPage],
                                description = descriptionList[pagerState.currentPage]
                            )

                            FloatingActionButton(
                                painter = painterResource(id = R.drawable.arrow_right_double),
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
                                    .align(Alignment.BottomEnd)
                                    .padding(16.dp)
                                    .offset(x = -(155).dp, y = 28.dp)
                                    .zIndex(1f)
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    ProgressBar(currentScreen = pagerState.currentPage + 1)
                }
            }
        }
    }
}

