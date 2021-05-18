package me.weiwen.moromoro.actions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.weiwen.moromoro.serializers.FormattedString

@Serializable
@SerialName("console-command")
data class ConsoleCommand(val command: String) : Action {
    override fun perform(ctx: Context): Boolean {
        val player = ctx.player
        val formatted = command.replace("%p", player.name)
        return player.server.dispatchCommand(player.server.consoleSender, formatted)
    }
}
