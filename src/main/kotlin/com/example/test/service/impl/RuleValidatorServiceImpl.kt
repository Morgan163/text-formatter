package com.example.test.service.impl

import com.example.test.domain.Rule
import com.example.test.service.RuleValidatorService
import org.springframework.stereotype.Service

@Service
class RuleValidatorServiceImpl : RuleValidatorService {

    override fun validateRules(rules: List<Rule>, text: String) {
        val textLength = text.length
        rules.forEach {
            validateRulePositions(it, textLength)
            require(intervalsNotCrossing(rules, it)) { "Rule intervals must not crossing" }
        }
    }

    private fun validateRulePositions(rule: Rule, textLength: Int) {
        require(rule.startPosition < textLength) { "Start position greater than text length" }
        require(rule.endPosition <= textLength) { "End position greater than text length" }
        require(rule.startPosition < rule.endPosition) { "Start position greater than end position" }
        require(rule.startPosition >= 0) { "Start position must not be less than zero " }
        require(rule.endPosition >= 0) { "End position must not be less than zero " }
    }

    private fun intervalsNotCrossing(rules: List<Rule>, newRule: Rule): Boolean {
        rules.filter { it != newRule }
            .forEach {
            if (newRule.startPosition <= it.endPosition && newRule.endPosition >= it.startPosition) {
                return false
            }
        }
        return true
    }
}