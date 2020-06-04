package com.example.test.service.impl

import com.example.test.domain.Concept
import com.example.test.domain.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ParsingRulesServiceImplTest {
    @Autowired
    lateinit var parsingRulesServiceImpl: ParsingRulesServiceImpl

    @Test
    fun `parsing entity rule must work`() {
        val input = "positions 14 through 22 - Entity"
        assertEquals(
            listOf(Rule(Concept.ENTITY, 14, 22)),
            parsingRulesServiceImpl.parseRules(listOf(input))
        )
    }

    @Test
    fun `parsing link rule must work`() {
        val input1 = "positions 17 through 25 - Link"
        val input2 = "positions 54 through 78 - Link"
        assertEquals(
            listOf(Rule(Concept.LINK, 17, 25), Rule(Concept.LINK, 54, 78)),
            parsingRulesServiceImpl.parseRules(listOf(input1, input2))
        )
    }

    @Test
    fun `parsing username rule must work`() {
        val input1 = "positions 17 through 25 - Twitter username"
        val input2 = "positions 54 through 78 - Twitter username"
        assertEquals(
            listOf(
                Rule(Concept.TWITTER_USERNAME, 17, 25),
                Rule(Concept.TWITTER_USERNAME, 54, 78)
            ),
            parsingRulesServiceImpl.parseRules(listOf(input1, input2))
        )
    }

    @Test
    fun `parsing rules must work`() {
        val input1 = "positions 17 through 25 - Twitter username"
        val input2 = "positions 54 through 78 - Link"
        val input3 = "positions 2 through 10 - Hashtag"
        assertEquals(
            listOf(
                Rule(Concept.TWITTER_USERNAME, 17, 25),
                Rule(Concept.LINK, 54, 78),
                Rule(Concept.OTHER, 2, 10)
            ),
            parsingRulesServiceImpl.parseRules(listOf(input1, input2, input3))
        )
    }

    @Test
    fun `parsing rule must throw exception`() {
        val input1 = "positions four through 25 - Link"
        val input2 = "positions 54 through 78 - Link"
        assertThrows(IllegalArgumentException::class.java) {
            parsingRulesServiceImpl.parseRules(listOf(input1, input2))
        }
    }
}