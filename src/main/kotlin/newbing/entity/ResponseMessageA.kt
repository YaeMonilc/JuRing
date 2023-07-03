package cc.yaeko.newbing.entity

class ResponseMessageA(
    type: Int,
    val item: Item
) : CodeMessage(type) {
    data class Item(
        val messages : List<Message>
    )
    data class Message (
        val text : String,
        val author : String
    )
}