package newbing.entity

import cc.yaeko.newbing.entity.CodeMessage

class ResponseMessageB(
    type: Int,
    val arguments: List<Inner>
) : CodeMessage(type) {
    data class Inner(
        val messages: List<Message>
    )
    data class Message (
        val text: String,
        val author: String
    )
}
