package me.weiwen.moromoro.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.potion.PotionEffectType

class PotionEffectTypeSerializer : KSerializer<PotionEffectType> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("PotionEffectType", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): PotionEffectType {
        val string = decoder.decodeString()
        return PotionEffectType.getByName(string) ?: PotionEffectType.SPEED
    }

    override fun serialize(encoder: Encoder, value: PotionEffectType) {
        val key = value.name
        return encoder.encodeString(key)
    }
}
