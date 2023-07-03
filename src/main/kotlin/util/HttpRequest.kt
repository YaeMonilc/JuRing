package cc.yaeko.util

import cc.yaeko.newbing.Config
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.InputStream
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.TimeUnit

object HttpRequest {
    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved("192.168.43.1", 7890)))
        .connectTimeout(180, TimeUnit.SECONDS)
        .readTimeout(180, TimeUnit.SECONDS)
        .writeTimeout(180, TimeUnit.SECONDS)
        .build()

    fun getForBing(url : String) : String {
        val request : Request = Request.Builder()
            .url(url)
            .addHeader("Cookie", Config.cookie)
            .addHeader("Host", "www.bing.com")
            .addHeader(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36 Edg/113.0.1774.50"
            )
            .get()
            .build()
        val response = okHttpClient.newCall(request).execute()
        return response.body?.string() ?: ""
    }

    fun getImageForWeibo(url : String) : InputStream? {
        val request : Request = Request.Builder()
            .url(url)
            .addHeader("Referer", "https://weibo.com/")
            .get()
            .build()
        val response = okHttpClient.newCall(request).execute()
        return response.body?.byteStream()
    }
}