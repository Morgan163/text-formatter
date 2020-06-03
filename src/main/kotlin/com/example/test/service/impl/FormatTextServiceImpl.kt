package com.example.test.service.impl

import com.example.test.service.FormatTextService
import com.example.test.service.ParsingRulesService
import com.example.test.service.RuleHandlerService
import org.apache.commons.text.StringEscapeUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FormatTextServiceImpl(
    private val ruleHandlers: List<RuleHandlerService>,
    private val parsingRulesService: ParsingRulesService
) : FormatTextService {

    val log = LoggerFactory.getLogger(FormatTextServiceImpl::class.java)

    override fun formatText(text: String, rules: List<String>): String {
        val parsedRules = parsingRulesService.parseRules(rules)
        var result = StringEscapeUtils.escapeHtml4(text)
        ruleHandlers.forEach {
            result = it.handleRules(parsedRules, text, result)
        }
        log.info("Result: $result")
        return result
    }
}