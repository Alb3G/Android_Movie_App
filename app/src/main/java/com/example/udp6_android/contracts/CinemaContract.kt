package com.example.udp6_android.contracts

import android.provider.BaseColumns

class CinemaContract {
    class Entry: BaseColumns {
        companion object {
            const val TABLE = "Cinema"
            const val ID = "id"
            const val NAME = "name"
            const val CITY = "city"
            const val LATITUDE = "latitude"
            const val LONGITUDE = "longitude"
        }
    }
}
