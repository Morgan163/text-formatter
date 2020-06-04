package com.example.test.handlers.impl

import com.example.test.domain.Concept
import com.example.test.domain.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class TextChangerImplTest {

    @Autowired
    lateinit var textChangerImpl: TextChangerImpl

    @Test
    fun `change entity rule text must work`() {
        val sourceString = "Obama visited Facebook headquaters: ref @username"
        val handledString = StringBuilder("<strong>Obama</strong> visited Facebook headquaters: ref @username")
        val handledSubstring = "Facebook"
        val resultSubString = "<strong>Facebook</strong>"
        val rule2 = Rule(Concept.ENTITY, 14, 22)
        val result = textChangerImpl.changeText(rule2, handledString, sourceString, handledSubstring, resultSubString)
        assertEquals(
            "<strong>Obama</strong> visited <strong>Facebook</strong> headquaters: ref @username",
            result.toString()
        )
    }

    @Test
    fun `change link rule text must work`() {
        val sourceString = "Obama visited Facebook headquaters: ref @username"
        val handledString = StringBuilder("<strong>Obama</strong> visited Facebook headquaters: ref @username")
        val handledSubstring = "ref"
        val resultSubString = "<a href=\"ref\">ref</a>"
        val rule1 = Rule(Concept.LINK, 36, 39)
        val result = textChangerImpl.changeText(rule1, handledString, sourceString, handledSubstring, resultSubString)
        assertEquals(
            "<strong>Obama</strong> visited Facebook headquaters: <a href=\"ref\">ref</a> @username",
            result.toString()
        )
    }

    @Test
    fun `change username rule text must work`() {
        val sourceString = "Obama visited Facebook headquaters: ref @username"
        val handledString = StringBuilder(
            "<strong>Obama</strong> visited Facebook headquaters:" +
                " <a href=\"ref\">ref</a> @username"
        )
        val handledSubstring = "username"
        val resultSubString = "<a href=\"http://twitter.com/username\">username</a>"
        val rule1 = Rule(Concept.TWITTER_USERNAME, 40, 49)
        val result = textChangerImpl.changeText(rule1, handledString, sourceString, handledSubstring, resultSubString)
        assertEquals(
            "<strong>Obama</strong> visited Facebook headquaters: <a href=\"ref\">ref</a> " +
                "@<a href=\"http://twitter.com/username\">username</a>",
            result.toString()
        )
    }
}