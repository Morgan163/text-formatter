package com.example.test.service.impl

import com.example.test.domain.Concept
import com.example.test.domain.Rule
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class RuleValidatorServiceImplTest {

    @Autowired
    lateinit var ruleValidatorServiceImpl: RuleValidatorServiceImpl

    @Test
    fun `rule start position grater than text length must throw exception`() {
        val input = "Obama visited Facebook headquaters: ref @username"
        val rule = Rule(Concept.ENTITY, 52, 55)
        assertThrows(IllegalArgumentException::class.java) {
            ruleValidatorServiceImpl.validateRules(listOf(rule), input)
        }
    }

    @Test
    fun `rule end position grater than text length must throw exception`() {
        val input = "Obama visited Facebook headquaters: ref @username"
        val rule = Rule(Concept.LINK, 38, 55)
        assertThrows(IllegalArgumentException::class.java) {
            ruleValidatorServiceImpl.validateRules(listOf(rule), input)
        }
    }

    @Test
    fun `rule start position less than zero must throw exception`() {
        val input = "Obama visited Facebook headquaters: ref @username"
        val rule = Rule(Concept.TWITTER_USERNAME, -1, 10)
        assertThrows(IllegalArgumentException::class.java) {
            ruleValidatorServiceImpl.validateRules(listOf(rule), input)
        }
    }

    @Test
    fun `rule end position less than start must throw exception`() {
        val input = "Obama visited Facebook headquaters: ref @username"
        val rule = Rule(Concept.ENTITY, 15, 10)
        assertThrows(IllegalArgumentException::class.java) {
            ruleValidatorServiceImpl.validateRules(listOf(rule), input)
        }
    }

    @Test
    fun `rule position crossing must throw exception`() {
        val input = "Obama visited Facebook headquaters: ref @username"
        val rule = Rule(Concept.LINK, 5, 10)
        val rule2 = Rule(Concept.LINK, 8, 15)
        assertThrows(IllegalArgumentException::class.java) {
            ruleValidatorServiceImpl.validateRules(listOf(rule, rule2), input)
        }
    }

    @Test
    fun `rule position crossing must throw exception 2`() {
        val input = "Obama visited Facebook headquaters: ref @username"
        val rule = Rule(Concept.LINK, 10, 25)
        val rule2 = Rule(Concept.LINK, 8, 15)
        assertThrows(IllegalArgumentException::class.java) {
            ruleValidatorServiceImpl.validateRules(listOf(rule, rule2), input)
        }
    }

    @Test
    fun `rule position crossing must throw exception 3`() {
        val input = "Obama visited Facebook headquaters: ref @username"
        val rule = Rule(Concept.LINK, 10, 25)
        val rule2 = Rule(Concept.LINK, 12, 20)
        assertThrows(IllegalArgumentException::class.java) {
            ruleValidatorServiceImpl.validateRules(listOf(rule, rule2), input)
        }
    }
}