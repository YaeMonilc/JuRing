package cc.yaeko.util

import cc.yaeko.JuRing
import net.mamoe.mirai.console.plugin.id
import net.mamoe.mirai.console.plugin.name
import okio.ByteString.Companion.readByteString
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object FileUtil {
    val rootDir = File("./data/${JuRing.id}.${JuRing.name}/")
    val userDir = File(rootDir, "/user/")
    init {
        if (!rootDir.exists())
            rootDir.mkdirs()
        if (!userDir.exists())
            userDir.mkdirs()
    }

    fun writeFile(file: File, string: String) {
        if (!file.exists()) {
            file.createNewFile()
        }
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(string.toByteArray())
        fileOutputStream.flush()
        fileOutputStream.close()
    }

    fun readFile(file: File) : String {
        if (!file.exists()) {
            return ""
        }
        return String(file.readBytes())
    }
}