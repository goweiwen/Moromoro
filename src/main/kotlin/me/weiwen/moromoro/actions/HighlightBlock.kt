@file:UseSerializers(MaterialSerializer::class)

package me.weiwen.moromoro.actions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import me.weiwen.moromoro.extensions.*
import me.weiwen.moromoro.serializers.MaterialSerializer

@Serializable
@SerialName("highlight-block")
data class HighlightBlock(val color: Long, val duration: Int = 250) : Action {
    private val colorUint = color.toUInt()

    override fun perform(ctx: Context): Boolean {
        val player = ctx.player ?: return false
        val block = ctx.block ?: return false
        val face = ctx.blockFace ?: return false

        val location = block.getRelative(face).location

        player.highlightBlock(location, colorUint, duration)

        return true
    }
}
