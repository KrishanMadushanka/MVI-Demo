package com.example.mvi_architecture.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvi_architecture.data.api.ApiHelper
import com.example.mvi_architecture.data.repository.MainRepository
import com.example.mvi_architecture.uis.viewmodel.DataViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataViewModel::class.java)) {
            return DataViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}