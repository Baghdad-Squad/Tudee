package com.baghdad.tudee.domain.entity

data class Category(
    val id: Long,
    val title: String,
    val image: Image
) {
    val isPredefinedCategory: Boolean
        get() = this.image is Image.Predefined


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

    enum class PredefinedType {
        EDUCATION,
        SHOPPING,
        MEDICAL,
        GYM,
        ENTERTAINMENT,
        COOKING,
        FAMILY_AND_FRIEND,
        TRAVELING,
        AGRICULTURE,
        CODING,
        ADORATION,
        FIXING_BUGS,
        CLEANING,
        WORK,
        BUDGETING,
        SELF_CARE,
        EVENT;

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
