package com.perkelle.dev.kcore.inventory.itemstack.dsl

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import com.perkelle.dev.kcore.chat.colour
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable

fun itemstack(block: DSLItemStackBuilder.() -> Unit) = DSLItemStackBuilder().also(block).getStack()

class DSLItemStackBuilder {

    private val enchantments = mutableMapOf<Enchantment, Int>()
    private var lore = mutableListOf<String>()
    private val flags = mutableListOf<ItemFlag>()
    private val attributes = ArrayListMultimap.create<Attribute, AttributeModifier>()

    lateinit var type: Material
    var amount = 1
    var damage = 0

    var name: String? = null
    var unbreakable = false

    fun Enchantment.apply(level: Int) {
        enchantments[this] = level
    }

    fun setLore(lore: List<String>) {
        this.lore = lore.map(String::colour).toMutableList()
    }

    fun addLore(line: String) = lore.add(line.colour())
    fun addLore(vararg lines: String) = lore.addAll(lines.map(String::colour))

    fun ItemFlag.apply() = flags.add(this)
    fun Collection<ItemFlag>.apply() = flags.addAll(this)

    infix fun Attribute.apply(modifier: AttributeModifier) = attributes.put(this, modifier)

    fun getStack(): ItemStack {
        val stack = ItemStack(type)

        stack.amount = 1
        stack.addUnsafeEnchantments(enchantments)

        stack.itemMeta = stack.itemMeta?.apply {
            name?.let { setDisplayName(it.colour()) }
            this@apply.lore = this@DSLItemStackBuilder.lore
            addItemFlags(*flags.toTypedArray())
            isUnbreakable = unbreakable
            attributeModifiers = attributes
            (this as Damageable).damage = damage
        }

        return stack
    }
}