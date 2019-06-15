package com.perkelle.dev.kcore.inventory.gui

import org.bukkit.inventory.Inventory

data class GUI(val inventory: Inventory, val allowClick: Boolean, val listenType: ListenType, val items: Map<Int, GUIItem>)