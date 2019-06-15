package com.perkelle.dev.kcore.inventory.gui

import com.perkelle.dev.kcore.event.KListener
import com.perkelle.dev.kcore.event.listen
import com.perkelle.dev.kcore.inventory.invTitle
import org.bukkit.event.inventory.InventoryClickEvent

class GUIListener : KListener {

    companion object {
        val guis = mutableListOf<GUI>()
    }

    override fun register() {
        listen<InventoryClickEvent> {
            val inv = clickedInventory ?: return@listen

            for (gui in guis) {
                if ((gui.listenType == ListenType.OBJECT && inv == gui.inventory) || (gui.listenType == ListenType.TITLE && inv.invTitle == gui.inventory.invTitle)) {
                    if(!gui.allowClick) isCancelled = true
                    gui.items[slot]?.let(GUIItem::logic) // Run logic on the clicked item
                } else continue
            }
        }
    }
}