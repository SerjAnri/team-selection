package ru.sfedu.teamselection.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.sfedu.teamselection.domain.Team

interface TeamRepository: JpaRepository<Team, Long> {
    fun findAllByFullFlag(isFull: Boolean): MutableList<Team>
    fun findTeamByTagsIn(tags: List<String>): MutableList<Team>
}