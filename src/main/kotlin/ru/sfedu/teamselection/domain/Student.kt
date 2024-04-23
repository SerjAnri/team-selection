package ru.sfedu.teamselection.domain

import ru.sfedu.teamselection.enums.Roles
import javax.persistence.*

@Entity
@Table(name = "students")
@Access(AccessType.FIELD)
class Student(
    @Column
    var fio: String = "",
    @Column
    var email: String = "",
    @Column
    var course: Int = 0,
    @Column
    var groupNumber: Int = 0,
    @Column
    var aboutSelf: String = "",
    @Column
    var tags: String = "", // Строка разделенная пробелами
    @Column
    var contacts: String = "",
    @Column
    var status: Boolean = false, // статус участик команды/не участник
    @ManyToOne(fetch = FetchType.LAZY)
    var currentTeam: Team? = null,
    @Column
    var captain: Boolean = false,
    @OneToOne
    var users: Users? = null
): Domain()