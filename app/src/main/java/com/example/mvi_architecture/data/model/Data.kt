package com.example.mvi_architecture.data.model

data class Data(
    val data: List<User>,
    val page: Int,
    val per_page: Int,
    val support: Support,
    val total: Int,
    val total_pages: Int
)