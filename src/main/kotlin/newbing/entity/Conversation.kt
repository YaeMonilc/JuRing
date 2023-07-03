package cc.yaeko.newbing.entity

data class Conversation(
    val conversationId : String,
    val clientId : String,
    val conversationSignature : String,
    val result : Result,
    var times: Int = 0
){
    data class Result(
        val value : String,
        val message : String
    )
}
