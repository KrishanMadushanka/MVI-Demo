package com.example.mvi_architecture.uis.intent

sealed class DataIntent {
    object FetchData : DataIntent()
}