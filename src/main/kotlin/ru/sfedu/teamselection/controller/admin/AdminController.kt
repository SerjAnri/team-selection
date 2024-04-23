package ru.sfedu.teamselection.controller.admin

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.sfedu.teamselection.domain.Student
import ru.sfedu.teamselection.domain.Track
import ru.sfedu.teamselection.enums.Roles
import ru.sfedu.teamselection.log
import ru.sfedu.teamselection.repository.TrackRepository
import ru.sfedu.teamselection.repository.UserRepository
import ru.sfedu.teamselection.service.ReportService
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/admin")
class AdminController(
    private val userRepository: UserRepository,
    private val trackRepository: TrackRepository,
    private val reportService: ReportService
) {

    // Распределение ролей
    @GetMapping("/giveRole")
    fun giveUserRole(@RequestParam email: String, @RequestParam role: String): ResponseEntity<String> {
        return try{
            userRepository.updateRoleByEmail(email, Roles.valueOf(role))
            ResponseEntity.ok().body("Пользователю $email выдана роль $role")
        } catch (ex: Exception){
            log.error { "Exception while giving role to user $email: ${ex.message}" }
            ResponseEntity.ok().body("Не удалось выдать роль пользователю $email")
        }
    }

    // Формирование отчетов
    @GetMapping("/getExcelForTrack", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun createExcelForTrack(@RequestParam id: Long): ResponseEntity<ByteArray>? {
        val track = trackRepository.findById(id).getOrNull()
        return track?.let { ResponseEntity.ok().body(reportService.trackToExcelFile(track)) } ?: ResponseEntity<ByteArray>(HttpStatus.NOT_FOUND)
    }

    // Работа с треками
    @GetMapping("/tracks")
    fun getAllTracks() = trackRepository.findAll().let {
        return@let if (it.isNotEmpty()) ResponseEntity<MutableList<Track>>(it, HttpStatus.OK)
        else ResponseEntity<MutableList<Track>>(HttpStatus.METHOD_NOT_ALLOWED)
    }

    @GetMapping("/trackById")
    fun getTrackById(@RequestBody trackId: Long) = try {
        ResponseEntity.ok(trackRepository.findById(trackId))
    } catch (ex: Exception){
        log.debug{ "Error occurred while writing student to DB: ${ex.message}" }
        ResponseEntity<Student>(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/tracks/createTrack")
    fun createTrack(@RequestBody track: Track) = try {
        trackRepository.save(track)
        ResponseEntity<Student>(HttpStatus.OK)
    } catch (ex: Exception){
        log.debug{ "Error occurred while writing student to DB: ${ex.message}" }
        ResponseEntity<Student>(HttpStatus.METHOD_NOT_ALLOWED)
    }

    @DeleteMapping("/track/deleteTrack")
    fun deleteTrack(@RequestBody trackId: Long) = try {
        trackRepository.deleteById(trackId)
        ResponseEntity.ok()
    } catch (ex: Exception) {
        log.debug { "Error occurred while writing student to DB: ${ex.message}" }
        ResponseEntity<Any>(HttpStatus.METHOD_NOT_ALLOWED)
    }

    @PostMapping("/tracks/changeTrack")
    fun changeTrack(@RequestBody track: Track) = try {
        val foundTrack = trackRepository.findById(track.id!!).getOrNull()
        foundTrack?.apply {
            name = track.name
            about = track.about
            startDate = track.startDate
            endDate = track.endDate
            type = track.type
            minConstraint = track.minConstraint
            maxConstraint = track.maxConstraint
            maxThirdCourseConstraint = track.maxThirdCourseConstraint
        }
        trackRepository.save(track)
        ResponseEntity.ok()
    } catch (ex: Exception){
        log.debug{ "Error occurred while writing student to DB: ${ex.message}" }
        ResponseEntity<Any>(HttpStatus.METHOD_NOT_ALLOWED)
    }
}