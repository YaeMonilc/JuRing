package cc.yaeko.command

import cc.yaeko.Config
import cc.yaeko.JuRing
import cc.yaeko.util.ClassUtil
import command.Command
import command.CommandSetting
import net.mamoe.mirai.message.data.MessageChain

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

    fun getCommandSetting(command: Command) : CommandSetting {
        return command.javaClass.getAnnotation(CommandSetting::class.java)
    }

    fun getCommandByName(commandName : String) : Command? {
        val commands = commandList.filter {
            it.javaClass.getAnnotation(CommandSetting::class.java).commandName == commandName
        }
        return if (commands.isEmpty()) null else commands[0]
    }

    fun isContains(commandName : String): Boolean {
        return commandList.isNotEmpty() && commandList.any {
            it.javaClass.getAnnotation(CommandSetting::class.java).commandName == commandName
        }
    }

    fun checkIsCommandMessage(messageChain: MessageChain) : Boolean {
        val message = messageChain.contentToString()
        if (!message.startsWith(Config.commandSign))
            return false
        return isContains(getCommandName(messageChain))
    }

    fun getCommandName(messageChain: MessageChain) = messageChain.contentToString().split(" ")[0].replace(Config.commandSign, "")

    fun getCommandArgs(messageChain: MessageChain) = messageChain.contentToString().split(" ").drop(1)

}