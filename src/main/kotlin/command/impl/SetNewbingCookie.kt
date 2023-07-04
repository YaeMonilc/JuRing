package cc.yaeko.command.impl

import cc.yaeko.newbing.Config
import command.Command
import command.CommandSetting
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent

@CommandSetting(commandName = "设置NewbingCookie", commandInfo = "设置Newbing Cookie", args = 1, argsInfo = "<Cookie>", enable = true)
class SetNewbingCookie : Command{
    override suspend fun handle(messageEvent: GroupMessageEvent, args: List<String>) {

    }

    override suspend fun handle(messageEvent: FriendMessageEvent, args: List<String>) {
        Config.cookie = args[1]
        messageEvent.sender.sendMessage("NewbingCookie 设置成功")
    }
}