package bcc.kafkaelk.kafkaelk.listeners

import bcc.kafkaelk.kafkaelk.models.Test
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.web.client.RestTemplate
import java.text.SimpleDateFormat


@EnableKafka
@SpringBootApplication
class SimpleKafkaExampleApplication {
//    @KafkaListener(topics = ["msg"])
//    fun msgListener(msg: Any) {
//        println(msg)
//    }
@Autowired
var client: RestHighLevelClient? = null
    private var mapper = ObjectMapper().setDateFormat(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ")).setSerializationInclusion(JsonInclude.Include.NON_NULL)


    @KafkaListener(topics = ["msg"])
    fun historyListener(record: ConsumerRecord<Long?, Any?>) {


//        val future: ListenableFuture<SendResult<Long, Any>> = this.kafkaTemplate!!.send("msg",1, record.value())
//        future.addCallback( SuccessCallback {System.out.println("Success!") }, FailureCallback { x: Throwable? -> System.err.println(x!!.message.toString()) })
//        kafkaTemplate.flush()

        val gson = Gson()
        var testModel = gson.fromJson(record.value().toString(), Any::class.java)
       // System.out.println(helperMe)


//
//        val headers = HttpHeaders()
//
//        headers.setAll(mapOf("content-type" to "application/json"))
        val newRequest = Test(28, "Fridaaay")
//        val restTemplate = RestTemplate()
//        val requestBody: HttpEntity<Test> = HttpEntity<Test>(newRequest, headers)
//        val result: ResponseEntity<Any> = restTemplate.postForEntity("http://localhost:9200/griffin/_doc/", requestBody, Any::class.java)

        val indexRequest = IndexRequest("griffin").type("_doc").source(mapper.writeValueAsString(testModel), XContentType.JSON)
        client!!.index(indexRequest, RequestOptions.DEFAULT)

        println(record.partition())
        println(record.key())
        System.out.println(record.value())
    }

    @KafkaListener(topics = ["activity"])
    fun activityListener(record: ConsumerRecord<Long?, Any?>) {
        println(record.partition())
        println(record.key())
        System.out.println(record.value())
    }

//    @KafkaListener(topics = ["msg"])
//    fun msgListener(record: ConsumerRecord<Long?, Any?>) {
//        println(record.partition())
//        println(record.key())
//        System.out.println(record.value())
//    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(SimpleKafkaExampleApplication::class.java, *args)
        }
    }
}