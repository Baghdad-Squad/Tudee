package com.baghdad.tudee.viewModel.utils

import com.baghdad.tudee.ui.screens.categories.CategoriesViewModel
import kotlinx.coroutines.delay

suspend fun waitUntilCategoriesLoaded(expectedSize: Int, timeoutMs: Long = 3000 , viewModel: CategoriesViewModel) {
    val start = System.currentTimeMillis()
    while (System.currentTimeMillis() - start < timeoutMs) {
        if (viewModel.state.value.categories.size == expectedSize) return
        delay(50)
    }
    throw AssertionError("Timed out waiting for categories. Current: ${viewModel.state.value.categories}")
}