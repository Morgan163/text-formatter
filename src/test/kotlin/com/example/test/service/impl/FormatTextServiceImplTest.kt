package com.example.test.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class FormatTextServiceImplTest {
    @Autowired
    lateinit var formatTextServiceImpl: FormatTextServiceImpl

    @Test
    fun `format text must work`() {
        val input = "Obama visited Facebook headquaters: ref @username"
        val input1 = "positions 40 through 49 - Twitter username"
        val input2 = "positions 0 through 5 - Entity"
        val input3 = "positions 36 through 39 - Link"
        val input4 = "positions 14 through 22 - Entity"
        val input5 = "positions 7 through 10 - Hashtag"
        val result = formatTextServiceImpl.formatText(input, listOf(input1, input2, input3, input4, input5))
        assertEquals(
            "<strong>Obama</strong> visited <strong>Facebook</strong> headquaters:" +
                " <a href=\"ref\">ref</a> @<a href=\"http://twitter.com/username\">username</a>", result
        )
    }

    @Test
    fun `format text must work 2`() {
        val input = "Obama visited Facebook headquaters: ref @username"
        val input1 = "positions 40 through 49 - Entity"
        val input2 = "positions 0 through 5 - Entity"
        val input3 = "positions 36 through 39 - Link"
        val input4 = "positions 14 through 22 - Twitter username"
        val input5 = "positions 8 through 10 - Hashtag"
        val result = formatTextServiceImpl.formatText(input, listOf(input1, input2, input3, input4, input5))
        assertEquals(
            "<strong>Obama</strong> visited F<a href=\"http://twitter.com/acebook\">acebook</a> headquaters:" +
                " <a href=\"ref\">ref</a> <strong>@username</strong>", result
        )
    }

    @Test
    fun `format text must work with same words`() {
        val input = "Obama obama Obama obama: obama @obama"
        val input1 = "positions 31 through 37 - Twitter username"
        val input2 = "positions 0 through 5 - Entity"
        val input3 = "positions 8 through 10 - Hashtag"
        val input4 = "positions 12 through 17 - Entity"
        val input5 = "positions 25 through 30 - Link"
        val result = formatTextServiceImpl.formatText(input, listOf(input1, input2, input3, input4, input5))
        assertEquals(
            "<strong>Obama</strong> obama <strong>Obama</strong> obama:" +
                " <a href=\"obama\">obama</a> @<a href=\"http://twitter.com/obama\">obama</a>", result
        )
    }
}