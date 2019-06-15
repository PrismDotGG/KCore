package com.perkelle.dev.kcore.bootstrap

import com.perkelle.dev.kcore.event.KEventExecutor
import com.perkelle.dev.kcore.inventory.gui.GUIListener
import org.bukkit.plugin.Plugin

object KCore {
    fun bootstrap(pl: Plugin) {
        KEventExecutor.pl = pl
        GUIListener().register()
    }
}