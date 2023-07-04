package cc.yaeko.newbing

import cc.yaeko.newbing.entity.Conversation
import cc.yaeko.newbing.manager.ConversationManager
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import newbing.ChatWebSocket
import java.net.InetSocketAddress
import java.net.Proxy

object Chat {
    private val userConversation : MutableMap<User, Conversation> = mutableMapOf()

    fun openNewConversation(user: User) : Conversation {
        val conversation = ConversationManager.getNewConversation()!!
        userConversation[user] = conversation
        return conversation
    }

    fun getConversationByUser(user: User) : Conversation? = userConversation[user]

    fun closeConversationByUser(user: User) = userConversation.remove(user)

    fun isConversationExists(user: User) : Boolean = userConversation.containsKey(user)

    fun sendMessage(groupMessageEvent: GroupMessageEvent) {
        val user = groupMessageEvent.sender
        val conversation : Conversation =
        if (!isConversationExists(user)){
            openNewConversation(user)
        }else {
            getConversationByUser(user)!!
        }
        val chatWebSocket = ChatWebSocket(groupMessageEvent, conversation)
        chatWebSocket.setProxy(Proxy(Proxy.Type.HTTP, InetSocketAddress("192.168.43.1", 7890)));
        chatWebSocket.connect()
    }
}