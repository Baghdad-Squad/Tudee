package com.baghdad.tudee.ui.shared

data class Selectable<T>(
    val value: T,
    val isSelected: Boolean = false,
)
