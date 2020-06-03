package com.example.test.service

import com.example.test.domain.Rule

interface RuleHandlerService {

    fun handleRules(rules: List<Rule>, sourceString: String, result: String): String

    fun validateRule(rule: Rule, sourceString: String) {
        require(rule.startPosition < sourceString.length) { "Start position greater than text length" }
        require(rule.endPosition <= sourceString.length) { "End position greater than text length" }
        require(rule.startPosition < rule.endPosition) { "Start position greater than end position" }
    }
}