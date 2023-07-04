package cc.yaeko.command.impl

import cc.yaeko.event.enity.Event
import cc.yaeko.event.manager.EventManager
import command.Command
import command.CommandSetting
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent

@CommandSetting(commandName = "测试命令", commandInfo = "测试用", args = 0, argsInfo = "<不知道有啥用>", enable = true, master = true)
class TestCommand : Command {
    override suspend fun handle(messageEvent: GroupMessageEvent, args: List<String>) {

    }

    override suspend fun handle(messageEvent: FriendMessageEvent, args: List<String>) {

    }

}