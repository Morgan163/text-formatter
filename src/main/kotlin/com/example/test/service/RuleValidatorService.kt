package com.example.test.service

import com.example.test.domain.Rule

interface RuleValidatorService {

    fun validateRules(rules: List<Rule>, text: String)
}