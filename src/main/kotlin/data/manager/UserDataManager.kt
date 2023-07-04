package cc.yaeko.data.manager

import cc.yaeko.JuRing
import cc.yaeko.data.enity.UserData
import cc.yaeko.util.FileUtil
import cc.yaeko.util.Function.toJson
import java.io.File

object UserDataManager {
    fun getData(id: Long): UserData {
        val file = File(FileUtil.userDir, "/${id}.dat")
        if (!file.exists()) {
            val userData = UserData(id)
            FileUtil.writeFile(file, userData.toJson())
            return userData
        }
        return JuRing.gson.fromJson(FileUtil.readFile(file), UserData::class.java)
    }

    fun save(userData: UserData) {
        val file = File(FileUtil.userDir, "/${userData.id}.dat")
        FileUtil.writeFile(file, userData.toJson())
    }
}