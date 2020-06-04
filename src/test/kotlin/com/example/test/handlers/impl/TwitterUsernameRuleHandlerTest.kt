package com.example.test.handlers.impl

import com.example.test.domain.Concept
import com.example.test.domain.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class TwitterUsernameRuleHandlerTest {
    @Autowired
    lateinit var twitterUsernameRuleHandlerService: TwitterUsernameRuleHandler

    @Test
    fun `handle username rule must work`() {
        val input = "Obama visited Facebook headquaters: ref @username"
        val rule1 = Rule(Concept.TWITTER_USERNAME, 40, 49)
        val result = twitterUsernameRuleHandlerService.handleRule(rule1, input, input)
        assertEquals(
            """Obama visited Facebook headquaters: ref @<a href="http://twitter.com/username">username</a>""",
            result
        )
    }
}
