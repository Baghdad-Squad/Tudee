package com.baghdad.tudee.presentation.shared

data class Selectable<T>(
    val value: T,
    val isSelected: Boolean = false,
)
