package com.example.test.service.impl

import com.example.test.domain.Concept
import com.example.test.domain.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class TwitterUsernameRuleHandlerServiceTest {
    @Autowired
    lateinit var twitterUsernameRuleHandlerService: TwitterUsernameRuleHandlerService

    @Test
    fun `handle username rule must work`() {
        val input = "Obama visited Facebook headquaters: ref @username"
        val rule1 = Rule(Concept.TWITTER_USERNAME, 40, 49)
        val rule2 = Rule(Concept.ENTITY, 14, 22)
        val result = twitterUsernameRuleHandlerService.handleRules(listOf(rule1, rule2), input, input)
        assertEquals(
            "Obama visited Facebook headquaters: ref @<a href=\"http://twitter.com/username\">username</a>",
            result
        )
    }

    @Test
    fun `handle username rule must throw exception`() {
        val input = "Obama visited Facebook headquaters: ref @username"
        val rule1 = Rule(Concept.TWITTER_USERNAME, 41, 60)
        val rule2 = Rule(Concept.ENTITY, 14, 22)
        assertThrows(IllegalArgumentException::class.java) {
            twitterUsernameRuleHandlerService.handleRules(listOf(rule1, rule2), input, input)
        }
    }
}