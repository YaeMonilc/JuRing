package cc.yaeko.command.impl

import cc.yaeko.iw233.manager.RandomImageManager
import command.Command
import command.CommandSetting
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent

@CommandSetting(commandName = "随机图片", commandInfo = "获取随机图片", enable = true)
class RandomImage : Command{
    override suspend fun handle(messageEvent: GroupMessageEvent, args: List<String>) {
        messageEvent.group.sendImage(RandomImageManager.random()!!)
    }

    override suspend fun handle(messageEvent: FriendMessageEvent, args: List<String>) {
        messageEvent.sender.sendImage(RandomImageManager.random()!!)
    }

}