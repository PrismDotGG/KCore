package com.perkelle.dev.kcore.event

import org.bukkit.event.Cancellable

fun Cancellable.cancelIf(predicate: () -> Boolean): Boolean {
    val cancel = predicate()
    if(cancel) this.isCancelled = true
    return cancel
}