package com.paws.utils.extensions

import java.util.*

fun String.capitalizeFirstLetter(): String {
    return replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }
}

fun String.generateSubBreedsText(): String {
    return "Has $this Sub Breeds"
}