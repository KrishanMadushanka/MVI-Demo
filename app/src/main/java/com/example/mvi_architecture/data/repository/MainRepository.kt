package com.example.mvi_architecture.data.repository

import com.example.mvi_architecture.data.api.ApiHelper


class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getData()

}