package com.baghdad.tudee.ui.screens.onboardingScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.baghdad.tudee.R
import com.baghdad.tudee.ui.composable.ProgressBar
import com.baghdad.tudee.ui.composable.TudeeCard
import com.baghdad.tudee.ui.composable.button.FloatingActionButton
import com.baghdad.tudee.ui.composable.button.TextButton
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onNavigateToHome: () -> Unit,
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

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (pagerState.currentPage < 2) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(), contentAlignment = Alignment.TopStart
                ) {
                    TextButton(
                        label = "Skip",
                        modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues()),
                        onClick = {
                            onNavigateToHome()
                        },
                        isEnabled = true,
                        contentPadding = PaddingValues(
                            vertical = 0.dp,
                            horizontal = 16.dp
                        ),
                    )
                }
            } else Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(WindowInsets.statusBars.asPaddingValues())
                    .padding(top = 56.dp)
            )

            HorizontalPager(
                state = pagerState,

                ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {


                    item {

                        Image(
                            painter = painterResource(robotImage[pagerState.currentPage]),
                            contentDescription = "Onboarding Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(260.dp)
                        )
                    }
                    item { Spacer(modifier = Modifier.height(32.dp)) }
                    item {


                        Box (modifier = Modifier.fillMaxWidth()){
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

                                    .align(Alignment.BottomCenter)
                                    .padding(16.dp)
                                    .offset(y = 28.dp)
                                    .zIndex(1f)

                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                    }
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