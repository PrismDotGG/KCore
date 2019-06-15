package com.perkelle.dev.kcore.event

import org.bukkit.Bukkit
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.Plugin

class KEventExecutor : EventExecutor, Listener {

    companion object {
        lateinit var pl: Plugin
    }

    val listeners = mutableMapOf<Class<out Event>, MutableList<EventListener<Event>>>()

    override fun execute(listener: Listener, e: Event) {
        if(listener !== this) return
        val eventListeners = listeners[e::class.java] ?: return

        if(e is Cancellable && e.isCancelled || eventListeners.isEmpty()) return

        eventListeners.forEach {
            it(e)
        }
    }

    @Suppress("UNCHECKED_CAST")
    inline fun<reified T: Event> listen(priority: EventPriority = EventPriority.NORMAL, noinline block: T.() -> Unit) {
        var eventListeners = listeners[T::class.java]

        if(eventListeners == null) {
            Bukkit.getServer().pluginManager.registerEvent(T::class.java, this, priority, this, pl)
            eventListeners = mutableListOf()
        }

        eventListeners.add(EventListener(block as Event.() -> Unit))
        listeners[T::class.java] = eventListeners
    }
}