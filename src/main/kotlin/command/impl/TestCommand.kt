package cc.yaeko.command.impl

import command.Command
import command.CommandSetting
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent

@CommandSetting(commandName = "测试命令", commandInfo = "测试用", args = 1, argsInfo = "<不知道有啥用>", enable = false, master = true)
class TestCommand : Command {
    override suspend fun handle(messageEvent: GroupMessageEvent, args: List<String>) {
        args.forEach {
            messageEvent.group.sendMessage(it)
        }
    }

    override suspend fun handle(messageEvent: FriendMessageEvent, args: List<String>) {

    }

}