package com.example.service

import com.example.dtos.BarDto
import jakarta.inject.Singleton
import java.time.Instant
import java.util.Optional
import java.util.Random
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


@Singleton
class FooService {
    private val barCache: MutableMap<String, BarDto> = HashMap()
    fun create(id: String): BarDto {
        val bar = BarDto(id, decorate(id), currentSeconds())
        barCache[id] = bar
        return bar
    }

    fun update(id: String): Optional<BarDto> {
        val optBar = findById(id)
        return optBar.map { bar: BarDto ->
            bar.label = decorate(id)
            bar.seconds = currentSeconds()
            bar
        }
    }

    fun findById(id: String): Optional<BarDto> {
        return Optional.ofNullable(barCache[id])
    }

    fun findAll(): List<BarDto> {
        return ArrayList(barCache.values)
    }

    fun remove(id: String): Optional<BarDto> {
        return Optional.ofNullable(barCache.remove(id))
    }

    private fun decorate(text: String): String {
        val decorators = java.util.List.of("***", "---", "|||", "...", "$$$", "!!!")
        val index = Random().nextInt(decorators.size)
        return decorators[index].toString() + " " + text + " " + decorators[index]
    }

    private fun currentSeconds(): Long {
        return Instant.now().epochSecond
    }
}