package com.example.test.rest

import com.example.test.service.dto.FormatTextRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class FormatTextControllerIT {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun `format text must work`() {
        val request = FormatTextRequest(
            text = "Obama visited Facebook headquaters: ref @username",
            rules = listOf(
                "positions 40 through 49 - Twitter username",
                "positions 0 through 5 - Entity",
                "positions 2 through 10 - Hashtag",
                "positions 14 through 22 - Entity",
                "positions 36 through 39 - Link"
            )
        )
        val result = testRestTemplate.postForEntity("/api/v1/format-text", request, String::class.java)
        Assertions.assertEquals(HttpStatus.OK, result.statusCode)
        Assertions.assertEquals(
            "<strong>Obama</strong> visited <strong>Facebook</strong> headquaters:" +
                " <a href=\"ref\">ref</a> @<a href=\"http://twitter.com/username\">username</a>",
            result.body
        )
    }
}