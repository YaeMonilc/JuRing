package cc.yaeko.iw233.manager

import cc.yaeko.iw233.Config
import cc.yaeko.util.HttpRequest
import java.io.InputStream

object RandomImageManager {
    fun random() : InputStream? {
        return HttpRequest.getImageForWeibo(Config.apiPath)
    }
}