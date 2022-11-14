package me.weiwen.moromoro.actions.mechanic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.weiwen.moromoro.actions.Action
import me.weiwen.moromoro.actions.Context
import me.weiwen.moromoro.extensions.spawnParticle
import org.bukkit.*
import org.bukkit.entity.Breedable

@Serializable
@SerialName("everstone")
object Everstone : Action {
    override fun perform(ctx: Context): Boolean {
        val entity = ctx.entity ?: return false

        if (entity is Breedable) {
            entity.ageLock = true
        }
        entity.spawnParticle(Particle.HEART, 2, 0.02)

        return true
    }
}

