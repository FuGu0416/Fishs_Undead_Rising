package com.Fishmod.mod_LavaCow.init;

import java.lang.reflect.Field;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.entities.BoneWormEntity;
import com.Fishmod.mod_LavaCow.entities.CactyrantEntity;
import com.Fishmod.mod_LavaCow.entities.FogletEntity;
import com.Fishmod.mod_LavaCow.entities.ForsakenEntity;
import com.Fishmod.mod_LavaCow.entities.GhoulEntity;
import com.Fishmod.mod_LavaCow.entities.GraveRobberEntity;
import com.Fishmod.mod_LavaCow.entities.LavaCowEntity;
import com.Fishmod.mod_LavaCow.entities.ParasiteEntity;
import com.Fishmod.mod_LavaCow.entities.PinguEntity;
import com.Fishmod.mod_LavaCow.entities.SkeletonKingEntity;
import com.Fishmod.mod_LavaCow.entities.SludgeLordEntity;
import com.Fishmod.mod_LavaCow.entities.UndeadSwineEntity;
import com.Fishmod.mod_LavaCow.entities.UndertakerEntity;
import com.Fishmod.mod_LavaCow.entities.VespaCocoonEntity;
import com.Fishmod.mod_LavaCow.entities.WendigoEntity;
import com.Fishmod.mod_LavaCow.entities.aquatic.UndeadFishEntity;
import com.Fishmod.mod_LavaCow.entities.aquatic.LampreyEntity;
import com.Fishmod.mod_LavaCow.entities.aquatic.PiranhaEntity;
import com.Fishmod.mod_LavaCow.entities.aquatic.SwarmerEntity;
import com.Fishmod.mod_LavaCow.entities.floating.AvatonEntity;
import com.Fishmod.mod_LavaCow.entities.floating.BansheeEntity;
import com.Fishmod.mod_LavaCow.entities.floating.FloatingMobEntity;
import com.Fishmod.mod_LavaCow.entities.floating.GraveRobberGhostEntity;
import com.Fishmod.mod_LavaCow.entities.floating.SeaHagEntity;
import com.Fishmod.mod_LavaCow.entities.floating.WraithEntity;
import com.Fishmod.mod_LavaCow.entities.flying.BeelzebubEntity;
import com.Fishmod.mod_LavaCow.entities.flying.EnigmothEntity;
import com.Fishmod.mod_LavaCow.entities.flying.FlyingMobEntity;
import com.Fishmod.mod_LavaCow.entities.flying.GhostRayEntity;
import com.Fishmod.mod_LavaCow.entities.flying.PteraEntity;
import com.Fishmod.mod_LavaCow.entities.flying.VespaEntity;
import com.Fishmod.mod_LavaCow.entities.flying.WarpedFireflyEntity;
import com.Fishmod.mod_LavaCow.entities.projectiles.AcidJetEntity;
import com.Fishmod.mod_LavaCow.entities.projectiles.BasicBombEntity;
import com.Fishmod.mod_LavaCow.entities.projectiles.CactusThornEntity;
import com.Fishmod.mod_LavaCow.entities.projectiles.DeathCoilEntity;
import com.Fishmod.mod_LavaCow.entities.projectiles.FlameJetEntity;
import com.Fishmod.mod_LavaCow.entities.projectiles.FURArrowEntity;
import com.Fishmod.mod_LavaCow.entities.projectiles.MothScalesEntity;
import com.Fishmod.mod_LavaCow.entities.projectiles.PiranhaLauncherEntity;
import com.Fishmod.mod_LavaCow.entities.projectiles.SandBurstEntity;
import com.Fishmod.mod_LavaCow.entities.projectiles.SapJetEntity;
import com.Fishmod.mod_LavaCow.entities.projectiles.SludgeJetEntity;
import com.Fishmod.mod_LavaCow.entities.projectiles.WarSmallFireballEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.CactoidEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.GhostSwarmerEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.LilSludgeEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.MimicEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.RavenEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.SalamanderEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.ScarabEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.ScarecrowEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.WetaEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.WispEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.unburied.FrigidEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.unburied.MummyEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.unburied.MycosisEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.unburied.UnburiedEntity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.fish.CodEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.gen.Heightmap;

@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FUREntityRegistry {
	public static final EntityType<LavaCowEntity> LAVACOW = registerEntity(EntityType.Builder.of(LavaCowEntity::new, EntityClassification.CREATURE).sized(0.9F, 1.4F).fireImmune(), "lavacow");
	public static final EntityType<MycosisEntity> MYCOSIS = registerEntity(EntityType.Builder.of(MycosisEntity::new, EntityClassification.MONSTER).sized(1.0F, 1.95F), "mycosis");
	public static final EntityType<ParasiteEntity> PARASITE = registerEntity(EntityType.Builder.of(ParasiteEntity::new, EntityClassification.MONSTER).sized(0.8F, 0.3F), "parasite");
	public static final EntityType<FogletEntity> FOGLET = registerEntity(EntityType.Builder.of(FogletEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.2F), "foglet");
	public static final EntityType<FogletEntity> IMP = registerEntity(EntityType.Builder.of(FogletEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.5F).fireImmune(), "imp");
	public static final EntityType<FrigidEntity> FRIGID = registerEntity(EntityType.Builder.of(FrigidEntity::new, EntityClassification.MONSTER).sized(1.0F, 1.95F), "frigid");
	public static final EntityType<UndeadSwineEntity> UNDEADSWINE = registerEntity(EntityType.Builder.of(UndeadSwineEntity::new, EntityClassification.MONSTER).sized(1.6F, 1.8F), "undeadswine");
	public static final EntityType<SalamanderEntity> SALAMANDER = registerEntity(EntityType.Builder.of(SalamanderEntity::new, EntityClassification.MONSTER).sized(1.95F, 1.6F).fireImmune(), "salamander");
	public static final EntityType<WendigoEntity> WENDIGO = registerEntity(EntityType.Builder.of(WendigoEntity::new, EntityClassification.MONSTER).sized(1.6F, 2.6F), "wendigo");
	public static final EntityType<MimicEntity> MIMIC = registerEntity(EntityType.Builder.of(MimicEntity::new, EntityClassification.MONSTER).sized(1.0F, 1.0F), "mimic");
	public static final EntityType<SludgeLordEntity> SLUDGELORD = registerEntity(EntityType.Builder.of(SludgeLordEntity::new, EntityClassification.MONSTER).sized(2.2F, 3.7F), "sludgelord");
	public static final EntityType<LilSludgeEntity> LILSLUDGE = registerEntity(EntityType.Builder.of(LilSludgeEntity::new, EntityClassification.MONSTER).sized(1.0F, 2.0F), "lilsludge");
	public static final EntityType<RavenEntity> RAVEN = registerEntity(EntityType.Builder.of(RavenEntity::new, EntityClassification.CREATURE).sized(0.5F, 0.9F), "raven");
	public static final EntityType<RavenEntity> SEAGULL = registerEntity(EntityType.Builder.of(RavenEntity::new, EntityClassification.CREATURE).sized(0.5F, 0.9F), "seagull");
	public static final EntityType<PteraEntity> PTERA = registerEntity(EntityType.Builder.of(PteraEntity::new, EntityClassification.MONSTER).sized(1.6F, 0.8F), "ptera");
	public static final EntityType<VespaEntity> VESPA = registerEntity(EntityType.Builder.of(VespaEntity::new, EntityClassification.MONSTER).sized(1.6F, 1.0F), "vespa");
	public static final EntityType<ScarecrowEntity> SCARECROW = registerEntity(EntityType.Builder.of(ScarecrowEntity::new, EntityClassification.MONSTER).sized(0.8F, 3.0F), "scarecrow");
	public static final EntityType<VespaCocoonEntity> VESPACOCOON = registerEntity(EntityType.Builder.of(VespaCocoonEntity::new, EntityClassification.MONSTER).sized(0.8F, 1.0F), "vespacocoon");
	public static final EntityType<PiranhaEntity> PIRANHA = registerEntity(EntityType.Builder.of(PiranhaEntity::new, EntityClassification.WATER_AMBIENT).sized(0.7F, 0.5F), "piranha");
	public static final EntityType<SwarmerEntity> SWARMER = registerEntity(EntityType.Builder.of(SwarmerEntity::new, EntityClassification.WATER_AMBIENT).sized(1.0F, 0.8F), "swarmer");
	public static final EntityType<BoneWormEntity> BONEWORM = registerEntity(EntityType.Builder.of(BoneWormEntity::new, EntityClassification.MONSTER).sized(0.8F, 2.0F), "boneworm");
	public static final EntityType<PinguEntity> PINGU = registerEntity(EntityType.Builder.of(PinguEntity::new, EntityClassification.MONSTER).sized(0.5F, 0.8F), "pingu");
	public static final EntityType<UndertakerEntity> UNDERTAKER = registerEntity(EntityType.Builder.of(UndertakerEntity::new, EntityClassification.MONSTER).sized(1.8F, 2.4F), "undertaker");
	public static final EntityType<UnburiedEntity> UNBURIED = registerEntity(EntityType.Builder.of(UnburiedEntity::new, EntityClassification.MONSTER).sized(1.0F, 1.95F), "unburied");
	public static final EntityType<GhostRayEntity> GHOSTRAY = registerEntity(EntityType.Builder.of(GhostRayEntity::new, EntityClassification.MONSTER).sized(1.6F, 0.25F), "ghostray");
	public static final EntityType<BansheeEntity> BANSHEE = registerEntity(EntityType.Builder.of(BansheeEntity::new, EntityClassification.MONSTER).sized(0.75F, 1.75F), "banshee");
	public static final EntityType<WetaEntity> WETA = registerEntity(EntityType.Builder.of(WetaEntity::new, EntityClassification.MONSTER).sized(0.8F, 0.5F).immuneTo(Blocks.SWEET_BERRY_BUSH, Blocks.CACTUS), "weta");
	public static final EntityType<AvatonEntity> AVATON = registerEntity(EntityType.Builder.of(AvatonEntity::new, EntityClassification.MONSTER).sized(1.25F, 1.5F), "avaton");
	public static final EntityType<ForsakenEntity> FORSAKEN = registerEntity(EntityType.Builder.of(ForsakenEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.99F).fireImmune(), "forsaken");
	public static final EntityType<SkeletonKingEntity> SKELETONKING = registerEntity(EntityType.Builder.of(SkeletonKingEntity::new, EntityClassification.MONSTER).sized(1.25F, 3.1F).fireImmune(), "skeletonking");
	public static final EntityType<MummyEntity> MUMMY = registerEntity(EntityType.Builder.of(MummyEntity::new, EntityClassification.MONSTER).sized(1.0F, 1.95F), "mummy");
	public static final EntityType<CactyrantEntity> CACTYRANT = registerEntity(EntityType.Builder.of(CactyrantEntity::new, EntityClassification.MONSTER).sized(1.3F, 2.8F), "cactyrant");
	public static final EntityType<CactoidEntity> CACTOID = registerEntity(EntityType.Builder.of(CactoidEntity::new, EntityClassification.MONSTER).sized(0.5F, 1.1F), "cactoid");
	public static final EntityType<WarpedFireflyEntity> WARPEDFIREFLY = registerEntity(EntityType.Builder.of(WarpedFireflyEntity::new, EntityClassification.MONSTER).sized(0.7F, 0.6F).fireImmune().clientTrackingRange(8), "warpedfirefly");
	public static final EntityType<SeaHagEntity> SEAHAG = registerEntity(EntityType.Builder.of(SeaHagEntity::new, EntityClassification.MONSTER).sized(0.75F, 1.75F).clientTrackingRange(8), "seahag");
	public static final EntityType<WispEntity> WISP = registerEntity(EntityType.Builder.of(WispEntity::new, EntityClassification.MONSTER).sized(0.525F, 0.525F).clientTrackingRange(8).fireImmune(), "wisp");
	public static final EntityType<GraveRobberEntity> GRAVEROBBER = registerEntity(EntityType.Builder.of(GraveRobberEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8), "graverobber");
	public static final EntityType<GraveRobberGhostEntity> GRAVEROBBERGHOST = registerEntity(EntityType.Builder.of(GraveRobberGhostEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8), "graverobberghost");
	public static final EntityType<WraithEntity> WRAITH = registerEntity(EntityType.Builder.of(WraithEntity::new, EntityClassification.MONSTER).sized(0.75F, 1.75F).clientTrackingRange(8), "wraith");
	public static final EntityType<GhostSwarmerEntity> GHOSTSWARMER = registerEntity(EntityType.Builder.of(GhostSwarmerEntity::new, EntityClassification.MONSTER).sized(0.7F, 0.5F).clientTrackingRange(8).fireImmune(), "ghostswarmer");
	public static final EntityType<ScarabEntity> SCARAB = registerEntity(EntityType.Builder.of(ScarabEntity::new, EntityClassification.MONSTER).sized(1.0F, 0.6F).clientTrackingRange(8), "scarab");
	public static final EntityType<BeelzebubEntity> BEELZEBUB = registerEntity(EntityType.Builder.of(BeelzebubEntity::new, EntityClassification.MONSTER).sized(1.6F, 1.0F), "beelzebub");
	public static final EntityType<VespaCocoonEntity> BEELZEBUBPUPA = registerEntity(EntityType.Builder.of(VespaCocoonEntity::new, EntityClassification.MONSTER).sized(0.8F, 1.0F), "beelzebubpupa");
	public static final EntityType<EnigmothEntity> ENIGMOTH = registerEntity(EntityType.Builder.of(EnigmothEntity::new, EntityClassification.MONSTER).sized(1.6F, 1.0F).fireImmune(), "enigmoth");
	public static final EntityType<UndeadFishEntity> MUMMIFIEDCOD = registerEntity(EntityType.Builder.of(UndeadFishEntity::new, EntityClassification.WATER_AMBIENT).sized(0.5F, 0.3F).clientTrackingRange(4), "mummified_cod");
	public static final EntityType<UndeadFishEntity> BONETROUT = registerEntity(EntityType.Builder.of(UndeadFishEntity::new, EntityClassification.WATER_AMBIENT).sized(0.5F, 0.3F).clientTrackingRange(4), "bone_trout");
	public static final EntityType<LampreyEntity> LAMPREY = registerEntity(EntityType.Builder.of(LampreyEntity::new, EntityClassification.WATER_AMBIENT).sized(0.8F, 0.3F).clientTrackingRange(4), "lamprey");
	public static final EntityType<GhoulEntity> GHOUL = registerEntity(EntityType.Builder.of(GhoulEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.2F), "ghoul");
	//public static final EntityType<LivingArmorEntity> LIVING_ARMOR = registerEntity(EntityType.Builder.of(LivingArmorEntity::new, EntityClassification.MONSTER).sized(1.0F, 1.95F), "living_armor");
	
	public static final EntityType<WarSmallFireballEntity> WAR_SMALL_FIREBALL = registerEntity(EntityType.Builder.<WarSmallFireballEntity>of(WarSmallFireballEntity::new, EntityClassification.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(1), "warsmallfireball");
	public static final EntityType<PiranhaLauncherEntity> PIRANHA_LAUNCHER = registerEntity(EntityType.Builder.<PiranhaLauncherEntity>of(PiranhaLauncherEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10), "piranhalauncher");
	public static final EntityType<AcidJetEntity> ACIDJET = registerEntity(EntityType.Builder.<AcidJetEntity>of(AcidJetEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10), "acidjet");
	public static final EntityType<BasicBombEntity> HOLY_GRENADE = registerEntity(EntityType.Builder.<BasicBombEntity>of(BasicBombEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10), "holygrenade");
	public static final EntityType<BasicBombEntity> GHOSTBOMB = registerEntity(EntityType.Builder.<BasicBombEntity>of(BasicBombEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10), "ghostbomb");
	public static final EntityType<BasicBombEntity> SONICBOMB = registerEntity(EntityType.Builder.<BasicBombEntity>of(BasicBombEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10), "sonicbomb");
	public static final EntityType<SludgeJetEntity> SLUDGEJET = registerEntity(EntityType.Builder.<SludgeJetEntity>of(SludgeJetEntity::new, EntityClassification.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(1), "sludgejet");
	public static final EntityType<SandBurstEntity> SANDBURST = registerEntity(EntityType.Builder.<SandBurstEntity>of(SandBurstEntity::new, EntityClassification.MISC).sized(0.5F, 0.8F).clientTrackingRange(6).updateInterval(2), "sandburst");
	public static final EntityType<DeathCoilEntity> DEATHCOIL = registerEntity(EntityType.Builder.<DeathCoilEntity>of(DeathCoilEntity::new, EntityClassification.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(1), "deathcoil");
	public static final EntityType<FlameJetEntity> FLAMEJET = registerEntity(EntityType.Builder.<FlameJetEntity>of(FlameJetEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10), "flamejet");
	public static final EntityType<CactusThornEntity> CACTUS_THORN = registerEntity(EntityType.Builder.<CactusThornEntity>of(CactusThornEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20), "cactus_thorn");
	public static final EntityType<SapJetEntity> SAPJET = registerEntity(EntityType.Builder.<SapJetEntity>of(SapJetEntity::new, EntityClassification.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(1), "sapjet");
	public static final EntityType<MothScalesEntity> MOTH_SCALES = registerEntity(EntityType.Builder.<MothScalesEntity>of(MothScalesEntity::new, EntityClassification.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(1), "moth_scales");
	public static final EntityType<BasicBombEntity> BASIC_BOMB = registerEntity(EntityType.Builder.<BasicBombEntity>of(BasicBombEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10), "basic_bomb");
	public static final EntityType<FURArrowEntity> GHOUL_ARROW = registerEntity(EntityType.Builder.<FURArrowEntity>of(FURArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20), "ghoul_arrow");
	public static final EntityType<FURArrowEntity> FANG_ARROW = registerEntity(EntityType.Builder.<FURArrowEntity>of(FURArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20), "fang_arrow");
	
	@SuppressWarnings("unchecked")
	private static final <T extends Entity> EntityType<T> registerEntity(EntityType.Builder<T> builder, String entityName) {
        ResourceLocation nameLoc = new ResourceLocation(mod_LavaCow.MODID, entityName);

    	return (EntityType<T>) builder.build(entityName).setRegistryName(nameLoc);
    }
    
    static {
        EntitySpawnPlacementRegistry.register(LAVACOW, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LavaCowEntity::checkLavaCowSpawnRules);
        EntitySpawnPlacementRegistry.register(MYCOSIS, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, MycosisEntity::checkMycosisSpawnRules);
        EntitySpawnPlacementRegistry.register(PARASITE, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ParasiteEntity::checkMonsterSpawnRules);
        EntitySpawnPlacementRegistry.register(FOGLET, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, FogletEntity::checkFogletSpawnRules);
        EntitySpawnPlacementRegistry.register(IMP, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, FogletEntity::checkFogletSpawnRules);
        EntitySpawnPlacementRegistry.register(FRIGID, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FrigidEntity::checkFrigidSpawnRules);
        EntitySpawnPlacementRegistry.register(UNDEADSWINE, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, UndeadSwineEntity::checkUndeadSwineSpawnRules);
        EntitySpawnPlacementRegistry.register(SALAMANDER, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SalamanderEntity::checkSalamanderSpawnRules);
        EntitySpawnPlacementRegistry.register(WENDIGO, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WendigoEntity::checkWendigoSpawnRules);
        EntitySpawnPlacementRegistry.register(MIMIC, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MimicEntity::checkMimicSpawnRules);
        EntitySpawnPlacementRegistry.register(SLUDGELORD, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SludgeLordEntity::checkSludgeLordSpawnRules);
        EntitySpawnPlacementRegistry.register(RAVEN, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, RavenEntity::checkRavenSpawnRules);
        EntitySpawnPlacementRegistry.register(SEAGULL, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, RavenEntity::checkSeagullSpawnRules);
        EntitySpawnPlacementRegistry.register(PTERA, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING, FlyingMobEntity::checkFlyerSpawnRules);
        EntitySpawnPlacementRegistry.register(VESPA, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING, FlyingMobEntity::checkFlyerSpawnRules);
        EntitySpawnPlacementRegistry.register(SCARECROW, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ScarecrowEntity::checkScarecrowSpawnRules);
        EntitySpawnPlacementRegistry.register(PIRANHA, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PiranhaEntity::checkPiranhaSpawnRules);
        EntitySpawnPlacementRegistry.register(SWARMER, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SwarmerEntity::checkSwarmerSpawnRules);
        EntitySpawnPlacementRegistry.register(BONEWORM, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BoneWormEntity::checkBoneWormSpawnRules);
        EntitySpawnPlacementRegistry.register(PINGU, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PinguEntity::checkPinguSpawnRules);
        EntitySpawnPlacementRegistry.register(UNDERTAKER, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, UndertakerEntity::checkUndertakerSpawnRules);
        EntitySpawnPlacementRegistry.register(GHOSTRAY, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GhostRayEntity::checkGhostRaySpawnRules);
        EntitySpawnPlacementRegistry.register(BANSHEE, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FloatingMobEntity::checkBansheeSpawnRules);
        EntitySpawnPlacementRegistry.register(WETA, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WetaEntity::checkWetaSpawnRules);
        EntitySpawnPlacementRegistry.register(AVATON, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FloatingMobEntity::checkBansheeSpawnRules);
        EntitySpawnPlacementRegistry.register(FORSAKEN, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ForsakenEntity::checkForsakenSpawnRules);
        EntitySpawnPlacementRegistry.register(SKELETONKING, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
        EntitySpawnPlacementRegistry.register(MUMMY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MummyEntity::checkMummySpawnRules);
        EntitySpawnPlacementRegistry.register(CACTYRANT, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CactyrantEntity::checkCactyrantSpawnRules);
        EntitySpawnPlacementRegistry.register(CACTOID, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CactoidEntity::checkCactoidSpawnRules);
        EntitySpawnPlacementRegistry.register(WARPEDFIREFLY, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlyingMobEntity::checkFlyerSpawnRules);
        EntitySpawnPlacementRegistry.register(SEAHAG, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SeaHagEntity::checkSeaHagSpawnRules);
        EntitySpawnPlacementRegistry.register(WISP, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkWispSpawnRules);
        EntitySpawnPlacementRegistry.register(GRAVEROBBER, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
        EntitySpawnPlacementRegistry.register(GRAVEROBBERGHOST, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FloatingMobEntity::checkBansheeSpawnRules);
        EntitySpawnPlacementRegistry.register(WRAITH, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FloatingMobEntity::checkBansheeSpawnRules);
        EntitySpawnPlacementRegistry.register(GHOSTSWARMER, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GhostSwarmerEntity::checkWispSpawnRules);
        EntitySpawnPlacementRegistry.register(BEELZEBUB, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING, FlyingMobEntity::checkFlyerSpawnRules);
        EntitySpawnPlacementRegistry.register(ENIGMOTH, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING, EnigmothEntity::checkEnigmothSpawnRules);
        EntitySpawnPlacementRegistry.register(MUMMIFIEDCOD, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, UndeadFishEntity::checkUndeadFishSpawnRules);
        EntitySpawnPlacementRegistry.register(BONETROUT, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, UndeadFishEntity::checkUndeadFishSpawnRules);
        EntitySpawnPlacementRegistry.register(LAMPREY, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SwarmerEntity::checkSwarmerSpawnRules);
        EntitySpawnPlacementRegistry.register(GHOUL, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, GhoulEntity::checkGhoulSpawnRules);
        //EntitySpawnPlacementRegistry.register(LIVING_ARMOR, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, LivingArmorEntity::checkLivingArmorSpawnRules);
    }
    
    @SuppressWarnings("rawtypes")
	@SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
        try {
            for (Field f : FUREntityRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof EntityType) {
                    event.getRegistry().register((EntityType<?>) obj);
                } else if (obj instanceof EntityType[]) {
                    for (EntityType type : (EntityType[]) obj) {
                        event.getRegistry().register(type);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public static void initializeAttributes(EntityAttributeCreationEvent event) {
        event.put(LAVACOW, LavaCowEntity.createAttributes().build());
        event.put(MYCOSIS, MycosisEntity.createAttributes().build());
        event.put(PARASITE, ParasiteEntity.createAttributes().build());
        event.put(FOGLET, FogletEntity.createAttributesFoglet().build());
        event.put(IMP, FogletEntity.createAttributesImp().build());
        event.put(FRIGID, FrigidEntity.createAttributes().build());
        event.put(UNDEADSWINE, UndeadSwineEntity.createAttributes().build());
        event.put(SALAMANDER, SalamanderEntity.createAttributes().build());
        event.put(WENDIGO, WendigoEntity.createAttributes().build());
        event.put(MIMIC, MimicEntity.createAttributes().build());
        event.put(SLUDGELORD, SludgeLordEntity.createAttributes().build());
        event.put(LILSLUDGE, LilSludgeEntity.createAttributes().build());
        event.put(RAVEN, RavenEntity.createAttributes().build());
        event.put(SEAGULL, RavenEntity.createAttributes().build());
        event.put(PTERA, PteraEntity.createAttributes().build());
        event.put(VESPA, VespaEntity.createAttributes().build());
        event.put(SCARECROW, ScarecrowEntity.createAttributes().build());
        event.put(VESPACOCOON, VespaCocoonEntity.createAttributes().build());
        event.put(PIRANHA, PiranhaEntity.createAttributes().build());
        event.put(SWARMER, SwarmerEntity.createAttributes().build());
        event.put(BONEWORM, BoneWormEntity.createAttributes().build());
        event.put(PINGU, PinguEntity.createAttributes().build());
        event.put(UNDERTAKER, UndertakerEntity.createAttributes().build());
        event.put(UNBURIED, UnburiedEntity.createAttributes().build());
        event.put(GHOSTRAY, GhostRayEntity.createAttributes().build());
        event.put(BANSHEE, BansheeEntity.createAttributes().build());
        event.put(WETA, WetaEntity.createAttributes().build());
        event.put(AVATON, AvatonEntity.createAttributes().build());
        event.put(FORSAKEN, ForsakenEntity.createAttributes().build());
        event.put(SKELETONKING, SkeletonKingEntity.createAttributes().build());
        event.put(MUMMY, MummyEntity.createAttributes().build());
        event.put(CACTYRANT, CactyrantEntity.createAttributes().build());
        event.put(CACTOID, CactyrantEntity.createAttributes().build());
        event.put(WARPEDFIREFLY, WarpedFireflyEntity.createAttributes().build());
        event.put(SEAHAG, SeaHagEntity.createAttributes().build());
        event.put(WISP, WispEntity.createAttributes().build());
        event.put(GRAVEROBBER, GraveRobberEntity.createAttributes().build());
        event.put(GRAVEROBBERGHOST, GraveRobberGhostEntity.createAttributes().build());
        event.put(WRAITH, SeaHagEntity.createAttributes().build());
        event.put(GHOSTSWARMER, GhostSwarmerEntity.createAttributes().build());
        event.put(SCARAB, ScarabEntity.createAttributes().build());
        event.put(BEELZEBUB, BeelzebubEntity.createAttributes().build());
        event.put(BEELZEBUBPUPA, VespaCocoonEntity.createAttributes().build());
        event.put(ENIGMOTH, BeelzebubEntity.createAttributes().build());
        event.put(MUMMIFIEDCOD, CodEntity.createAttributes().build());
        event.put(BONETROUT, CodEntity.createAttributes().build());
        event.put(LAMPREY, LampreyEntity.createAttributes().build());
        event.put(GHOUL, GhoulEntity.createAttributes().build());
        //event.put(LIVING_ARMOR, LivingArmorEntity.createAttributes().build());
    }
}
