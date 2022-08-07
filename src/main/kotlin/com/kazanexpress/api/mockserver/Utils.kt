package com.kazanexpress.api.mockserver

import org.apache.commons.text.similarity.JaccardSimilarity
import kotlin.random.Random

private val CHAR_POOL: List<Char> = ('a'..'z') + ('0'..'9')

fun randomString(length: Int): String {
    return (1..length)
        .map { Random.nextInt(0, CHAR_POOL.size) }
        .map(CHAR_POOL::get)
        .joinToString("")
}

fun similarity(s1: String?, s2: String?): Double =
    if (s1.isNullOrEmpty() && s2.isNullOrEmpty()) {
        1.0
    } else {
        JaccardSimilarity().apply(s1 ?: "", s2 ?: "")
    }