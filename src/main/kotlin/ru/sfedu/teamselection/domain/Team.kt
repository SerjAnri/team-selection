package ru.sfedu.teamselection.domain

import javax.persistence.*

@Entity
@Table(name = "team")
@Access(AccessType.FIELD)
class Team(
    @Column
    var name: String = "",
    @Column
    var about: String = "",
    @Column
    var quantityOfStudents: Int = 0,
    @Column
    var fullFlag: Boolean = false,
    @Column
    var tags: String = "",
    @ManyToOne(fetch = FetchType.LAZY)
    var currentTrack: Track? = null,
    @Column
    @OneToMany(mappedBy = "currentTeam", fetch = FetchType.LAZY)
    var students: MutableList<Student> = mutableListOf()
): Domain()