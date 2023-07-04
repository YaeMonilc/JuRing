package newbing

import cc.yaeko.JuRing
import cc.yaeko.newbing.Config
import cc.yaeko.newbing.entity.CodeMessage
import cc.yaeko.newbing.entity.Conversation
import cc.yaeko.newbing.entity.ResponseMessageA
import cc.yaeko.util.Function.toJson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.MessageChainBuilder
import newbing.entity.ResponseMessageB
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.io.PrintWriter
import java.io.StringWriter
import java.net.URI
import java.util.stream.Collectors


class ChatWebSocket(
    private val groupMessageEvent : GroupMessageEvent,
    private val conversation : Conversation
) : WebSocketClient(URI.create(Config.chatApi)) {
    private val sign = Regex("\\[\\^[0-9]*\\^\\]")
    private val messageRecord : MutableList<ResponseMessageB> = mutableListOf()

    private var complete = false

    override fun onOpen(handshakedata: ServerHandshake?) {
        JuRing.logger.info("Connect")

        send("{\"protocol\":\"json\",\"version\":1}\u001E")
        send("{\"type\":6}\u001E")

        val msg = groupMessageEvent.message.contentToString().replace("@${cc.yaeko.Config.botQQ}", "")
        JuRing.logger.info(msg)

        val message = object : CodeMessage(4) {
            val arguments = listOf(
                object : Any() {
                    val source = "cib"
                    val isStartOfSession = conversation.times < 1
                    val message = object : Any() {
                        val text = msg
                        val messageType = "Chat"
                    }
                    val tone = "Creative"
                    val conversationSignature = conversation.conversationSignature
                    val participant = object : Any() {
                        val id = conversation.clientId
                    }
                    val spokenTextMode = "None"
                    val conversationId = conversation.conversationId
                }
            )
            val invocationId = "2"
            val target = "chat"
        }.toJson() + "\u001E"
        conversation.times++
        send(message)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onMessage(message: String?) {
        if (complete)
            return

        if (cc.yaeko.Config.debug)
            JuRing.logger.warning(message)

        val tmp = message!!.split("\u001E")
        if (tmp.isEmpty())
            return
        val msg = tmp[0].replace("\u001E", "")
        if (msg == "{}")
            return

        var text = ""
        when(JuRing.gson.fromJson(msg, CodeMessage::class.java).type) {
            1 -> {
                JuRing.logger.info("Get Response")
                val json = JuRing.gson.fromJson(msg, ResponseMessageB::class.java)
                if (!json.arguments[0].messages.isNullOrEmpty()) {
                    messageRecord.add(json)
                    if (json.arguments[0].messages[0].text.contains("对不起，我不想继续这个对话。我还在学习，所以我感谢你的理解和耐心。")) {
                        text = json.arguments[0].messages[0].text
                        complete()
                    }else{
                        return
                    }
                }
            }
            3 -> {
                return
            }
            2 -> {
                val collect = JuRing.gson.fromJson(msg, ResponseMessageA::class.java).item.messages.stream().filter {
                    it.author == "bot"
                }.collect(Collectors.toList())
                if (collect.isEmpty()) {
                    return
                }
                text = collect.last().text

            }
            7 -> {
                text = messageRecord[messageRecord.size - 1].arguments[0].messages[0].text
            }
        }

        if (text.contains("*")){
            text = text.replace("*", "")
        }
        if (text.contains(sign)){
            text = text.replace(sign, "")
        }
        GlobalScope.launch {
            JuRing.logger.info(text)
            if (text != ""){
                groupMessageEvent.group.sendMessage(text)
                complete()
            }
        }
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        JuRing.logger.info("Connect Close")
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onError(ex: Exception?) {
        GlobalScope.launch {
            val messageChainBuilder = MessageChainBuilder()
            messageChainBuilder.add("出现错误：")
            messageChainBuilder.add(ex.toString())
            val sw = StringWriter()
            ex?.printStackTrace(PrintWriter(sw, true))
            messageChainBuilder.add(sw.toString())
            Bot.getInstance(cc.yaeko.Config.botQQ).getFriend(cc.yaeko.Config.master)?.sendMessage(messageChainBuilder.build())
            groupMessageEvent.group.sendMessage("出现错误")
            complete()
        }
        JuRing.logger.error(ex)
    }

    private fun complete() {
        complete = true
        close()
    }
}
