package com.perkelle.dev.kcore.inventory

import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory

val Inventory.invTitle: String?
    get() = viewers
        .map { it.openInventory }
        .filterNot { it.type == InventoryType.CRAFTING }
        .filterNot { it.title.isEmpty() }
        .firstOrNull()
        ?.title