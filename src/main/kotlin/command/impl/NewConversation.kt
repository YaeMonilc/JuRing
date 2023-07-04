package command.impl

import cc.yaeko.newbing.Chat
import command.Command
import command.CommandSetting
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageChainBuilder

@CommandSetting(commandName = "开始新对话", commandInfo = "开始一个新的对话", enable = true)
class NewConversation : Command {
    override suspend fun handle(messageEvent: GroupMessageEvent, args: List<String>) {
        val messageChainBuilder = MessageChainBuilder()

        Chat.closeConversationByUser(messageEvent.sender)
        Chat.openNewConversation(messageEvent.sender)

        messageChainBuilder.append(At(messageEvent.sender))
        messageChainBuilder.append(" ")
        messageChainBuilder.append("已清空对话")
        messageEvent.sender.sendMessage(messageChainBuilder.build())
    }

    override suspend fun handle(messageEvent: FriendMessageEvent, args: List<String>) {
        val messageChainBuilder = MessageChainBuilder()

        Chat.closeConversationByUser(messageEvent.sender)
        Chat.openNewConversation(messageEvent.sender)

        messageChainBuilder.append("已清空对话")
        messageEvent.sender.sendMessage(messageChainBuilder.build())
    }

}
