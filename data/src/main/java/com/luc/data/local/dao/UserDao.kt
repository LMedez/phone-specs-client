package com.luc.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.luc.common.entities.UserProfileEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
abstract class UserDao : BaseDao<UserProfileEntity> {
    @Query("SELECT * FROM UserProfileEntity WHERE uid = :id")
    abstract suspend fun getUser(id: String): UserProfileEntity?

    /**
     * Get a user by id.
     * @return the user from the table with a specific id.
     */
    @Query("SELECT * FROM UserProfileEntity WHERE uid = :id")
    protected abstract fun getUserById(id: String): Flow<UserProfileEntity>

    fun getDistinctUserById(id: String):
            Flow<UserProfileEntity> = getUserById(id).distinctUntilChanged()
}
