package com.baghdad.tudee.domain.entity

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Category (
    val id: Long,
    val title: String,
    val imageUri: String
)
