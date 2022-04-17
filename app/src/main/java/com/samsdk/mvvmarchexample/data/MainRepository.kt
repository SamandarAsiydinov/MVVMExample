package com.samsdk.mvvmarchexample.data

import com.samsdk.mvvmarchexample.model.User
import io.reactivex.Single

class MainRepository(
    private val apiHelper: ApiHelper
) {
    fun getUsers(): Single<List<User>> {
        return apiHelper.getUsers()
    }
}