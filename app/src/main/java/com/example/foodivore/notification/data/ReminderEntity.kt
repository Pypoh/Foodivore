/*
 * Copyright (c) 2019 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.example.foodivore.notification.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlin.collections.ArrayList

@Entity(tableName = "reminder_data")
data class ReminderEntity(
    @PrimaryKey
    @SerializedName("id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String? = null,

    @ColumnInfo(name = "type")
    @SerializedName("type")
    var type: MealType = MealType.Other,

    @ColumnInfo(name = "desc")
    @SerializedName("desc")
    var desc: String? = null,

    @ColumnInfo(name = "hour")
    @SerializedName("hour")
    var hour: Int = 0,

    @ColumnInfo(name = "minute")
    @SerializedName("minute")
    var minute: Int = 0,

    @ColumnInfo(name = "days")
    @SerializedName("days")
    var days: ArrayList<String?>? = null,

    @ColumnInfo(name = "completed")
    @SerializedName("completed")
    var completed: Boolean = false

) : Parcelable {

    enum class MealType {
        Breakfast,
        Lunch,
        Dinner,
        Other
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.apply {
            writeInt(id)
            writeString(name)
            writeString(type.name)
            writeString(desc)
            writeInt(hour)
            writeInt(minute)
            writeStringList(days)
            writeInt(if (completed) 1 else 0)
        }
    }

//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as ReminderEntity
//
//        if (id != other.id) return false
//        if (name != other.name) return false
//        if (type != other.type) return false
//        if (desc != other.desc) return false
//        if (hour != other.hour) return false
//        if (minute != other.minute) return false
//        if (days != null) {
//            if (other.days == null) return false
//            if (!(days as ArrayList).contentEquals(other.days as Array<out String>)) return false
//        } else if (other.days != null) return false
//        if (completed != other.completed) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        var result = id
//        result = 31 * result + (name?.hashCode() ?: 0)
//        result = 31 * result + type.hashCode()
//        result = 31 * result + (desc?.hashCode() ?: 0)
//        result = 31 * result + hour
//        result = 31 * result + minute
//        result = 31 * result + (days?.contentHashCode() ?: 0)
//        result = 31 * result + completed.hashCode()
//        return result
//    }

    companion object CREATOR : Parcelable.Creator<ReminderEntity> {
        override fun createFromParcel(source: Parcel): ReminderEntity {
            return ReminderEntity().apply {
                id = source.readInt()
                name = source.readString()
                type = ReminderEntity.MealType.valueOf(source.readString().toString())
                desc = source.readString()
                hour = source.readInt()
                minute = source.readInt()
                days?.let { source.readStringList(it) }
                completed = source.readInt() == 1
            }
        }

        override fun newArray(size: Int): Array<ReminderEntity?> {
            return arrayOfNulls(size)
        }
    }


}

class ReminderConverter {

    companion object {
        private val gson = Gson()

        @TypeConverter
        @JvmStatic
        fun mealTypeToString(mealType: ReminderEntity.MealType): String = mealType.name


        @TypeConverter
        @JvmStatic
        fun stringToMealType(mealType: String): ReminderEntity.MealType =
            enumValueOf<ReminderEntity.MealType>(mealType)

//        @TypeConverter
//        @JvmStatic
//        fun daysArrayToString(days: ArrayList<String?>?): String {
//            val stringBuilder = StringBuilder()
//            if (days != null) {
//                for (day in days) {
//                    stringBuilder.append(day).append(",")
//                }
//                return stringBuilder.toString()
//            }
//            return ""
//        }
//
//        @TypeConverter
//        @JvmStatic
//        fun stringToDaysArray(days: String): ArrayList<String> {
//            val stringBuilder = StringBuilder()
//            return days.split(",").toList() as ArrayList<String>
//        }

        @TypeConverter
        @JvmStatic
        fun daysListToString(days: ArrayList<String?>?): String = gson.toJson(days)

        @TypeConverter
        @JvmStatic
        fun stringToDaysList(days: String): ArrayList<String> {
            val listType = object : TypeToken<ArrayList<String>>() {}.type
            return gson.fromJson(days ,listType)
        }


    }

}