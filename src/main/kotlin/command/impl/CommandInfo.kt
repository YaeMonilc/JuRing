package cc.yaeko.command.impl

import cc.yaeko.command.CommandManager
import command.Command
import command.CommandSetting
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent

@CommandSetting(commandName = "命令详情", commandInfo = "获取命令详情", args = 1, argsInfo = "<命令名称>", enable = true)
class CommandInfo : Command {
    override suspend fun handle(messageEvent: GroupMessageEvent, args: List<String>) {
        val command = CommandManager.getCommandByName(args[0])
        if (command == null) {
            messageEvent.group.sendMessage("暂无此命令")
        }
        val commandSetting = CommandManager.getCommandSetting(command!!)
        messageEvent.group.sendMessage("${commandSetting.commandName} ${commandSetting.argsInfo}\n${commandSetting.commandInfo}")
    }

    override suspend fun handle(messageEvent: FriendMessageEvent, args: List<String>) {
        val command = CommandManager.getCommandByName(args[0])
        if (command == null) {
            messageEvent.sender.sendMessage("暂无此命令")
        }
        val commandSetting = CommandManager.getCommandSetting(command!!)
        messageEvent.sender.sendMessage("${commandSetting.commandName} ${commandSetting.argsInfo}\n${commandSetting.commandInfo}")
    }
}