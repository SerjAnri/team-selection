package ru.sfedu.teamselection.domain

import java.util.*
import javax.persistence.*


@Entity
@Table(name = "track")
@DiscriminatorColumn(name = "type")
@Access(AccessType.FIELD)
class Track(
    @Column
    var name: String = "",
    @Column
    var about: String = "",
    @Column
    var startDate: Date = Date(),
    @Column
    var endDate: Date = Date(),
    @Column(name = "type")
    var type: String = "", //два значения bachelor/master
    @Column
    var minConstraint: Int = 3,
    @Column
    var maxConstraint: Int = 5,
    @Column
    var maxThirdCourseConstraint: Int? = null,
    @Column
    @OneToMany(mappedBy = "currentTrack", fetch = FetchType.LAZY)
    var currentTeams: List<Team> = mutableListOf()
): Domain()