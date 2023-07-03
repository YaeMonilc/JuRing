package cc.yaeko.util

import cc.yaeko.JuRing

object Function {
    fun Any.toJson() : String {
        return JuRing.gson.toJson(this)
    }
}