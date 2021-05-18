package me.weiwen.moromoro.actions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.weiwen.moromoro.removePermanentPotionEffects

@Serializable
@SerialName("remove-permanent-potion-effect")
data class RemovePermanentPotionEffect(val key: String) : Action {
    override fun perform(ctx: Context): Boolean {
        val player = ctx.player
        player.removePermanentPotionEffects(key)
        return true
    }
}
