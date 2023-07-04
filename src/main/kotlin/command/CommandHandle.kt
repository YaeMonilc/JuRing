package cc.yaeko.command

import cc.yaeko.Config
import cc.yaeko.JuRing
import command.Command
import command.CommandSetting
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.MessageChain

object CommandHandle {

    suspend fun runCommand(messageEvent: GroupMessageEvent) {
        val args = CommandManager.getCommandArgs(messageEvent.message)
        val commandName = CommandManager.getCommandName(messageEvent.message)
        val command = CommandManager.getCommandByName(commandName) ?: return
        val commandSetting = CommandManager.getCommandSetting(command)

        if (!commandSetting.enable) {
            messageEvent.group.sendMessage("$commandName enable 状态为 false")
            return
        }
        if (commandSetting.master) {
            if (messageEvent.sender.id != Config.master) {
                messageEvent.group.sendMessage("$commandName master 状态为 true")
                return
            }
        }
        if (args.size != commandSetting.args) {
            messageEvent.group.sendMessage("$commandName ${commandSetting.argsInfo}")
            return
        }
        command.handle(messageEvent, args)
    }

    suspend fun runCommand(messageEvent: FriendMessageEvent) {
        val args = CommandManager.getCommandArgs(messageEvent.message)
        val commandName = CommandManager.getCommandName(messageEvent.message)
        val command = CommandManager.getCommandByName(commandName) ?: return
        val commandSetting = CommandManager.getCommandSetting(command)

        if (!commandSetting.enable) {
            messageEvent.sender.sendMessage("$commandName enable 状态为 false")
            return
        }
        if (commandSetting.master) {
            if (messageEvent.sender.id != Config.master) {
                messageEvent.sender.sendMessage("$commandName master 状态为 true")
                return
            }
        }
        if (args.size != commandSetting.args) {
            messageEvent.sender.sendMessage("$commandName ${commandSetting.argsInfo}")
            return
        }
        command.handle(messageEvent, args)
    }

}