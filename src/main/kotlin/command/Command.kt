package command

import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent

interface Command {
    suspend fun handle(messageEvent: GroupMessageEvent, args: List<String>)
    suspend fun handle(messageEvent: FriendMessageEvent, args: List<String>)
}
