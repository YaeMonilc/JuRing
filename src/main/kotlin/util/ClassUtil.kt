package cc.yaeko.util

import cc.yaeko.JuRing
import net.mamoe.mirai.console.plugin.id
import net.mamoe.mirai.console.plugin.jvm.JvmPluginClasspath
import java.util.jar.JarFile

object ClassUtil {
    private lateinit var jvmPluginClasspath: JvmPluginClasspath
    fun init(jvmPluginClasspath: JvmPluginClasspath){
        this.jvmPluginClasspath = jvmPluginClasspath
    }

    fun getClassByAnnotation(clazz: Class<out Annotation>) : List<Class<*>>{
        val jarFile = JarFile(jvmPluginClasspath.pluginFile)
        val entries = jarFile.entries()
        val packagesName = JuRing.id.replace(".", "/")

        val mutableList = mutableListOf<Class<*>>()

        while (entries.hasMoreElements()) {
            val element = entries.nextElement()
            val elementName = element.name

            if (!elementName.contains(packagesName) || !elementName.endsWith(".class"))
                continue

            val c = Class.forName(elementName.replace("/", ".").replace(".class", ""))
            if (c.isAnnotationPresent(clazz))
                mutableList.add(c)
        }
        return mutableList
    }
}