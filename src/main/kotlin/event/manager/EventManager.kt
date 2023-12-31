package cc.yaeko.event.manager

import cc.yaeko.event.enity.Event
import net.mamoe.mirai.event.events.GroupMessageEvent

object EventManager {
    private val eventAll : MutableMap<Long, MutableList<Event>> = mutableMapOf()

    fun checkEvent(id : Long, classifyId : Long) : Boolean{
        if (!eventAll.contains(id)){
            return false
        }
        val events = eventAll[id]!!.filter {
            it.classifyId == classifyId
        }
        return events.isNotEmpty()
    }

    fun getEvent(id : Long, classifyId: Long) : Event? {
        if (!eventAll.contains(id)){
            return null
        }
        val events = eventAll[id]!!.filter {
            it.classifyId == classifyId
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
            it.classifyId == id
        }
        if (events.isEmpty()){
            return
        }
        eventAll[id] = events.slice(0..events.size - 2).toMutableList()
    }

    fun registerEvent(event: Event, id: Long) {
        if (!eventAll.contains(id)){
            eventAll[id] = mutableListOf(event)
            return
        }
        eventAll[id]!!.add(event)
    }

}