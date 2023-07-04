package cc.yaeko.event.enity

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class Event(
    val groupId : Long
) {
    private var value : String = ""

    suspend fun subscribe(timeout : Int) : String? {
        var time = 0
        runBlocking {
            while (value == "" && time <= timeout){
                delay(1000)
                time += 1000
            }
            return@runBlocking
        }
        return if (value == "") null else value
    }

    fun response(value : String) {
        this.value = value
    }
}