package com.example.test.service.impl

import com.example.test.domain.Concept
import com.example.test.domain.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EntityRuleHandlerServiceTest {
    @Autowired
    lateinit var entityRuleHandlerService: EntityRuleHandlerService

    @Test
    fun `handle entity rule must work`() {
        val input = "Obama visited Facebook headquaters: ref @username"
        val rule1 = Rule(Concept.ENTITY, 0, 5)
        val result = entityRuleHandlerService.handleRule(rule1, input, input)
        assertEquals(
            "<strong>Obama</strong> visited Facebook headquaters: ref @username",
            result
        )
    }

    @Test
    fun `handle entity rule must work 2`() {
        val input = "Obama visited Facebook headquaters: ref @username"
        val rule2 = Rule(Concept.ENTITY, 14, 22)
        val result = entityRuleHandlerService.handleRule(rule2, input, input)
        assertEquals(
            "Obama visited <strong>Facebook</strong> headquaters: ref @username",
            result
        )
    }

    @Test
    fun `handle entity rule must throw exception`() {
        val input = "Obama visited Facebook headquaters: ref @username"
        val rule2 = Rule(Concept.ENTITY, 24, 22)
        val exception = assertThrows(IllegalArgumentException::class.java) {
            entityRuleHandlerService.handleRule(rule2, input, input)
        }
        assertEquals("Start position greater than end position", exception.message)
    }
}