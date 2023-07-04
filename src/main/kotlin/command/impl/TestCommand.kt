package cc.yaeko.command.impl

import cc.yaeko.event.enity.Event
import cc.yaeko.event.manager.EventManager
import command.Command
import command.CommandSetting
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent

@CommandSetting(commandName = "测试命令", commandInfo = "测试用", args = 0, argsInfo = "<不知道有啥用>", enable = true, master = false)
class TestCommand : Command {
    override suspend fun handle(messageEvent: GroupMessageEvent, args: List<String>) {
        messageEvent.group.sendMessage("请输入文字")
        val event = Event(messageEvent.sender.id)
        EventManager.registerEvent(event, messageEvent.sender.id)
        val s = event.subscribe(5000)
        if (s == null) {
            messageEvent.group.sendMessage("已过期")
            return
        }
        messageEvent.group.sendMessage(s)
    }

    override suspend fun handle(messageEvent: FriendMessageEvent, args: List<String>) {

    }

}