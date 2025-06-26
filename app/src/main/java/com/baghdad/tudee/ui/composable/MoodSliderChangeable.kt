package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.screens.homeScreen.HomeScreenUIState
import com.baghdad.tudee.ui.screens.homeScreen.SliderState

@Composable
fun MoodSliderChangeable(
    state: HomeScreenUIState,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        Column(modifier = Modifier.fillMaxWidth(0.6f)) {

            TextMoodIcon(
                text = when (state.sliderState) {
                    SliderState.STAY_WORKING -> stringResource(R.string.Stay_working)
                    SliderState.TADOO -> stringResource(R.string.Tadaa)
                    SliderState.ZERO_PROGRESS -> stringResource(R.string.Zero_progress)
                    SliderState.NOTHING_IN_YOUR_LIST -> stringResource(R.string.Nothing_on_your_list)

                },
                icon = painterResource(
                    id = when (state.sliderState) {
                        SliderState.STAY_WORKING -> R.drawable.ic_okay_feedback
                        SliderState.TADOO -> R.drawable.ic_good_feedback
                        SliderState.ZERO_PROGRESS -> R.drawable.ic_bad_feedback
                        SliderState.NOTHING_IN_YOUR_LIST -> R.drawable.ic_poor_feedback

                    }
                )
            )
            Text(
                text = when (state.sliderState) {
                    SliderState.STAY_WORKING -> stringResource(R.string.you_ve_completed_3_out_of_10_tasks_keep_going)
                    SliderState.TADOO -> stringResource(R.string.you_re_doing_amazing_tudee_is_proud_of_you)
                    SliderState.ZERO_PROGRESS -> stringResource(R.string.you_just_scrolling_not_working_tudee_is_watching_back_to_work)
                    SliderState.NOTHING_IN_YOUR_LIST -> stringResource(R.string.fill_your_day_with_something_awesome)
                },
                style = Theme.typography.body.small,
                color = Theme.color.textColor.body,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        Theme.color.primaryColor.normal.copy(0.4f),
                        shape = RoundedCornerShape(100)
                    )
            )

            Image(
                painter = painterResource(
                    when (state.sliderState) {
                        SliderState.STAY_WORKING -> R.drawable.happy_robot
                        SliderState.TADOO -> R.drawable.image_cute_robot
                        SliderState.ZERO_PROGRESS -> R.drawable.image_angry
                        SliderState.NOTHING_IN_YOUR_LIST -> R.drawable.happy_robot

                    }
                ),
                contentDescription = when (state.sliderState) {
                    SliderState.STAY_WORKING -> stringResource(R.string.happy_robot)
                    SliderState.TADOO -> stringResource(R.string.Cute_Robot)
                    SliderState.ZERO_PROGRESS -> stringResource(R.string.Angry_Robott)
                    SliderState.NOTHING_IN_YOUR_LIST -> stringResource(R.string.happy_robot)
                },
            )
        }

    }
}