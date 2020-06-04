package com.example.test.service.impl

import com.example.test.domain.Concept
import com.example.test.domain.Rule
import com.example.test.service.ParsingRulesService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ParsingRulesServiceImpl : ParsingRulesService {

    val log = LoggerFactory.getLogger(ParsingRulesServiceImpl::class.java)

    override fun parseRules(ruleStrings: List<String>): List<Rule> {
        val rules = mutableListOf<Rule>()
        ruleStrings.forEach {
            rules.add(parseRule(it))
        }
        return rules
    }

    private fun intervalsNotCrossing(rules: List<Rule>, newRule: Rule): Boolean {
        rules.forEach {
            if (newRule.startPosition <= it.endPosition && newRule.endPosition >= it.startPosition) {
                return false
            }
        }
        return true
    }

    private fun parseRule(rule: String): Rule {
        val parts = rule.split(POSITIONS_DELIMITER, THROUGH_DELIMITER, HYPHEN_DELIMITER).filter { it.isNotEmpty() }
        val startPosition = parts[0].trim().toIntOrNull()
        val endPosition = parts[1].trim().toIntOrNull()
        requireNotNull(startPosition) { "Start position should be digit" }
        requireNotNull(endPosition) { "End position should be digit" }
        val concept = Concept.valueOfConceptName(parts[2].trim())
        if (Concept.OTHER == concept) {
            log.warn("Rule is not supported. Rule: $rule")
        }
        return Rule(concept, startPosition, endPosition)
    }

    companion object {
        const val POSITIONS_DELIMITER = "positions"
        const val THROUGH_DELIMITER = "through"
        const val HYPHEN_DELIMITER = "-"
    }
}