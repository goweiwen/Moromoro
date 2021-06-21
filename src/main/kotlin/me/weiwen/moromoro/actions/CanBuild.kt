package me.weiwen.moromoro.actions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.weiwen.moromoro.extensions.canBuildAt

@Serializable
@SerialName("can-build")
object CanBuild : Action {
    override fun perform(ctx: Context): Boolean {
        val player = ctx.player ?: return false
        val loc = ctx.block?.location ?: return false
        return player.canBuildAt(loc)
    }
}

