package com.example.test.rest

import com.example.test.service.FormatTextService
import com.example.test.service.dto.FormatTextRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FormatTextController(
    private val formatTextService: FormatTextService
) {

    @PostMapping("api/v1/format-text")
    fun formatText(@RequestBody request: FormatTextRequest): String =
        formatTextService.formatText(request.text, request.rules)
}