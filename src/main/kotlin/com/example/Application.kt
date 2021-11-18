package com.example

import io.micronaut.runtime.Micronaut.* // ktlint-disable no-wildcard-imports
import io.swagger.v3.oas.annotations.* // ktlint-disable no-wildcard-imports
import io.swagger.v3.oas.annotations.info.* // ktlint-disable no-wildcard-imports

@OpenAPIDefinition(
    info = Info(
        title = "demo",
        version = "0.0"
    )
)
object Api
fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("com.example")
        .start()
}
