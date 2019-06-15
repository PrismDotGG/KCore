package com.perkelle.dev.kcore.event

import org.bukkit.event.Event

// This is the class that actually wraps the event handler
class EventListener<in T: Event>(listener: (T) -> Unit): (T) -> Unit by listener
