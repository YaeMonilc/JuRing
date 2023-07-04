package cc.yaeko.data.enity

class UserData (
    val id : Long,
    var money : Long = 0,
    var level : Long = 0,
    val singIn : MutableList<String> = mutableListOf()
)