package com.example.nakedkafkastreams

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NakedKafkaStreamsApplication

fun main(args: Array<String>) {
	runApplication<NakedKafkaStreamsApplication>(*args)
}
