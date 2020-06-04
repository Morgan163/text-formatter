package com.example.test.service

import com.example.test.domain.Concept
import com.example.test.domain.Rule
import java.lang.StringBuilder

interface RuleHandlerService {

    fun supported(concept: Concept): Boolean

    fun handleRule(rule: Rule, sourceString: String, result: String): String

    fun validateRule(rule: Rule, sourceString: String) {
        require(rule.startPosition < sourceString.length) { "Start position greater than text length" }
        require(rule.endPosition <= sourceString.length) { "End position greater than text length" }
        require(rule.startPosition < rule.endPosition) { "Start position greater than end position" }
        require(rule.startPosition >= 0) { "Start position must not be less than zero " }
        require(rule.endPosition >= 0) { "End position must not be less than zero " }
    }

    fun getSubstringIndex(
        handledString: StringBuilder,
        sourceString: String,
        rule: Rule,
        handleSubstring: String
    ): Int {
        val startIndexForIndexOf = handledString.length - sourceString.length + rule.startPosition
        var startSubstringIndex = handledString.indexOf(handleSubstring, startIndexForIndexOf)
        if (startSubstringIndex == -1) {
            startSubstringIndex = handledString.indexOf(handleSubstring, rule.startPosition)
        }
        return startSubstringIndex
    }
}