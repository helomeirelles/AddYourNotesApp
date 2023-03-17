package com.addyournotes.utils

import androidx.room.TypeConverter
import java.util.UUID

class UUIDConverter {
    @TypeConverter
    fun fromUUID(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun uuidFromString(UUIDString: String): UUID {
        return UUID.fromString(UUIDString)
    }
}