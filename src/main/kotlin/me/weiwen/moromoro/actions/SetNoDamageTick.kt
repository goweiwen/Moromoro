package me.weiwen.moromoro.actions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.LivingEntity

@Serializable
@SerialName("set-no-damage-tick")
data class SetNoDamageTick(val ticks: Int = 0) : Action {
    override fun perform(ctx: Context): Boolean {
        val entity = ctx.entity as? LivingEntity ?: ctx.player ?: return false

        entity.noDamageTicks = ticks
        entity.lastDamage = Double.MAX_VALUE

        return true
    }
}

