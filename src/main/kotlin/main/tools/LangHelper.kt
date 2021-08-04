package main.tools

import org.bukkit.configuration.file.FileConfiguration

class LangHelper(private val config: FileConfiguration) {
    fun get(sentence: LangStrings): String = config.getString(sentence.key) ?: "UNKNOWN KEY"
}