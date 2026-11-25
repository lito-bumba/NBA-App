package com.bumba.nbaapp.domain.repository

import com.bumba.nbaapp.domain.model.Team

interface TeamRepository {

    fun getTeams(): List<Team>
}