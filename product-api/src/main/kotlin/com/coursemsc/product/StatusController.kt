package com.coursemsc.product

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class StatusController {

    @GetMapping("/status")
    fun health(): ResponseEntity<HashMap<String, Any>> {

        val response = HashMap<String, Any>()

        response["service"] = "Product-API"
        response["status"] = "up"
        response["httpStatus"] = HttpStatus.OK.value()

        return ok(response)
    }
}