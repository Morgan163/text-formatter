package com.example.test.domain

enum class Concept(val conceptName: String) {
    ENTITY("Entity"),
    LINK("Link"),
    TWITTER_USERNAME("Twitter username"),
    OTHER("Not suported concept")
    ;

    companion object {
        fun valueOfConceptName(name: String): Concept {
            return values().find { name == it.conceptName } ?: OTHER
        }
    }
}
