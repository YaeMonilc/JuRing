package cc.yaeko.newbing.manager

import cc.yaeko.JuRing
import cc.yaeko.newbing.Config
import cc.yaeko.newbing.entity.Conversation
import cc.yaeko.util.HttpRequest

object ConversationManager {
    fun getNewConversation() : Conversation? {
        return JuRing.gson.fromJson(HttpRequest.getForBing(Config.conversionApi), Conversation::class.java)
    }
}