package com.bennyhuo.kotlin.reflections.eg.plugin2

import com.bennyhuo.kotlin.reflections.eg.plugincommon.Plugin

class PluginImpl2: Plugin{
    override fun start() {
        println("Plugin2: Start")
    }

    override fun stop() {
        println("Plugin2: Stop")
    }
}
