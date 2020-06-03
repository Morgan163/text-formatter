package com.example.test.service.impl

import com.example.test.domain.Concept
import com.example.test.domain.Rule
import com.example.test.service.RuleHandlerService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.StringBuilder

@Service
class LinkRuleHandlerService : RuleHandlerService {

    val log = LoggerFactory.getLogger(LinkRuleHandlerService::class.java)

    override fun handleRules(rules: List<Rule>, sourceString: String, result: String): String {
        var handledString = StringBuilder(result)
        rules.filter { it.concept == Concept.LINK }
            .forEach {
                handledString = handleRule(it, handledString, sourceString)
                log.debug("[LinkRuleHandlerService] result after handling rule = $it : $handledString")
            }
        return handledString.toString()
    }

    private fun handleRule(rule: Rule, handledString: StringBuilder, sourceString: String): StringBuilder {
        validateRule(rule, sourceString)

        val handleSubstring = sourceString.substring(IntRange(rule.startPosition, rule.endPosition - 1))
        var startSubstringIndex = getSubstringIndex(handledString, sourceString, rule, handleSubstring)
        val result = handledString.delete(startSubstringIndex, startSubstringIndex + handleSubstring.length)
        if (startSubstringIndex > result.length) {
            startSubstringIndex = result.length - 1
        }
        return result.insert(
            startSubstringIndex, "$OPEN_LINK_TAG " +
                "$HREF\"$handleSubstring\">$handleSubstring$CLOSE_LINK_TAG"
        )
    }

    companion object {
        const val OPEN_LINK_TAG = "<a"
        const val CLOSE_LINK_TAG = "</a>"
        const val HREF = "href="
    }
}