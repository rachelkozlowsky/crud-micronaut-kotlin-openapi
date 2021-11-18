package com.example.controlls

import com.example.dtos.BarDto
import com.example.service.FooService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.reactivex.rxjava3.core.Single
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import javax.validation.constraints.NotBlank

@Controller("/")
class FooController(private val fooService: FooService) {

    // POST
    @Post(uri = "/bars", produces = [MediaType.APPLICATION_JSON])
    @Operation(
        summary = "Creates a new bar object adding a decorated id and the current time",
        description = "Showcase of the creation of a dto"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Entry was successfully added or its weight was updated"),
        ApiResponse(
            responseCode = "201",
            description = "Bar object correctly created",
            content = [Content(mediaType = "application/json", schema = Schema(type = "BarDto"))]
        ),
        ApiResponse(
            responseCode = "500",
            description = "Remote error, server is going nuts"
        ),
        ApiResponse(responseCode = "400", description = "Invalid id Supplied")
    )
    @Tag(name = "create")
    fun create(@Body id: @NotBlank String?): Single<HttpResponse<BarDto>> {
        return Single.just(HttpResponse.created(fooService.create(id!!)))
    }

    // PUT
    @Put(uri = "/bars/{id}", produces = [MediaType.APPLICATION_JSON])
    @Operation(
        summary = "Updates an existing bar object with a new label and modifying the current time",
        description = "Showcase of the update of a dto"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Bar object correctly updated", content = [Content(mediaType = "application/json", schema = Schema(type = "BarDto"))]),
        ApiResponse(responseCode = "404", description = "Bar not found by using the provided id"),
        ApiResponse(responseCode = "500", description = "Remote error, server is going nuts")
    )
    @Tag(name = "update")
    fun update(@Parameter(description = "Id to generate a Bar object") id: @NotBlank String?): Single<HttpResponse<BarDto>> {
        return Single.just(
            fooService.update(id!!)
                .map<MutableHttpResponse<BarDto>> { body: BarDto? ->
                    HttpResponse.ok(
                        body
                    )
                }
                .orElseGet { HttpResponse.notFound() }
        )
    }

    // GET BY ID
    @Get(uri = "/bars/{id}", produces = [MediaType.APPLICATION_JSON])
    @Operation(
        summary = "Find the bar object corresponding to the provided id",
        description = "Showcase of a finder method returning a dto"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200", description = "A bar object has been successfully found and returned",
            content = [Content(mediaType = "application/json", schema = Schema(type = "BarDto"))]
        ),
        ApiResponse(responseCode = "404", description = "Bar not found by using the provided id"),
        ApiResponse(responseCode = "500", description = "Remote error, server is going nuts")
    )
    @Tag(name = "findById")
    fun findById(@Parameter(description = "id to find a bar object") id: @NotBlank String?): Single<HttpResponse<BarDto>> {
        return Single.just(
            fooService.findById(id!!)
                .map<MutableHttpResponse<BarDto>> { body: BarDto? ->
                    HttpResponse.ok(
                        body
                    )
                }
                .orElseGet { HttpResponse.notFound() }
        )
    }

    // GET ALL
    @Get(uri = "/bars", produces = [MediaType.APPLICATION_JSON])
    @Operation(summary = "Find all the bar objects", description = "Showcase of a method returning a list of dtos")
    @ApiResponses(
        ApiResponse(
            responseCode = "200", description = "A bar object has been successfully found and returned",
            content = [Content(mediaType = "application/json", schema = Schema(type = "BarDto"))]
        ),
        ApiResponse(responseCode = "404", description = "Bar not found by using the provided id"),
        ApiResponse(responseCode = "500", description = "Remote error, server is going nuts")
    )
    @Tag(name = "findAll")
    fun findAll(): Single<List<BarDto>> {
        return Single.just(fooService.findAll())
    }

    // DELETE
    @Delete(uri = "/bars/{id}", produces = [MediaType.APPLICATION_JSON])
    @Operation(
        summary = "Remove the bar object corresponding to the provided id",
        description = "Showcase of a deletion method"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200", description = "Bar object has been correctly removed",
            content = [Content(mediaType = "application/json", schema = Schema(type = "BarDto"))]
        ),
        ApiResponse(responseCode = "500", description = "Remote error, server is going nuts")
    )
    @Tag(name = "remove")
    fun remove(@Parameter(description = "Id to remove a bar object") id: @NotBlank String?): Single<HttpStatus> {
        fooService.remove(id!!)
        return Single.just(HttpStatus.ACCEPTED)
    }
}
