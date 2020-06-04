package com.example.test.handlers.impl

import com.example.test.domain.Concept
import com.example.test.domain.Rule
import com.example.test.handlers.RuleHandler
import com.example.test.handlers.TextChanger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class EntityRuleHandler(
    private val textChanger: TextChanger
) : RuleHandler {

    val log = LoggerFactory.getLogger(EntityRuleHandler::class.java)

    override fun supported(concept: Concept): Boolean =
        concept == Concept.ENTITY

    override fun handleRule(rule: Rule, sourceString: String, result: String): String {
        val handledString = handleRule(rule, StringBuilder(result), sourceString)
        log.debug("[EntityRuleHandlerService] result after handling rule = $rule : $result")
        return handledString.toString()
    }

    private fun handleRule(rule: Rule, handledString: StringBuilder, sourceString: String): StringBuilder {
        val handleSubstring = sourceString.substring(IntRange(rule.startPosition, rule.endPosition - 1))
        return textChanger.changeText(
            rule, handledString, sourceString, handleSubstring,
            """<strong>$handleSubstring</strong>""")
    }
}