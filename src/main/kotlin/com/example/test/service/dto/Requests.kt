package com.example.test.service.dto

data class FormatTextRequest(
    val text: String,
    val rules: List<String>
)