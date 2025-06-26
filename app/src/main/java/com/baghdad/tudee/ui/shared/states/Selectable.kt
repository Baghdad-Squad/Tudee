package com.baghdad.tudee.ui.shared.states

data class Selectable<T>(
    val value: T,
    val isSelected: Boolean = false,
)