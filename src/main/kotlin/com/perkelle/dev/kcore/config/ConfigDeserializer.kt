package com.perkelle.dev.kcore.config

import org.bukkit.configuration.ConfigurationSection

class ConfigDeserializer {

    val builtIn = listOf(
        Int::class.java,
        String::class.java,
        Long::class.java,
        Boolean::class.java,
        Short::class.java,
        Double::class.java,
        Float::class.java,
        List::class.java
    )

    inline fun<reified T> to(raw: ConfigurationSection) = to(raw, T::class.java)

    fun<T> to(raw: ConfigurationSection, clazz: Class<T>): T {
        val instance = clazz.newInstance()

        clazz.declaredFields.forEach { field ->
            val name =
                if(field.isAnnotationPresent(FieldName::class.java)) field.getAnnotation(FieldName::class.java).name
                else field.name

            if(builtIn.contains(field.type)) {
                field.set(instance, raw.get(name))
            } else {
                field.set(instance, to(raw.getConfigurationSection(name) ?: return@forEach, field.type))
            }
        }

        return instance
    }
}