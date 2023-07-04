package cc.yaeko

import cc.yaeko.command.CommandHandle
import cc.yaeko.command.CommandManager
import cc.yaeko.newbing.Chat
import cc.yaeko.util.ClassUtil
import com.google.gson.Gson
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent

object JuRing : KotlinPlugin(
        JvmPluginDescription(
                id = "cc.yaeko",
                name = "JuRing",
                version = "0.1.0",
        ) {
            author("YaeMonilc")
            info("https://github.com/YaeMonilc/JuRing/")
        }
) {

    val gson = Gson()

    override fun onEnable() {
        ClassUtil.init(jvmPluginClasspath)
        CommandManager.init()

        GlobalEventChannel.subscribeAlways<FriendMessageEvent> {
            if (CommandManager.checkIsCommandMessage(it.message)){
                CommandHandle.runCommand(it)
            }
        }

        GlobalEventChannel.subscribeAlways<GroupMessageEvent> {
            if (CommandManager.checkIsCommandMessage(it.message)){
                CommandHandle.runCommand(it)
            } else if (it.message.contentToString().contains("@${Config.botQQ}"))
                Chat.sendMessage(it)
        }
    }
}