package cc.yaeko.event.enity

import cc.yaeko.event.manager.EventManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.message.data.MessageChain

class Event(
    val classifyId : Long
) {
    private var value : MessageChain? = null

    suspend fun subscribe(timeout : Int) : MessageChain? {
        var time = 0
        runBlocking {
            while (value == null && time <= timeout){
                delay(1000)
                time += 1000
            }
            return@runBlocking
        }
        if (time >= timeout) {
            EventManager.eventComplete(classifyId)
            return null
        }
        return value
    }

    fun response(value : MessageChain) {
        this.value = value
    }
}