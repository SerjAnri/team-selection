package ru.sfedu.teamselection.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.sfedu.teamselection.domain.Student

interface StudentRepository: JpaRepository<Student, Long> {
    fun findAllByCaptain(flag: Boolean): MutableList<Student>
    fun findStudentByStatus(flag: Boolean): MutableList<Student>
    fun findStudentByTagsIn(tags: List<String>): MutableList<Student>
}