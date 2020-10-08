package bcc.kafkaelk.kafkaelk

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaelkApplication

fun main(args: Array<String>) {
	runApplication<KafkaelkApplication>(*args)
}
