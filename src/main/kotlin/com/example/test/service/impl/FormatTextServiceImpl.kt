package com.example.test.service.impl

import com.example.test.service.FormatTextService
import com.example.test.service.ParsingRulesService
import com.example.test.handlers.RuleHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FormatTextServiceImpl(
    private val ruleHandlers: List<RuleHandler>,
    private val parsingRulesService: ParsingRulesService
) : FormatTextService {

    val log = LoggerFactory.getLogger(FormatTextServiceImpl::class.java)

    override fun formatText(text: String, rules: List<String>): String {
        val parsedRules = parsingRulesService.parseRules(rules)
        var result = text
        parsedRules.sortedBy { it.startPosition }.forEach { rule ->
            result = ruleHandlers.firstOrNull { it.supported(rule.concept) }?.handleRule(rule, text, result) ?: result
        }
        log.info("Result: $result")
        return result
    }
}