package me.weiwen.moromoro

import me.weiwen.moromoro.hooks.EssentialsHook
import me.weiwen.moromoro.items.EquippedItemsManager
import me.weiwen.moromoro.blocks.BlockListener
import me.weiwen.moromoro.items.ItemListener
import me.weiwen.moromoro.recipes.RecipeListener
import me.weiwen.moromoro.managers.*
import me.weiwen.moromoro.projectiles.ItemProjectileManager
import me.weiwen.moromoro.resourcepack.ResourcePackManager
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Moromoro : JavaPlugin() {
    companion object {
        lateinit var plugin: Moromoro
            private set
    }

    var config: MoromoroConfig = parseConfig(this)

    val resourcePackManager: ResourcePackManager by lazy { ResourcePackManager(this) }
    val itemProjectileManager: ItemProjectileManager by lazy { ItemProjectileManager(this) }
    val equippedItemsManager: EquippedItemsManager by lazy { EquippedItemsManager(this) }
    val itemManager: ItemManager by lazy { ItemManager(this, equippedItemsManager) }
    val blockManager: BlockManager by lazy { BlockManager(this, itemManager) }
    val recipeManager: RecipeManager by lazy { RecipeManager(this) }

    val flyInClaimsManager: FlyInClaimsManager by lazy { FlyInClaimsManager(this) }
    val permanentPotionEffectManager: PermanentPotionEffectManager by lazy { PermanentPotionEffectManager(this) }
    val experienceBoostManager: ExperienceBoostManager by lazy { ExperienceBoostManager(this) }

    val essentialsHook: EssentialsHook by lazy { EssentialsHook(this) }

    override fun onLoad() {
        plugin = this
    }

    override fun onEnable() {
        equippedItemsManager.enable()
        itemManager.enable()
        blockManager.enable()
        if (server.pluginManager.getPlugin("Essentials") != null) {
            essentialsHook.register()
            recipeManager.enable()
        }
        experienceBoostManager.enable()
        permanentPotionEffectManager.enable()
        flyInClaimsManager.enable()
        itemProjectileManager.enable()
        resourcePackManager.enable()

        getCommand("pack")?.let {
            it.setExecutor { sender, _, _, _ ->
                if (sender is Player) {
                    resourcePackManager.send(sender)
                    true
                } else {
                    false
                }
            }
        }

        val command = getCommand("moromoro")
        command?.setExecutor { sender, _, _, args ->
            when (args[0]) {
                "rp" -> {
                    if (sender is Player) {
                        resourcePackManager.send(sender)
                        true
                    } else {
                        false
                    }
                }
                "debug" -> {
                    if (args.size == 1) {
                        sender.sendMessage(ChatColor.GOLD.toString() + "${itemManager.keys.size} items, ${recipeManager.recipes.size} recipes loaded.")
                        true
                    } else {
                        val template = itemManager.templates[args[1]]
                        if (template != null) {
                            sender.sendMessage(ChatColor.GOLD.toString() + "$template")
                            true
                        } else {
                            sender.sendMessage(ChatColor.GOLD.toString() + "No such item.")
                            false
                        }
                    }
                }
                "reload" -> {
                    equippedItemsManager.disable()
                    if (args.size == 1) {
                        config = parseConfig(this)
                        itemManager.load()
                        blockManager.load()
                        recipeManager.load()
                    } else {
                        when (args[1]) {
                            "config" -> parseConfig(this)
                            "items" -> itemManager.load()
                            "blocks" -> blockManager.load()
                            "recipes" -> recipeManager.load()
                            else -> {
                                config = parseConfig(this)
                                itemManager.load()
                                recipeManager.load()
                            }
                        }
                    }
                    equippedItemsManager.enable()
                    sender.sendMessage(ChatColor.GOLD.toString() + "Reloaded configuration!")
                    true
                }
                else -> false
            }
        }
        command?.setTabCompleter { _, _, _, args ->
            when (args.size) {
                0 -> listOf("reload", "rp")
                else -> listOf()
            }
        }

        // Hotfix: reload to allow recursive
        config = parseConfig(this)
        itemManager.load()
        recipeManager.load()

        logger.info("Moromoro is enabled")
    }

    override fun onDisable() {
        if (server.pluginManager.getPlugin("Essentials") != null) {
            essentialsHook.unregister()
        }

        resourcePackManager.disable()
        flyInClaimsManager.disable()
        permanentPotionEffectManager.disable()
        experienceBoostManager.disable()
        recipeManager.disable()
        blockManager.disable()
        itemManager.disable()
        equippedItemsManager.disable()

        logger.info("Moromoro is disabled")
    }
}
