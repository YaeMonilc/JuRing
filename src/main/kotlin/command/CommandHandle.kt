package cc.yaeko.command

import cc.yaeko.Config
import command.Command
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.MessageChain

object CommandHandle {

    suspend fun runCommand(command : Command, messageEvent: GroupMessageEvent) {
        val args = getCommandSplit(messageEvent.message)
        command.handle(messageEvent, args)
    }

    suspend fun runCommand(command : Command, messageEvent: FriendMessageEvent) {
        val args = getCommandSplit(messageEvent.message)
        command.handle(messageEvent, args)
    }

    fun checkIsCommandMessage(messageChain: MessageChain) : Boolean {
        val message = messageChain.contentToString()
        if (!message.startsWith(Config.commandSign))
            return false
        return CommandManager.isContains(message.substring(1))
    }

    fun getCommandName(messageChain: MessageChain) = messageChain.contentToString().split(" ")[0].replace(Config.commandSign, "")

    fun getCommandSplit(messageChain: MessageChain) = messageChain.contentToString().split(" ").drop(1)

}