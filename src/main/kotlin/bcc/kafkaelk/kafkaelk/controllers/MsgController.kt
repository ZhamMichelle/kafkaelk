package bcc.kafkaelk.kafkaelk.controllers

import bcc.kafkaelk.kafkaelk.models.JsonModel
import bcc.kafkaelk.kafkaelk.models.Test
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
    @Autowired
    private val kafkaTemplate: KafkaTemplate<Long, Any>? = null

//    @PostMapping
//    fun sendOrder(@RequestBody test: Test) {
//        kafkaTemplate!!.send("msg", test.msgId, test.msg)
//    }

    @PostMapping("result")
    fun sendMsg(@RequestBody test: Any) {

        val future: ListenableFuture<SendResult<Long, Any>> = kafkaTemplate!!.send("msg",1, test.toString())
            future.addCallback( SuccessCallback {System.out.println("Success!") }, FailureCallback { x: Throwable? -> System.err.println(x!!.message.toString()) })
        kafkaTemplate.flush()
    }
}