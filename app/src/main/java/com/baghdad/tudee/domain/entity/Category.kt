package com.baghdad.tudee.domain.entity

import androidx.annotation.DrawableRes
import com.baghdad.tudee.R

data class Category(
    val id: Long,
    val title: String,
    val image: Image
) {
    sealed class Image {
        data class ByteArray(val data: kotlin.ByteArray) : Image() {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as ByteArray

                return data.contentEquals(other.data)
            }

            override fun hashCode(): Int {
                return data.contentHashCode()
            }
        }

        data class Predefined(val type: PredefinedType) : Image()
    }
    // TODO : Change the paths of the images
    enum class PredefinedType(@DrawableRes val path: Int) {
        EDUCATION(R.drawable.ic_baseball_bat),
        SHOPPING(R.drawable.ic_baseball_bat),
        MEDICAL(R.drawable.ic_baseball_bat),
        GYM(R.drawable.ic_baseball_bat),
        ENTERTAINMENT(R.drawable.ic_baseball_bat),
        COOKING(R.drawable.ic_baseball_bat),
        FAMILY_AND_FRIEND(R.drawable.ic_baseball_bat),
        TRAVELING(R.drawable.ic_baseball_bat),
        AGRICULTURE(R.drawable.ic_baseball_bat),
        CODING(R.drawable.ic_baseball_bat),
        ADORATION(R.drawable.ic_baseball_bat),
        FIXING_BUGS(R.drawable.ic_baseball_bat),
        CLEANING(R.drawable.ic_baseball_bat),
        WORK(R.drawable.ic_baseball_bat),
        BUDGETING(R.drawable.ic_baseball_bat),
        SELF_CARE(R.drawable.ic_baseball_bat),
        EVENT(R.drawable.ic_baseball_bat);

        val formattedName: String
            get(): String {
                return this.name
                    .lowercase()
                    .split('_')
                    .joinToString(" ") { word ->
                        word.replaceFirstChar { it.uppercase() }
                    }
            }

    }
}
