package com.perkelle.dev.kcore.inventory.gui

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

data class GUIItem(val item: ItemStack, val logic: InventoryClickEvent.() -> Unit)