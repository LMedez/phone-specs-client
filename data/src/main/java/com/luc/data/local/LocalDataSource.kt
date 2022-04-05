package com.luc.data.local

import com.luc.common.entities.asUserProfile
import com.luc.common.model.UserProfile
import com.luc.common.model.asUserProfileEntity
import com.luc.data.local.dao.UserDao

class LocalDataSource(private val userDao: UserDao) {

    /**
     * Declare all methods with internal modifier
     */

    internal suspend fun getUser(id: String) = userDao.getUser(id)?.asUserProfile()
    internal suspend fun insertUser(userProfile: UserProfile) = userDao.insert(userProfile.asUserProfileEntity())

}