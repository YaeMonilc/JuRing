package cc.yaeko

import cc.yaeko.iw233.manager.RandomImageManager
import cc.yaeko.newbing.Chat
import cc.yaeko.newbing.Config
import cc.yaeko.newbing.manager.ConversationManager
import cc.yaeko.util.Function.toJson
import com.google.gson.Gson
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageChainBuilder

object JuRing : KotlinPlugin(
        JvmPluginDescription(
                id = "cc.yaeko",
                name = "JuRing",
                version = "0.1.0",
        ) {
            author("YaeMonilc")
        }
) {

    val gson = Gson()

    override fun onEnable() {
        

        GlobalEventChannel.subscribeAlways<GroupMessageEvent> {
            val message = it.message.contentToString()
            if (message.contains("#开始新对话")){
                Chat.closeConversationByUser(it.sender)
                val messageChainBuilder = MessageChainBuilder()
                messageChainBuilder.add(At(it.sender))
                messageChainBuilder.add("已清空对话")
                Chat.openNewConversation(it.sender)
                it.group.sendMessage(messageChainBuilder.build())
            }else if (message.contains("#对话次数")){
                val messageChainBuilder = MessageChainBuilder()
                messageChainBuilder.add(At(it.sender))
                messageChainBuilder.add("次数：")
                messageChainBuilder.add(Chat.getConversationByUser(it.sender).times.toString())
                it.group.sendMessage(messageChainBuilder.build())
            }else if (message.contains("#设置Cookie")){
                val strArray = message.split(" ")
                if (strArray.size >= 2) {
                    Config.cookie = strArray[1]
                    it.group.sendMessage("设置成功")
                }
            }else if (message.contains("#查看Cookie")){
                val messageChainBuilder = MessageChainBuilder()
                messageChainBuilder.add("Cookie：")
                messageChainBuilder.add(Config.cookie)
                it.group.sendMessage(messageChainBuilder.build())
            }else if (message.contains("#随机图片")){
                it.group.sendImage(RandomImageManager.random()!!)
            } else {
                if (it.message.contentToString().contains("@${cc.yaeko.Config.botQQ}"))
                    Chat.sendMessage(it)
            }
        }
    }
}