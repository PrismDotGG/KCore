package com.perkelle.dev.kcore.inventory.itemstack.dsl

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun itemstack(block: DSLItemStackBuilder.() -> Unit) = DSLItemStackBuilder().also(block)

class DSLItemStackBuilder {

    val type: Material

    fun getStack(): ItemStack {

    }
}