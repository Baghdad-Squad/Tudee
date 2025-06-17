package com.baghdad.tudee.data.fakeData

import com.baghdad.tudee.R
import com.baghdad.tudee.domain.entity.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
fun fakeCategoriesData(): Flow<List<Category>> {
    return flow {
        emit(listOf(
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ), Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ), Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ), Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            ),
            Category(
                id = Uuid.random(),
                title = "Fake",
                imageUri = R.drawable.ic_baseball_bat.toString()
            )
        ))
    }
}