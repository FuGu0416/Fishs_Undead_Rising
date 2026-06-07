package com.Fishmod.fur.events;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURBiomesRegistry;

import net.minecraft.client.Camera;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Client-side fog tweaks for the Luminous Undergrove biome: a short, close fog wall
 * tinted teal so the cave reads as a misty grove.
 *
 * <p>The transition is <em>blended</em>, not switched: instead of a hard "am I in the
 * biome" test (which snaps at the border), we Gaussian-sample the biomes around the
 * camera the same way vanilla {@code FogRenderer} samples biome fog colours, producing a
 * 0..1 weight that eases as you cross the edge. Both the fog colour and the near/far
 * planes are then lerped by that weight, so the change lines up with vanilla's own colour
 * blend. Weight 0 leaves vanilla fog completely untouched.
 */
@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class FURFogHandler {

    private static final Vec3 UNIT = new Vec3(1.0D, 1.0D, 1.0D);

    // ── Tunables ─────────────────────────────────────────────────────────────
    // Fog colour (teal mist). Lower these toward 0 for a darker fog, which makes the
    // emissive grove blocks contrast/"glow" harder against it.
    private static final double FOG_RED = 0.05D;
    private static final double FOG_GREEN = 0.18D;
    private static final double FOG_BLUE = 0.22D;
    // Fog planes. NEAR is how close the mist starts: anything nearer than this renders
    // fog-free, so raising NEAR lets the glow blocks around you stay vivid (at the cost of a
    // less dense close mist). FAR is where the mist becomes fully opaque.
    private static final double FOG_NEAR = 8.0D;
    private static final double FOG_FAR = 48.0D;

    // Cache the weight for the second event of the same frame (RenderFog + ComputeFogColor
    // both fire per frame with the same camera position).
    private static Vec3 cachedPos = null;
    private static double cachedWeight = 0.0D;

    /**
     * Fraction (0..1) of the Gaussian-weighted neighbourhood around the camera that is
     * Luminous Undergrove. Mirrors vanilla's fog-colour sampling (quart coords, scale 0.25)
     * so the fog transition matches vanilla's colour transition exactly.
     */
    private static double undergroveWeight(Camera camera) {
        Entity entity = camera.getEntity();
        if (entity == null) {
            return 0.0D;
        }
        Vec3 pos = camera.getPosition();
        if (pos.equals(cachedPos)) {
            return cachedWeight;
        }
        Level level = entity.level();
        double w = CubicSampler.gaussianSampleVec3(
                pos.scale(0.25D),
                (x, y, z) -> level.getBiomeManager().getNoiseBiomeAtQuart(x, y, z)
                        .is(FURBiomesRegistry.LUMINOUS_UNDERGROVE) ? UNIT : Vec3.ZERO).x;
        cachedPos = pos;
        cachedWeight = w;
        return w;
    }

    /** Pull the fog in toward near 4 / far 48, eased by the biome weight. Must be cancelled to apply. */
    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        double w = undergroveWeight(event.getCamera());
        if (w <= 0.0D) {
            return;
        }
        event.setNearPlaneDistance((float) Mth.lerp(w, event.getNearPlaneDistance(), FOG_NEAR));
        event.setFarPlaneDistance((float) Mth.lerp(w, event.getFarPlaneDistance(), FOG_FAR));
        event.setCanceled(true);
    }

    /** Tint the fog teal (0.05, 0.18, 0.22), eased from vanilla's computed colour by the biome weight. */
    @SubscribeEvent
    public static void onFogColor(ViewportEvent.ComputeFogColor event) {
        double w = undergroveWeight(event.getCamera());
        if (w <= 0.0D) {
            return;
        }
        event.setRed((float) Mth.lerp(w, event.getRed(), FOG_RED));
        event.setGreen((float) Mth.lerp(w, event.getGreen(), FOG_GREEN));
        event.setBlue((float) Mth.lerp(w, event.getBlue(), FOG_BLUE));
    }
}
