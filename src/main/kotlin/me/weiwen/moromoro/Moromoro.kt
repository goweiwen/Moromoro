package me.weiwen.moromoro

import me.weiwen.moromoro.hooks.EssentialsHook
import me.weiwen.moromoro.listeners.PlayerListener
import me.weiwen.moromoro.listeners.RecipeListener
import me.weiwen.moromoro.managers.*
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin

class Moromoro: JavaPlugin() {
    companion object {
        lateinit var plugin: Moromoro
            private set
    }

    var config: MoromoroConfig = parseConfig(this)

    val equippedItemsManager: EquippedItemsManager by lazy { EquippedItemsManager(this) }
    val flyItemsManager: FlyInClaimsManager by lazy { FlyInClaimsManager(this) }
    val itemManager: ItemManager by lazy { ItemManager(this) }
    val recipeManager: RecipeManager by lazy { RecipeManager(this) }
    val permanentPotionEffectManager: PermanentPotionEffectManager by lazy { PermanentPotionEffectManager(this) }

    val essentialsHook: EssentialsHook by lazy { EssentialsHook(this) }

    override fun onLoad() {
        plugin = this
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(PlayerListener(this), this)
        server.pluginManager.registerEvents(RecipeListener(this), this)

        itemManager.enable()
        if (server.pluginManager.getPlugin("Essentials") != null) {
            essentialsHook.register()
            recipeManager.enable()
        }
        permanentPotionEffectManager.enable()
        flyItemsManager.enable()
        equippedItemsManager.enable()

        val command = getCommand("moromoro")
        command?.setExecutor { sender, _, _, args ->
            when (args[0]) {
                "reload" -> {
                    if (args.size == 1) {
                        config = parseConfig(this)
                        itemManager.load()
                        recipeManager.load()
                    } else {
                        when (args[1]) {
                            "config" -> parseConfig(this)
                            "items" -> itemManager.load()
                            "recipes" -> recipeManager.load()
                            else -> {
                                config = parseConfig(this)
                                itemManager.load()
                                recipeManager.load()
                            }
                        }
                    }
                    sender.sendMessage(ChatColor.GOLD.toString() + "Reloaded configuration!")
                    true
                }
                else -> false
            }
        }
        command?.setTabCompleter { _, _, _, args ->
            when (args.size) {
                0 -> listOf("reload")
                else -> listOf()
            }
        }

        logger.info("Moromoro is enabled")
    }

    override fun onDisable() {
        if (server.pluginManager.getPlugin("Essentials") != null) {
            essentialsHook.unregister()
        }

        equippedItemsManager.disable()
        flyItemsManager.disable()
        permanentPotionEffectManager.disable()
        recipeManager.disable()
        itemManager.disable()

        logger.info("Moromoro is disabled")
    }
}
