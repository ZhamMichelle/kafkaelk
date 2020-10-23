package bcc.kafkaelk.kafkaelk.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.LongDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import java.util.*


@Configuration
class KafkaConsumerConfig {
    @Value("\${spring.kafka.bootstrap-servers}")
    private val kafkaServer: String? = null

    @Value("\${spring.kafka.consumer.group-id}")
    private val kafkaGroupId: String? = null

    @Bean
    fun consumerConfigs(): Map<String, Any?> {
        val props: MutableMap<String, Any?> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaServer
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = LongDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.GROUP_ID_CONFIG] = kafkaGroupId
        return props
    }

    @Bean
    fun kafkaListenerContainerFactory(): KafkaListenerContainerFactory<*> {
        val factory: ConcurrentKafkaListenerContainerFactory<Long, Any> = ConcurrentKafkaListenerContainerFactory<Long, Any>()
        factory.setConsumerFactory(consumerFactory())
        return factory
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<Long, Any> {
        return DefaultKafkaConsumerFactory<Long, Any>(consumerConfigs())
    }

}