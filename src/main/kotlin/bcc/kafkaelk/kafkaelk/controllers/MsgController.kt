package bcc.kafkaelk.kafkaelk.controllers

import bcc.kafkaelk.kafkaelk.models.ProcessExecutionEvent
import com.google.gson.Gson
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.stringify
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.FailureCallback
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.SuccessCallback
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("msg")
class MsgController {
    private val LOG = LoggerFactory.getLogger(ELKController::class.java.name)

    @Autowired
    private val kafkaTemplate: KafkaTemplate<Long, Any>? = null

//    @PostMapping
//    fun sendOrder(@RequestBody test: Test) {
//        kafkaTemplate!!.send("msg", test.msgId, test.msg)
//    }

    @OptIn(UnstableDefault::class)
    @ImplicitReflectionSerializer
    @PostMapping("result")
    fun sendMsg(@RequestBody test: Any) : String{
try {
    var helper: String = "55.3.244.1 GET /index.html 15824 0.043"
    var helper2: String="{\"indexName\":\"griffin\", \"firstname\":\"Vlad\",\"lastname\":\"Forever\"}"

    val response = Json {
        ignoreUnknownKeys = false
        isLenient = true

        //strictMode = true
        //useArrayPolymorphism = true
    }
    //var helper6 = JsonEncodingException(test.toString())
    val gson = Gson()
    val helperMe: String = gson.toJson(test)

//    var helper4 = response.stringify(test) //.JsonConvert.SerializeObject(test);   // .parse<Test>(test.toString()) //.parse<Test>(test.toString()) //response.parseJson(test.toString()) //
//    var helper3: String=helper4.toString();
//    var helper5 = JsonSerializer.Parse<Test>(JsonSerializer.ToString(persons))
//    val obj = JSON.parse(Test.serializer(), """{"a":42}""")

    val future: ListenableFuture<SendResult<Long, Any>> = this.kafkaTemplate!!.send("msg",1, helperMe)


    future.addCallback( SuccessCallback {System.out.println("Success!") }, FailureCallback { x: Throwable? -> System.err.println(x!!.message.toString()) })
    kafkaTemplate.flush()
    return "Ok"
}catch (e: Exception){
    LOG.error(e.toString())

    return e.toString()
}
    }

    @PostMapping("activityResult")
    fun sendActivity(@RequestBody activity: ProcessExecutionEvent) {
        try {
            val future: ListenableFuture<SendResult<Long, Any>> = this.kafkaTemplate!!.send("activity",1, activity)
            future.addCallback( SuccessCallback {System.out.println("Success!") }, FailureCallback { x: Throwable? -> System.err.println(x!!.message.toString()) })
            kafkaTemplate.flush()
        }catch (e: Exception){
            LOG.error(e.toString())
        }
    }
}