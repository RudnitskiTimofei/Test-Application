package by.it.andersen.newsapikt.presentation.util

import java.util.*

object Utils {
    val date: String
        get() {
            val date = Date()
            return date.toString()
        }

    val language: String
        get() {
            val myLocale = Locale("blr", "BLR")
            return myLocale.language
        }

    val country: String
        get() {
            val myLocale = Locale("blr", "BLR")
            return myLocale.country
        }
}