package com.baghdad.tudee.domain.entity

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Category @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val title: String,
    val imageUri: String
)
