package com.baghdad.tudee.ui.screens.tasks

import androidx.compose.ui.graphics.painter.Painter
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.R

data class TasksUiState(
    val tasks: List<TaskUi> = emptyList(),
//        listOf(
//        TaskUi(
//            "Design UI mockups",
//            "Create wireframes for the app",
//            Task.Priority.HIGH,
//            (R.drawable.ic_airplane)
//        ),
//        TaskUi(
//            "Implement login",
//            "Add authentication flow",
//            Task.Priority.MEDIUM,
//            (R.drawable.ic_birthday_cake)
//        ),
//        TaskUi(
//            "Write unit tests",
//            "Cover main business logic",
//            Task.Priority.LOW,
//            (R.drawable.ic_baseball_bat)
//        ),
//        TaskUi(
//            "Setup CI/CD",
//            "Configure build pipeline",
//            Task.Priority.HIGH,
//            (R.drawable.ic_book_open)
//        ),
//        TaskUi(
//            "Code review",
//            "Review PR #123",
//            Task.Priority.MEDIUM,
//            (R.drawable.ic_chef)
//        ),
//        TaskUi(
//            "Deploy to staging",
//            "Push latest changes",
//            Task.Priority.LOW,
//            (R.drawable.ic_body_part_muscle)
//        )
//    ),
    val selectedTab: Task.State = Task.State.TODO
)

data class TaskUi(
    val title: String,
    val description: String,
    val priority: Task.Priority,
    val icon: Int
)
