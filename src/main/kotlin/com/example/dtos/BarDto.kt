package com.example.dtos

import io.micronaut.core.annotation.Introspected

@Introspected
class BarDto(val id: String?, var label: String?, var seconds: Long)
