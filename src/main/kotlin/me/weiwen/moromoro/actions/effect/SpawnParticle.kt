package me.weiwen.moromoro.actions.effect

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.weiwen.moromoro.actions.Action
import me.weiwen.moromoro.actions.Context
import me.weiwen.moromoro.actions.LocationSelector
import me.weiwen.moromoro.actions.PlayerLocationSelector
import org.bukkit.Particle
import org.bukkit.util.Vector
import kotlin.math.PI

@Serializable
@SerialName("spawn-particle")
data class SpawnParticle(
    val location: LocationSelector = PlayerLocationSelector,
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
        val entity = ctx.projectile ?: ctx.entity ?: ctx.player ?: return false

        val vec = Vector(x, y, z)
        vec.rotateAroundY(-entity.location.yaw.toDouble() * PI / 180)

        val location = entity.location.add(vec)

        entity.world.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, extra)
        return true
    }
}
