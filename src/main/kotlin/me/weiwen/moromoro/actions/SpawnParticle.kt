package me.weiwen.moromoro.actions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.weiwen.moromoro.extensions.playSoundAt
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.util.Vector
import kotlin.math.PI

@Serializable
@SerialName("spawn-particle")
data class SpawnParticle(
    val particle: Particle,
    val x: Double = 0.0,
    val y: Double = 0.0,
    val z: Double = 0.0,
    val count: Int = 1,
    @SerialName("offset-x")
    val offsetX: Double = 0.0,
    @SerialName("offset-y")
    val offsetY: Double = 0.0,
    @SerialName("offset-z")
    val offsetZ: Double = 0.0,
    val extra: Double = 0.0,
) : Action {
    override fun perform(ctx: Context): Boolean {
        val player = ctx.player ?: return false

        val vec = Vector(x, y, z)
        vec.rotateAroundY(-player.location.yaw.toDouble() * PI / 180)

        val location = player.location.add(vec)

        player.world.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, extra)
        return true
    }
}
