package cc.yaeko.command.impl

import cc.yaeko.JuRing
import cc.yaeko.data.manager.UserDataManager
import command.Command
import command.CommandSetting
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageChainBuilder
import java.text.SimpleDateFormat
import java.util.*

@CommandSetting(commandName = "签到", commandInfo = "每天只能签到一次", enable = true)
class SingIn : Command {
    override suspend fun handle(messageEvent: GroupMessageEvent, args: List<String>) {
        val userData = UserDataManager.getData(messageEvent.sender.id)
        val date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        val messageChain = MessageChainBuilder()

        if(userData.singIn.contains(date)) {
            messageChain
                .append(At(messageEvent.sender))
                .append(" ")
                .append("你今天已经签到过了")
            messageEvent.group.sendMessage(messageChain.build())
            return
        }

        val money = (0..300).random()
        userData.money += money
        userData.level += 3
        userData.singIn.add(date)
        UserDataManager.save(userData)
        messageChain
            .append(At(messageEvent.sender))
            .append(" ").append("\n")
            .append("签到成功, 获得:").append("\n")
            .append("💵x$money").append("\n")
            .append("🧾+3")
        messageEvent.group.sendMessage(messageChain.build())
    }

    override suspend fun handle(messageEvent: FriendMessageEvent, args: List<String>) {
        val userData = UserDataManager.getData(messageEvent.sender.id)
        val date = SimpleDateFormat("yyyy-mm-dd").format(Date())
        val messageChain = MessageChainBuilder()

        if(userData.singIn.contains(date)) {
            messageChain.append(At(messageEvent.sender))
                .append(" ")
                .append("你今天已经签到过了")
            messageEvent.friend.sendMessage(messageChain.build())
            return
        }

        val money = (0..300).random()
        userData.money += money
        userData.level += 3
        userData.singIn.add(date)
        UserDataManager.save(userData)
        messageChain.append(At(messageEvent.sender))
            .append(" ").append("\n")
            .append("签到成功, 获得:").append("\n")
            .append("💵x$money").append("\n")
            .append("🧾+3")
        messageEvent.friend.sendMessage(messageChain.build())
    }

}