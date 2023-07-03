package cc.yaeko.command.impl

import command.Command
import command.CommandSetting
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent

@CommandSetting(commandName = "测试命令", commandInfo = "测试用", args = 0, enable = true)
class TestCommand : Command {
    override suspend fun handle(messageEvent: GroupMessageEvent, args: List<String>) {
        messageEvent.group.sendMessage(messageEvent.message.contentToString())
    }

    override suspend fun handle(messageEvent: FriendMessageEvent, args: List<String>) {

    }

}