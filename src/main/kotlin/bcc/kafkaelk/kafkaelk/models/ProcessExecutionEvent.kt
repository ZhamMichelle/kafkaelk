package bcc.kafkaelk.kafkaelk.models

import java.util.*

class ProcessExecutionEvent (
        val id: String,
        val activityInstanceId: String,
        val currentActivityId: String,
        val currentTransitionId: String,
        val parentActivityInstanceId: String,
        val parentId: String,
        val processInstanceId: String,
        val processDefinitionId: String,
        val businessKey: String,
        val processBusinessKey: String,
        var variables: MutableMap<String, Any>,
        val currentActivityName: String,
        val startTime: Date,
        val endTime: Date
)