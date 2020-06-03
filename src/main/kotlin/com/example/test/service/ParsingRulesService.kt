package com.example.test.service

import com.example.test.domain.Rule

interface ParsingRulesService {

    fun parseRules(ruleStrings: List<String>): List<Rule>
}