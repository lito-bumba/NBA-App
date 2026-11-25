package com.bumba.nbaapp.domain.model

data class Team(
    val id: String,
    val conference: String,
    val division: String,
    val city: String,
    val name: String,
    val abbreviation: String
)
