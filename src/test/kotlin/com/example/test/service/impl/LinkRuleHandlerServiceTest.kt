package com.example.test.service.impl

import com.example.test.domain.Concept
import com.example.test.domain.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class LinkRuleHandlerServiceTest {
    @Autowired
    lateinit var linkRuleHandlerService: LinkRuleHandlerService

    @Test
    fun `handle link rule must work`() {
        val input = "Obama visited Facebook headquaters: ref @username"
        val rule1 = Rule(Concept.LINK, 36, 39)
        val rule2 = Rule(Concept.ENTITY, 14, 22)
        val result = linkRuleHandlerService.handleRules(listOf(rule1, rule2), input, input)
        assertEquals(
            "Obama visited Facebook headquaters: <a href=\"ref\">ref</a> @username",
            result
        )
    }

    @Test
    fun `handle link rule must throw exception`() {
        val input = "Obama visited Facebook headquaters: ref @username"
        val rule1 = Rule(Concept.LINK, 50, 55)
        val rule2 = Rule(Concept.ENTITY, 14, 22)
        assertThrows(IllegalArgumentException::class.java) {
            linkRuleHandlerService.handleRules(listOf(rule1, rule2), input, input)
        }
    }
}