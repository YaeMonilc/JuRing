package cc.yaeko.event.manager

import cc.yaeko.event.enity.Event
import net.mamoe.mirai.event.events.GroupMessageEvent

object EventManager {
    private val eventAll : MutableMap<Long, MutableList<Event>> = mutableMapOf()

    fun checkEvent(id : Long) : Boolean{
        if (!eventAll.contains(id)){
            return false
        }
        val events = eventAll[id]!!.filter {
            it.groupId == id
        }
        return events.isNotEmpty()
    }

    fun getEvent(id : Long) : Event? {
        if (!eventAll.contains(id)){
            return null
        }
        val events = eventAll[id]!!.filter {
            it.groupId == id
        }
        if (events.isEmpty()){
            return null
        }
        return events.last()
    }

    fun eventComplete(id : Long) {
        if (!eventAll.contains(id)){
            return
        }
        val events = eventAll[id]!!.filter {
            it.groupId == id
        }
        if (events.isEmpty()){
            return
        }
        eventAll[id] = events.slice(0..events.size - 2).toMutableList()
    }

    fun registerEvent(event: Event, groupMessageEvent: GroupMessageEvent) {
        if (!eventAll.contains(groupMessageEvent.sender.id)){
            eventAll[groupMessageEvent.sender.id] = mutableListOf()
        }
        eventAll[groupMessageEvent.sender.id]!!.add(event)
    }

}