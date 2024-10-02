package rise.tiao1.buut.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * A Room Database [TypeConverter] class for transforming data between the database and application.
 * This class provides conversion methods for enums, date-time objects, lists of integers, and durations.
 */
class Converter {


    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    private val localTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME
    private val gson = Gson()

    /**
     * Converts a [String] to a [LocalDateTime] object.
     */
    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let {
            return LocalDateTime.parse(value, formatter)
        }
    }

    /**
     * Converts a [LocalDateTime] object to a [String].
     */
    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.format(formatter)
    }

    /**
     * Converts a list of integers to a [String] using JSON serialization.
     */
    @TypeConverter
    fun fromIntList(value: List<Int>?): String? {
        return gson.toJson(value)
    }

    /**
     * Converts a [String] containing JSON data to a list of integers.
     */
    @TypeConverter
    fun toIntList(value: String?): List<Int>? {
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, type)
    }

    /**
     * Converts a [String] to a [LocalTime] object.
     */
    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? {
        return value?.let {
            return LocalTime.parse(value, localTimeFormatter)
        }
    }

    /**
     * Converts a [LocalTime] object to a [String].
     */
    @TypeConverter
    fun fromLocalTime(localTime: LocalTime?): String? {
        return localTime?.format(localTimeFormatter)
    }

    /**
     * Converts a [Duration] object to a [String].
     */
    @TypeConverter
    fun fromDuration(duration: Duration?): String? {
        return duration?.toString()
    }

    /**
     * Converts a [String] to a [Duration] object.
     */
    @TypeConverter
    fun toDuration(durationString: String?): Duration? {
        return durationString?.let { Duration.parse(it) }
    }
}