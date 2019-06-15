package com.perkelle.dev.kcore.event

import org.bukkit.event.Event
import org.bukkit.event.EventPriority

inline fun<reified T: Event> listen(priority: EventPriority = EventPriority.NORMAL, noinline logic: T.() -> Unit) = KEventExecutor().listen(priority, logic)