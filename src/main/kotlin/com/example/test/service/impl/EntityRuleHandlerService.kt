package com.example.test.service.impl

import com.example.test.domain.Concept
import com.example.test.domain.Rule
import com.example.test.service.RuleHandlerService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.StringBuilder

@Service
class EntityRuleHandlerService : RuleHandlerService {

    val log = LoggerFactory.getLogger(EntityRuleHandlerService::class.java)

    override fun handleRules(rules: List<Rule>, sourceString: String, result: String): String {
        var handledString = StringBuilder(result)
        rules.filter { it.concept == Concept.ENTITY }
            .forEach {
                handledString = handleRule(it, handledString, sourceString)
                log.debug("[EntityRuleHandlerService] result after handling rule = $it : $result")
            }
        return handledString.toString()
    }

    private fun handleRule(rule: Rule, handledString: StringBuilder, sourceString: String): StringBuilder {
        validateRule(rule, sourceString)

        val handleSubstring = sourceString.substring(IntRange(rule.startPosition, rule.endPosition - 1))
        val startIndexForIndexOf = handledString.length - sourceString.length + rule.startPosition
        var startSubstringIndex = handledString.indexOf(handleSubstring, startIndexForIndexOf)
        val result = handledString.delete(startSubstringIndex, startSubstringIndex + handleSubstring.length)
        if (startSubstringIndex > result.length) {
            startSubstringIndex = result.length - 1
        }
        return result.insert(startSubstringIndex, "$OPEN_STRONG_TAG$handleSubstring$CLOSE_STRONG_TAG")
    }

    companion object {
        const val OPEN_STRONG_TAG = "<strong>"
        const val CLOSE_STRONG_TAG = "</strong>"
    }
}