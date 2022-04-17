package com.samsdk.mvvmarchexample.data

class ApiHelper(
    private val apiService: ApiService
) {
    fun getUsers() = apiService.getUsers()
}