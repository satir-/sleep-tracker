/*
 * Copyright (C) 2023 Noom, Inc.
 */
package com.noom.sleep

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping("/test")
    fun test() : Map<String, String> {
        println("GET /test")
        return mapOf(
            "testMessage" to "Hello world!"
        )
    }
}
