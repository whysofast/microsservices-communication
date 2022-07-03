package com.coursemsc.product

import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableRabbit
@SpringBootApplication
class MicrosservicesCommunicationApplication

fun main(args: Array<String>) {
    runApplication<MicrosservicesCommunicationApplication>(*args)
}
