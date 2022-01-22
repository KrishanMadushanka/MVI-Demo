package com.example.mvi_architecture.data.api

import com.example.mvi_architecture.data.model.Data

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getData(): Data {
        return apiService.getData()
    }
}