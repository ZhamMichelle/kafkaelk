package bcc.kafkaelk.kafkaelk.controllers


import bcc.kafkaelk.kafkaelk.models.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpEntity
import org.springframework.web.client.RestTemplate
import java.io.PrintWriter
import java.io.StringWriter
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.logging.Level


@RestController
@RequestMapping("elk")
internal class ELKController {
    private val LOG = LoggerFactory.getLogger(ELKController::class.java.name)

    @Autowired
    var restTemplete: RestTemplate? = null

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

    @RequestMapping(value = ["/elk"])
    fun helloWorld(): String {
        val response = "FedorBlond" + Date()
        LOG.info(Level.INFO.toString(), response)
        return response
    }

    @RequestMapping(value = ["/exception"])
    fun exception(): String {
        var response = ""
        response = try {
            throw Exception("Exception has occured....")
        } catch (e: Exception) {
            e.printStackTrace()
            LOG.error(e.toString())
            val sw = StringWriter()
            val pw = PrintWriter(sw)
            e.printStackTrace(pw)
            val stackTrace = sw.toString()
            LOG.error("Exception - $stackTrace")
            stackTrace
        }
        return response
    }

    @PostMapping("test")
    fun test(){
      try{
        val restTemplate=RestTemplate()
        val headers=HttpHeaders()
        headers.setAll(mapOf("content-type" to "application/json"))
        val newRequest  = Test(msgId = 2, msg="Imagine Dragons")
        val requestBode: HttpEntity<Any> = HttpEntity<Any>(newRequest, headers)
        val result: ResponseEntity<Any> = restTemplate.postForEntity("http://localhost:5555", requestBode, Any::class.java)
      }catch (e:java.lang.Exception){
          LOG.error(e.toString())
      }
      }

    @GetMapping("test2")
    fun test2(): Any{
        try{
            val restTemplate=RestTemplate()
            val headers=HttpHeaders()
            headers.setAll(mapOf("content-type" to "application/json"))
            val newRequest  = Test(msgId = 2, msg="Imagine Dragons")
            val requestBody: HttpEntity<Any> = HttpEntity<Any>(newRequest, headers)
            val result: ResponseEntity<Any> = restTemplate.postForEntity("http://localhost:8080/elk/urlTest", requestBody, Any::class.java)
        return result
        }catch (e:java.lang.Exception){
            LOG.error(e.toString())
            return "error"
        }
    }

    @PostMapping("urlTest")
    fun urlTest(@RequestBody test: Test ) : Test{
            return test
    }
}