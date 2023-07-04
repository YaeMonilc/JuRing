package cc.yaeko.command.impl

import command.Command
import command.CommandSetting
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageChainBuilder

@CommandSetting(commandName = "菜单", commandInfo = "查看菜单", enable = true)
class Menu : Command {
    override suspend fun handle(messageEvent: GroupMessageEvent, args: List<String>) {
        val messageChain = MessageChainBuilder()
            .append(At(messageEvent.sender))
            .append(" ").append("\n")
            .append("       ---").append("菜单").append("---").append("\n")
            .append("| ✔").append("签到").append(" | 🎫").append("抽奖").append(" |").append("\n")
            .append("------------------")
            .build()
        messageEvent.group.sendMessage(messageChain)
    }

    override suspend fun handle(messageEvent: FriendMessageEvent, args: List<String>) {
        val messageChain = MessageChainBuilder()
            .append(At(messageEvent.sender))
            .append(" ").append("\n")
            .append("       ---").append("菜单").append("---").append("\n")
            .append("| ✔").append("签到").append(" | 🎫").append("抽奖").append(" |").append("\n")
            .append("------------------")
            .build()
        messageEvent.friend.sendMessage(messageChain)
    }
}