package com.example.test.handlers

import com.example.test.domain.Concept
import com.example.test.domain.Rule
import java.lang.StringBuilder

interface RuleHandler {

    fun supported(concept: Concept): Boolean

    fun handleRule(rule: Rule, sourceString: String, result: String): String

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