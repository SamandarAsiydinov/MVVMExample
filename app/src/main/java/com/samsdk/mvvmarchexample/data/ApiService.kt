package com.samsdk.mvvmarchexample.data

import com.samsdk.mvvmarchexample.model.User
import io.reactivex.Single

interface ApiService {
    fun getUsers(): Single<List<User>>
}