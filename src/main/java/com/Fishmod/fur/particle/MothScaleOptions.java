package com.Fishmod.fur.particle;

import org.joml.Vector3f;

import com.Fishmod.fur.init.FURParticleRegistry;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;

/**
 * Particle options for {@code fur:moth_scale}: an END_ROD-styled particle whose
 * tint is supplied at spawn time through RGB + scale parameters, exactly like vanilla's
 * redstone dust ({@link net.minecraft.core.particles.DustParticleOptions}).
 *
 * <p>Reusing {@link DustParticleOptionsBase} gives us the color/scale fields plus the
 * network and command (de)serialization for free.
 */
public class MothScaleOptions extends DustParticleOptionsBase {
	public static final Codec<MothScaleOptions> CODEC = RecordCodecBuilder.create((inst) ->
		inst.group(
			ExtraCodecs.VECTOR3F.fieldOf("color").forGetter((opt) -> opt.color),
			Codec.FLOAT.fieldOf("scale").forGetter((opt) -> opt.scale)
		).apply(inst, MothScaleOptions::new));

	public static final ParticleOptions.Deserializer<MothScaleOptions> DESERIALIZER = new ParticleOptions.Deserializer<MothScaleOptions>() {
		public MothScaleOptions fromCommand(ParticleType<MothScaleOptions> type, StringReader reader) throws CommandSyntaxException {
			Vector3f color = DustParticleOptionsBase.readVector3f(reader);
			reader.expect(' ');
			float scale = reader.readFloat();
			return new MothScaleOptions(color, scale);
		}

		public MothScaleOptions fromNetwork(ParticleType<MothScaleOptions> type, FriendlyByteBuf buf) {
			return new MothScaleOptions(DustParticleOptionsBase.readVector3f(buf), buf.readFloat());
		}
	};

	public MothScaleOptions(Vector3f color, float scale) {
		super(color, scale);
	}

	public ParticleType<MothScaleOptions> getType() {
		return FURParticleRegistry.MOTH_SCALE.get();
	}
}
