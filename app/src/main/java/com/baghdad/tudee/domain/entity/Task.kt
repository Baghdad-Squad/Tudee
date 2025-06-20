package com.baghdad.tudee.domain.entity

import kotlinx.datetime.LocalDate

data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val date: LocalDate,
    val priority: Priority,
    val categoryId: Long,
    val state: State
){
    enum class State {
        TODO,
        IN_PROGRESS,
        DONE
    }

    enum class Priority {
        LOW,
        MEDIUM,
        HIGH
    }
}
