package com.beyonnex.anagrams.anagramcheck.dto

import java.util.*

data class AnagramResponse (
    var id: UUID,
    var result: Boolean,
    var texts: List<String>
)