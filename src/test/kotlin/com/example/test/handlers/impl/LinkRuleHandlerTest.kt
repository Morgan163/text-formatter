package com.example.test.handlers.impl

import com.example.test.domain.Concept
import com.example.test.domain.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class LinkRuleHandlerTest {
    @Autowired
    lateinit var linkRuleHandlerService: LinkRuleHandler

    @Test
    fun `handle link rule must work`() {
        val input = "Obama visited Facebook headquaters: ref @username"
        val rule1 = Rule(Concept.LINK, 36, 39)
        val result = linkRuleHandlerService.handleRule(rule1, input, input)
        assertEquals(
            """Obama visited Facebook headquaters: <a href="ref">ref</a> @username""",
            result
        )
    }
}
