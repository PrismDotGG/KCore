package com.perkelle.dev.kcore.inventory.gui

import com.perkelle.dev.kcore.chat.colour
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun gui(block: GUIBuilder.() -> Unit) = GUIBuilder().also(block).build()

class GUIBuilder {

    private val items = mutableMapOf<Int, GUIItem>()

    var title = "Inventory"
    var rows = 1

    var registerListener = true
    var allowClick = false
    var listenType = ListenType.OBJECT

    fun addItem(row: Int, column: Int, item: ItemStack, logic: InventoryClickEvent.() -> Unit = {}) =
            addItem(row * 9 + column, item, logic)

    fun addItem(slot: Int, item: ItemStack, logic: InventoryClickEvent.() -> Unit = {}) {
        items[slot] = GUIItem(item, logic)
    }

    fun fill(item: ItemStack, logic: InventoryClickEvent.() -> Unit = {}) {
        for(i in 0 until rows * 9) {
            if(items[i] == null) {
                items[i] = GUIItem(item, logic)
            }
        }
    }

    fun build(): GUI {
        val inv = Bukkit.createInventory(null, rows * 9, title.colour())

        items.forEach { slot, item -> inv.setItem(slot, item.item) }

        val gui = GUI(
            inv,
            allowClick,
            listenType,
            items
        )

        if(registerListener) GUIListener.guis.add(gui)

        return gui
    }
}