package ru.sfedu.teamselection.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.opendevl.JFlat
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import ru.sfedu.teamselection.domain.Student
import ru.sfedu.teamselection.domain.Team
import ru.sfedu.teamselection.domain.Track
import ru.sfedu.teamselection.log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


@Service
class ReportService {

    private val objectMapper = ObjectMapper()
    fun trackToExcelFile(track: Track): ByteArray {
        val jsonTrack = objectMapper.writeValueAsString(track)
        val flat = JFlat(jsonTrack)
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        flat.json2Sheet().headerSeparator("_").jsonAsSheet;
        flat.write2csv("${track.name}.csv");
        return File("${track.name}.csv").readBytes()
    }

    @Bean
    fun test(){
        trackToExcelFile(Track(
            name = "first track",
            currentTeams = mutableListOf(Team(
                name = "test",
                students = mutableListOf(Student(fio = "test1"), Student("test2"))
            )),
            startDate = Date(),
            type = "bachelor",
            maxThirdCourseConstraint = 1,
        ))
    }
}