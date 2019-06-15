package com.perkelle.dev.kcore.chat

import org.bukkit.ChatColor

fun String.colour() = ChatColor.translateAlternateColorCodes('&', this)

fun String.strip() = ChatColor.stripColor(this)