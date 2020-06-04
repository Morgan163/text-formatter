package com.example.test.handlers

import com.example.test.domain.Rule

interface TextChanger {

    fun changeText(
        rule: Rule,
        handledString: StringBuilder,
        sourceString: String,
        handleSubstring: String,
        resultSubstring: String
    ): StringBuilder
}