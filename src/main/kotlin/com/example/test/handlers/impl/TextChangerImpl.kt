package com.example.test.handlers.impl

import com.example.test.domain.Rule
import com.example.test.handlers.TextChanger
import org.springframework.stereotype.Component

@Component
class TextChangerImpl : TextChanger {

    override fun changeText(
        rule: Rule,
        handledString: StringBuilder,
        sourceString: String,
        handleSubstring: String,
        resultSubstring: String
    ): StringBuilder {
        var startSubstringIndex = getSubstringIndex(handledString, sourceString, rule, handleSubstring)
        val result = handledString.delete(startSubstringIndex, startSubstringIndex + handleSubstring.length)
        if (startSubstringIndex > result.length) {
            startSubstringIndex = result.length - 1
        }
        return result.insert(startSubstringIndex, resultSubstring)
    }

    private fun getSubstringIndex(
        handledString: java.lang.StringBuilder,
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