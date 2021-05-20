package me.weiwen.moromoro.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import me.weiwen.moromoro.Moromoro
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class ItemStackSerializer : KSerializer<ItemStack> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("ItemStack", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ItemStack {
        val string = decoder.decodeString()
        return Moromoro.plugin.essentialsHook.getItemStack(string) ?: ItemStack(Material.STICK)
    }

    override fun serialize(encoder: Encoder, value: ItemStack) {
        val string = Moromoro.plugin.essentialsHook.getName(value) ?: "stick"
        return encoder.encodeString(string)
    }
}
