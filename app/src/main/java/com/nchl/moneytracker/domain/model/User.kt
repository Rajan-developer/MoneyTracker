package com.nchl.moneytracker.domain.model

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = User.TABLE_NAME)
data class User(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    var id: String,
    @ColumnInfo(name = NAME)
    var name: String? = null,
    @ColumnInfo(name = EMAIl)
    var email: String? = null,
    @ColumnInfo(name = ADDRESS)
    var address: String? = null,
    @ColumnInfo(name = PROFILE_IMAGE)
    var profileImage: String? = null,

    ) {

    companion object {
        const val TABLE_NAME = "users"
        const val COLUMN_ID = "id"
        const val NAME = "name"
        const val EMAIl = "email"
        const val ADDRESS = "address"
        const val PROFILE_IMAGE = "profile_image"

        fun fromContentValues(values: ContentValues): User {
            values.let {
                val user =
                    User(
                        id = values.getAsString(COLUMN_ID)
                    )
                if (it.containsKey(COLUMN_ID)) {
                    user.id = it.getAsString(COLUMN_ID)
                }
                if (it.containsKey(NAME))
                    user.name = it.getAsString(NAME)
                if (it.containsKey(EMAIl))
                    user.email = it.getAsString(EMAIl)
                if (it.containsKey(ADDRESS))
                    user.address = it.getAsString(ADDRESS)
                if (it.containsKey(PROFILE_IMAGE))
                    user.profileImage = it.getAsString(PROFILE_IMAGE)

                return user
            }
        }

    }
}