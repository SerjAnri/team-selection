package ru.sfedu.teamselection.domain

import ru.sfedu.teamselection.enums.Roles
import javax.persistence.*


@Entity
@Table(name = "users")
class Users(
    @Column
    var fio: String = "",
    @Column
    var email: String = "",
    @Column
    @Enumerated(EnumType.STRING)
    var role: Roles? = null,
    @Column
    var registered: Boolean? = null
): Domain()