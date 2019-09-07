package com.perkelle.dev.kcore.config

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File

open class Konfig(val pl: Plugin) {

    private val file = File(pl.dataFolder, fileName)
    private val fileName: String
        get() = this::class.java.getAnnotation(FileName::class.java).name
    val _config: YamlConfiguration

    init {
        if(!pl.dataFolder.exists()) pl.dataFolder.mkdir()
        if(!file.exists()) createFromResource()
        _config = YamlConfiguration.loadConfiguration(file)
    }

    inline fun<reified T> to() = ConfigDeserializer().to<T>(_config.root ?: throw NullPointerException("Config root is null"))

    inline fun<reified T> getGeneric(key: String, default: T) = _config.get(key) as? T ?: default
    inline fun<reified T> getGenericOrNull(key: String) = _config.get(key) as? T?

    fun save() = _config.save(file)

    private fun createFromResource() {
        file.createNewFile()

        this::class.java.getResourceAsStream("/$fileName").use { inStream ->
            file.outputStream().use { outStream ->
                inStream.copyTo(outStream)
            }
        }
    }
}