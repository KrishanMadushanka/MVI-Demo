package com.example.mvi_architecture.data.api

import com.example.mvi_architecture.data.model.Data

interface ApiHelper {

    suspend fun getData(): Data

}