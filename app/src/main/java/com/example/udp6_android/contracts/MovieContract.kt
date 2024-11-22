package com.example.udp6_android.contracts

import android.provider.BaseColumns

class MovieContract {
    companion object {
        const val DB_NAME = "movieDb"
        const val VERSION = 1
        class Entry: BaseColumns {
            companion object {
                const val TABLE = "Movie"
                const val ID = "id"
                const val TITLE = "title"
                const val DESCRIPTION = "description"
                const val IMG_ID = "imgResId"
                const val DURATION = "duration"
                const val RELEASE_YEAR = "releaseYear"
                const val COUNTRY = "country"
            }
        }
    }
}