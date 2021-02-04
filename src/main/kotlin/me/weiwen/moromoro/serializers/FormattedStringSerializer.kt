package me.weiwen.moromoro.serializers

import de.themoep.minedown.MineDown
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.md_5.bungee.api.chat.TextComponent

@Serializable(with = FormattedStringSerializer::class)
data class FormattedString(val value: String)

object FormattedStringSerializer : KSerializer<FormattedString> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("FormattedString", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): FormattedString {
        val string = decoder.decodeString()

        val components = MineDown.parse(string)
        val value = TextComponent.toLegacyText(*components)

        return FormattedString(value)
    }

    override fun serialize(encoder: Encoder, value: FormattedString) {
        val components = TextComponent.fromLegacyText(value.value)
        val string = MineDown.stringify(components)

        return encoder.encodeString(string)
    }
}
