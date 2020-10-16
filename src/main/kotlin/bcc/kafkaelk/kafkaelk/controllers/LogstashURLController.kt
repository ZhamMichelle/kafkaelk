package bcc.kafkaelk.kafkaelk.controllers

import bcc.kafkaelk.kafkaelk.models.Test
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
internal class LogstashURLController {
    @PostMapping
    fun urlTest(@RequestBody test: Test) : Test {
        return test
    }
}