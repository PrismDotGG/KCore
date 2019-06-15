package com.perkelle.dev.kcore.event

import com.google.common.reflect.ClassPath

fun registerListeners(pkg: String) =
    ClassPath.from(KEventExecutor.pl::class.java.classLoader)
        .getTopLevelClassesRecursive(pkg)
        .filter { it.packageName.startsWith(pkg) }
        .map(ClassPath.ClassInfo::load)
        .filter { it.interfaces.contains(KListener::class.java) }
        .filter(Class<*>::hasEmptyConstructor)
        .forEach { (it.newInstance() as KListener).register() }

private fun Class<*>.hasEmptyConstructor() =
        try {
            getDeclaredConstructor()
            true
        } catch(_: Exception) {
            false
        }