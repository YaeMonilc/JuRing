package cc.yaeko.command.impl

import cc.yaeko.command.CommandManager
import cc.yaeko.newbing.Chat
import command.Command
import command.CommandSetting
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageChainBuilder

@CommandSetting(commandName = "对话次数", commandInfo = "获取对话次数", enable = true)
class ConversationCount : Command {
    override suspend fun handle(messageEvent: GroupMessageEvent, args: List<String>) {
        val messageChainBuilder = MessageChainBuilder()
        messageChainBuilder.append(At(messageEvent.sender))
            .append(" ")
            .append("次数：")
        val conversation = Chat.getConversationByUser(messageEvent.sender)
        messageChainBuilder.append(conversation?.times?.toString() ?: "0")
        messageEvent.group.sendMessage(messageChainBuilder.build())
    }

    override suspend fun handle(messageEvent: FriendMessageEvent, args: List<String>) {
        val messageChainBuilder = MessageChainBuilder()
        messageChainBuilder.append("次数：")
        val conversation = Chat.getConversationByUser(messageEvent.sender)
        messageChainBuilder.append(conversation?.times?.toString() ?: "0")
        messageEvent.sender.sendMessage(messageChainBuilder.build())
    }
}