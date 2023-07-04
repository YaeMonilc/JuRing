package cc.yaeko.command.impl

import cc.yaeko.JuRing
import cc.yaeko.iw233.manager.RandomImageManager
import command.Command
import command.CommandSetting
import net.mamoe.mirai.console.plugin.*
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.MessageChainBuilder
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage

@CommandSetting(commandName = "关于", commandInfo = "获取机器人信息", enable = true)
class About : Command {

    override suspend fun handle(messageEvent: GroupMessageEvent, args: List<String>) {
        val messageChain = MessageChainBuilder()
            .append(RandomImageManager.random()!!.uploadAsImage(messageEvent.group)).append("\n")
            .append("Name: ").append(JuRing.name).append("\n")
            .append("Id: ").append(JuRing.id).append("\n")
            .append("Version: ").append(JuRing.version.toString()).append("\n")
            .append("Author: ").append(JuRing.author).append("\n")
            .append("Info: ").append(JuRing.info).append("\n")
        messageEvent.group.sendMessage(messageChain.build())
    }

    override suspend fun handle(messageEvent: FriendMessageEvent, args: List<String>) {
        val messageChain = MessageChainBuilder()
            .append(RandomImageManager.random()!!.uploadAsImage(messageEvent.sender)).append("\n")
            .append("Name: ").append(JuRing.name).append("\n")
            .append("Id: ").append(JuRing.id).append("\n")
            .append("Version: ").append(JuRing.version.toString()).append("\n")
            .append("Author: ").append(JuRing.author).append("\n")
            .append("Info: ").append(JuRing.info).append("\n")
        messageEvent.sender.sendMessage(messageChain.build())
    }
}