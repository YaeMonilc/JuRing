package cc.yaeko.command.impl

import cc.yaeko.data.manager.UserDataManager
import cc.yaeko.event.enity.Event
import cc.yaeko.event.manager.EventManager
import command.Command
import command.CommandSetting
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageChainBuilder

@CommandSetting(commandName = "抽奖", commandInfo = "消耗一点💵抽奖", enable = true)
class Raffle : Command {
    override suspend fun handle(messageEvent: GroupMessageEvent, args: List<String>) {
        val userData = UserDataManager.getData(messageEvent.sender.id)
        var messageChain = MessageChainBuilder()
            .append(At(messageEvent.sender))
            .append(" ").append("\n")
            .append("---------提示---------").append("\n")
            .append("   ").append("是否消耗💵x50抽奖").append("\n")
            .append("----------------------").append("\n")
            .append("          ").append("YES").append("      ").append("NO")
            .build()
        messageEvent.group.sendMessage(messageChain)

        val event = Event(messageEvent.group.id)
        EventManager.registerEvent(event, messageEvent.sender.id)
        val input = event.subscribe(10000)
        if (input.isNullOrEmpty()) {
            messageChain = MessageChainBuilder()
                .append(At(messageEvent.sender))
                .append(" ")
                .append("超时无响应,已取消")
                .build()
            messageEvent.group.sendMessage(messageChain)
            return
        }
        when(input.contentToString()) {
            "YES" -> {
                val money = (0..500).random()
                userData.money += money - 50
                UserDataManager.save(userData)
                messageChain = MessageChainBuilder()
                    .append(At(messageEvent.sender))
                    .append(" ")
                    .append("获得: ").append("💵x$money")
                    .build()
                messageEvent.group.sendMessage(messageChain)
            }
            "NO" -> {
                messageChain = MessageChainBuilder()
                    .append(At(messageEvent.sender))
                    .append(" ")
                    .append("已取消")
                    .build()
                messageEvent.group.sendMessage(messageChain)
            }
        }

    }

    override suspend fun handle(messageEvent: FriendMessageEvent, args: List<String>) {

    }
}