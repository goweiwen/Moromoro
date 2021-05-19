package me.weiwen.moromoro.actions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("remove-item")
object RemoveItem : Action {
    override fun perform(ctx: Context): Boolean {
        ctx.removeItem = true
        return true
    }
}

