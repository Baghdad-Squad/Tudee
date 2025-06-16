package com.baghdad.tudee.domain.entity

import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Task @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val title: String,
    val description: String,
    val date: LocalDate,
    val priority: Priority,
    val categoryId: Uuid,
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
