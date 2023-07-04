package command

annotation class CommandSetting (
    val commandName : String,
    val commandInfo : String = "",
    val args : Int = 0,
    val argsInfo : String = "",
    val master : Boolean = false,
    val enable : Boolean = true
)
