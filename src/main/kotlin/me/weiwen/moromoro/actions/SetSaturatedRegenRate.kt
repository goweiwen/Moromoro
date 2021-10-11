package me.weiwen.moromoro.actions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("set-saturated-regen-rate")
data class SetSaturatedRegenRate(val rate: Int = 80) : Action {
    override fun perform(ctx: Context): Boolean {
        val player = ctx.player ?: return false
        player.saturatedRegenRate = rate
        return true
    }
}
