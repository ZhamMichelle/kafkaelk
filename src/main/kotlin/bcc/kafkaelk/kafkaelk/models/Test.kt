package bcc.kafkaelk.kafkaelk.models

import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable


class Test (val msgId: Long, val msg: String)

@Serializable
class Container<T>(val data: T)