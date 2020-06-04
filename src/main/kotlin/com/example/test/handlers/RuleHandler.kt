package com.example.test.handlers

import com.example.test.domain.Concept
import com.example.test.domain.Rule

interface RuleHandler {

    fun supported(concept: Concept): Boolean

    fun handleRule(rule: Rule, sourceString: String, result: String): String
}