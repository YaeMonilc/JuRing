package cc.yaeko.command.impl

import cc.yaeko.data.manager.UserDataManager
import command.Command
import command.CommandSetting
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageChainBuilder

@CommandSetting(commandName = "我的信息", commandInfo = "查看自己的信息", enable = true)
class AboutMe : Command {
    override suspend fun handle(messageEvent: GroupMessageEvent, args: List<String>) {
        val userData = UserDataManager.getData(messageEvent.sender.id)
        val messageChain = MessageChainBuilder()
            .append(At(messageEvent.sender))
            .append(" ").append("\n")
            .append("💵: ${userData.money}").append("\n")
            .append("🧾: ${userData.level}")
            .build()
        messageEvent.group.sendMessage(messageChain)
    }

    override suspend fun handle(messageEvent: FriendMessageEvent, args: List<String>) {
        val userData = UserDataManager.getData(messageEvent.sender.id)
        val messageChain = MessageChainBuilder()
            .append(At(messageEvent.sender))
            .append(" ").append("\n")
            .append("💵: ${userData.money}").append("\n")
            .append("🧾: ${userData.level}")
            .build()
        messageEvent.friend.sendMessage(messageChain)
    }
}