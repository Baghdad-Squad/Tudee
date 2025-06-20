package com.baghdad.tudee.ui.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.baghdad.tudee.domain.entity.Task
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed interface Route {

    @Serializable
    data object OnboardingScreen : Route

    @Serializable
    data object HomeScreen : Route

    @Serializable
    data class TasksScreen(
        val taskState: Task.State = Task.State.IN_PROGRESS,
    ) : Route

    @Serializable
    data object CategoriesScreen : Route

    @Serializable
    data class CategoryTasksScreen(
        val categoryId: Long = 0L,
    ) : Route
}

object CustomNavType {
    val TaskStateType = object : NavType<Task.State>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): Task.State? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Task.State {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Task.State): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Task.State) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }

}