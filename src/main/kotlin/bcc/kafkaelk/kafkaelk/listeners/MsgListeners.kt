package bcc.kafkaelk.kafkaelk.listeners

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListener


@EnableKafka
@SpringBootApplication
class SimpleKafkaExampleApplication {
//    @KafkaListener(topics = ["msg"])
//    fun msgListener(msg: Any) {
//        println(msg)
//    }

    @KafkaListener(topics = ["msg"])
    fun orderListener(record: ConsumerRecord<Long?, Any?>) {
        println(record.partition())
        println(record.key())
        System.out.println(record.value())
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(SimpleKafkaExampleApplication::class.java, *args)
        }
    }
}