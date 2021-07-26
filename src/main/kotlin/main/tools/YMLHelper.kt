package main.tools

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File


class YMLHelper(fileName: String, plugin: JavaPlugin) : YamlConfiguration() {
    private val file: File

    init {
        val fileNameFull = "${fileName}.yml"

        file = File(plugin.dataFolder, fileNameFull)
        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.createNewFile()
        }

        load(file)
    }

    override fun set(path: String, value: Any?) {
        super.set(path, value)
        save()
    }

    fun save() = save(file)

    fun reload() = load(file)
}
