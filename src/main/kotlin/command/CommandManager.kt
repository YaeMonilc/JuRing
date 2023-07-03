package cc.yaeko.command

import cc.yaeko.JuRing
import cc.yaeko.util.ClassUtil
import command.Command
import command.CommandSetting

object CommandManager {
    private val commandList : MutableList<Command> = mutableListOf()

    fun init() {
        val classByAnnotation = ClassUtil.getClassByAnnotation(CommandSetting::class.java)
        classByAnnotation.forEach {
            commandList.add(it.getDeclaredConstructor().newInstance() as Command)
        }
        commandList.forEach {
            JuRing.logger.error(it.javaClass.name)
        }
    }

    fun getCommandByName(commandName : String) : Command {
        return commandList.filter {
            it.javaClass.getAnnotation(CommandSetting::class.java).commandName == commandName
        }[0]
    }

    fun isContains(commandName : String): Boolean {
        return commandList.isNotEmpty() && commandList.any {
            it.javaClass.getAnnotation(CommandSetting::class.java).commandName == commandName
        }
    }

}