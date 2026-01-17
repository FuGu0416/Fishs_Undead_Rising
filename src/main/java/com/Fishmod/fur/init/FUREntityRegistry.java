package com.Fishmod.fur.init;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.CactyrantEntity;
import com.Fishmod.fur.entities.FogletEntity;
import com.Fishmod.fur.entities.ImpEntity;
import com.Fishmod.fur.entities.IsnachiEntity;
import com.Fishmod.fur.entities.LavaCowEntity;
import com.Fishmod.fur.entities.UndertakerEntity;
import com.Fishmod.fur.entities.WendigoEntity;
import com.Fishmod.fur.entities.aquatic.PiranhaEntity;
import com.Fishmod.fur.entities.aquatic.SwarmerEntity;
import com.Fishmod.fur.entities.floating.AvatonEntity;
import com.Fishmod.fur.entities.floating.BansheeEntity;
import com.Fishmod.fur.entities.floating.FloatingMobEntity;
import com.Fishmod.fur.entities.floating.SeaHagEntity;
import com.Fishmod.fur.entities.floating.WraithEntity;
import com.Fishmod.fur.entities.flying.PteraEntity;
import com.Fishmod.fur.entities.projectiles.BasicBombEntity;
import com.Fishmod.fur.entities.projectiles.CactusThornEntity;
import com.Fishmod.fur.entities.projectiles.FURArrowEntity;
import com.Fishmod.fur.entities.projectiles.FangDaggerEntity;
import com.Fishmod.fur.entities.tameable.CactoidEntity;
import com.Fishmod.fur.entities.tameable.MimicEntity;
import com.Fishmod.fur.entities.tameable.ScarecrowEntity;
import com.Fishmod.fur.entities.tameable.WetaEntity;
import com.Fishmod.fur.entities.tameable.WispEntity;
import com.Fishmod.fur.entities.tameable.unburied.FrigidEntity;
import com.Fishmod.fur.entities.tameable.unburied.MummyEntity;
import com.Fishmod.fur.entities.tameable.unburied.MycosisEntity;
import com.Fishmod.fur.entities.tameable.unburied.UnburiedEntity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FUREntityRegistry {
	public static final DeferredRegister<EntityType<?>> DEF_REG = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, mod_LavaCow.MODID);
	
	public static final RegistryObject<EntityType<LavaCowEntity>> LAVACOW = DEF_REG.register("lavacow", () -> (EntityType<LavaCowEntity>) EntityType.Builder.of(LavaCowEntity::new, MobCategory.CREATURE).sized(0.8F, 1.5F).setTrackingRange(8).build("lavacow"));
	public static final RegistryObject<EntityType<FogletEntity>> FOGLET = DEF_REG.register("foglet", () -> (EntityType<FogletEntity>) EntityType.Builder.of(FogletEntity::new, MobCategory.MONSTER).sized(0.6F, 1.2F).setTrackingRange(8).build("foglet"));
	public static final RegistryObject<EntityType<IsnachiEntity>> ISNACHI = DEF_REG.register("isnachi", () -> (EntityType<IsnachiEntity>) EntityType.Builder.of(IsnachiEntity::new, MobCategory.MONSTER).sized(0.6F, 1.2F).setTrackingRange(8).build("isnachi"));
	public static final RegistryObject<EntityType<ImpEntity>> IMP = DEF_REG.register("imp", () -> (EntityType<ImpEntity>) EntityType.Builder.of(ImpEntity::new, MobCategory.MONSTER).sized(0.6F, 1.2F).setTrackingRange(8).fireImmune().build("imp"));
	public static final RegistryObject<EntityType<SeaHagEntity>> SEAHAG = DEF_REG.register("seahag", () -> (EntityType<SeaHagEntity>) EntityType.Builder.of(SeaHagEntity::new, MobCategory.MONSTER).sized(0.75F, 1.75F).setTrackingRange(8).build("seahag"));
	public static final RegistryObject<EntityType<PiranhaEntity>> PIRANHA = DEF_REG.register("piranha", () -> (EntityType<PiranhaEntity>) EntityType.Builder.of(PiranhaEntity::new, MobCategory.WATER_AMBIENT).sized(0.7F, 0.5F).setTrackingRange(8).build("piranha"));
	public static final RegistryObject<EntityType<SwarmerEntity>> SWARMER = DEF_REG.register("swarmer", () -> (EntityType<SwarmerEntity>) EntityType.Builder.of(SwarmerEntity::new, MobCategory.WATER_AMBIENT).sized(1.0F, 0.8F).setTrackingRange(8).build("swarmer"));
	public static final RegistryObject<EntityType<CactyrantEntity>> CACTYRANT = DEF_REG.register("cactyrant", () -> (EntityType<CactyrantEntity>) EntityType.Builder.of(CactyrantEntity::new, MobCategory.MONSTER).sized(1.3F, 2.8F).setTrackingRange(8).build("cactyrant"));
	public static final RegistryObject<EntityType<WendigoEntity>> WENDIGO = DEF_REG.register("wendigo", () -> (EntityType<WendigoEntity>) EntityType.Builder.of(WendigoEntity::new, MobCategory.MONSTER).sized(1.6F, 2.6F).setTrackingRange(8).build("wendigo"));
	public static final RegistryObject<EntityType<ScarecrowEntity>> SCARECROW = DEF_REG.register("scarecrow", () -> (EntityType<ScarecrowEntity>) EntityType.Builder.of(ScarecrowEntity::new, MobCategory.MONSTER).sized(0.8F, 3.0F).setTrackingRange(8).build("scarecrow"));
	public static final RegistryObject<EntityType<WetaEntity>> WETA = DEF_REG.register("weta", () -> (EntityType<WetaEntity>) EntityType.Builder.of(WetaEntity::new, MobCategory.MONSTER).sized(0.8F, 0.5F).immuneTo(Blocks.SWEET_BERRY_BUSH, Blocks.CACTUS).setTrackingRange(8).build("scarecrow"));
	public static final RegistryObject<EntityType<AvatonEntity>> AVATON = DEF_REG.register("avaton", () -> (EntityType<AvatonEntity>) EntityType.Builder.of(AvatonEntity::new, MobCategory.MONSTER).sized(1.25F, 1.5F).setTrackingRange(8).build("avaton"));
	public static final RegistryObject<EntityType<WraithEntity>> WRAITH = DEF_REG.register("wraith", () -> (EntityType<WraithEntity>) EntityType.Builder.of(WraithEntity::new, MobCategory.MONSTER).sized(0.75F, 1.75F).setTrackingRange(8).build("wraith"));
	public static final RegistryObject<EntityType<WispEntity>> WISP = DEF_REG.register("wisp", () -> (EntityType<WispEntity>) EntityType.Builder.of(WispEntity::new, MobCategory.MONSTER).sized(0.525F, 0.525F).setTrackingRange(8).fireImmune().build("wisp"));
	public static final RegistryObject<EntityType<UnburiedEntity>> UNBURIED = DEF_REG.register("unburied", () -> (EntityType<UnburiedEntity>) EntityType.Builder.of(UnburiedEntity::new, MobCategory.MONSTER).sized(1.0F, 1.95F).setTrackingRange(8).build("unburied"));
	public static final RegistryObject<EntityType<MycosisEntity>> MYCOSIS = DEF_REG.register("mycosis", () -> (EntityType<MycosisEntity>) EntityType.Builder.of(MycosisEntity::new, MobCategory.MONSTER).sized(1.0F, 1.95F).setTrackingRange(8).build("mycosis"));
	public static final RegistryObject<EntityType<FrigidEntity>> FRIGID = DEF_REG.register("frigid", () -> (EntityType<FrigidEntity>) EntityType.Builder.of(FrigidEntity::new, MobCategory.MONSTER).sized(1.0F, 1.95F).setTrackingRange(8).build("frigid"));
	public static final RegistryObject<EntityType<MummyEntity>> MUMMY = DEF_REG.register("mummy", () -> (EntityType<MummyEntity>) EntityType.Builder.of(MummyEntity::new, MobCategory.MONSTER).sized(1.0F, 1.95F).setTrackingRange(8).build("mummy"));
	public static final RegistryObject<EntityType<UndertakerEntity>> UNDERTAKER = DEF_REG.register("undertaker", () -> (EntityType<UndertakerEntity>) EntityType.Builder.of(UndertakerEntity::new, MobCategory.MONSTER).sized(1.8F, 2.4F).setTrackingRange(8).build("undertaker"));
	public static final RegistryObject<EntityType<BansheeEntity>> BANSHEE = DEF_REG.register("banshee", () -> (EntityType<BansheeEntity>) EntityType.Builder.of(BansheeEntity::new, MobCategory.MONSTER).sized(0.75F, 1.75F).setTrackingRange(8).build("banshee"));
	public static final RegistryObject<EntityType<CactoidEntity>> CACTOID = DEF_REG.register("cactoid", () -> (EntityType<CactoidEntity>) EntityType.Builder.of(CactoidEntity::new, MobCategory.MONSTER).sized(0.5F, 1.1F).setTrackingRange(8).build("cactoid"));
	public static final RegistryObject<EntityType<MimicEntity>> MIMIC = DEF_REG.register("mimic", () -> (EntityType<MimicEntity>) EntityType.Builder.of(MimicEntity::new, MobCategory.MONSTER).sized(1.0F, 1.0F).setTrackingRange(8).build("mimic"));
	public static final RegistryObject<EntityType<PteraEntity>> PTERA = DEF_REG.register("ptera", () -> (EntityType<PteraEntity>) EntityType.Builder.of(PteraEntity::new, MobCategory.MONSTER).sized(1.6F, 0.8F).setTrackingRange(8).build("ptera"));
	
	public static final RegistryObject<EntityType<CactusThornEntity>> CACTUS_THORN = DEF_REG.register("cactus_thorn", () -> (EntityType) EntityType.Builder.of(CactusThornEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).setTrackingRange(4).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).build("cactus_thorn"));
	public static final RegistryObject<EntityType<BasicBombEntity>> BASIC_BOMB = DEF_REG.register("basic_bomb", () -> (EntityType) EntityType.Builder.of(BasicBombEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(4).setShouldReceiveVelocityUpdates(true).setUpdateInterval(10).build("basic_bomb"));
	public static final RegistryObject<EntityType<BasicBombEntity>> HOLY_GRENADE = DEF_REG.register("holygrenade", () -> (EntityType) EntityType.Builder.of(BasicBombEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(4).setShouldReceiveVelocityUpdates(true).setUpdateInterval(10).build("holygrenade"));
	public static final RegistryObject<EntityType<BasicBombEntity>> GHOST_BOMB = DEF_REG.register("ghost_bomb", () -> (EntityType) EntityType.Builder.of(BasicBombEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(4).setShouldReceiveVelocityUpdates(true).setUpdateInterval(10).build("ghostbomb"));
	public static final RegistryObject<EntityType<BasicBombEntity>> SONIC_BOMB = DEF_REG.register("sonic_bomb", () -> (EntityType) EntityType.Builder.of(BasicBombEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(4).setShouldReceiveVelocityUpdates(true).setUpdateInterval(10).build("sonicbomb"));
	public static final RegistryObject<EntityType<FURArrowEntity>> GHOUL_ARROW = DEF_REG.register("ghoul_arrow", () -> (EntityType) EntityType.Builder.of(FURArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("ghoul_arrow"));
	public static final RegistryObject<EntityType<FURArrowEntity>> FANG_ARROW = DEF_REG.register("fang_arrow", () -> (EntityType) EntityType.Builder.of(FURArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("fang_arrow"));
	public static final RegistryObject<EntityType<FangDaggerEntity>> FANG_DAGGER = DEF_REG.register("fang_dagger", () -> (EntityType) EntityType.Builder.of(FangDaggerEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("fang_dagger"));
	
	/*
	public static final EntityType<ParasiteEntity> PARASITE = registerEntity(EntityType.Builder.of(ParasiteEntity::new, EntityClassification.MONSTER).sized(0.8F, 0.3F), "parasite");
	public static final EntityType<UndeadSwineEntity> UNDEADSWINE = registerEntity(EntityType.Builder.of(UndeadSwineEntity::new, EntityClassification.MONSTER).sized(1.6F, 1.8F), "undeadswine");
	public static final EntityType<SalamanderEntity> SALAMANDER = registerEntity(EntityType.Builder.of(SalamanderEntity::new, EntityClassification.MONSTER).sized(1.95F, 1.6F).fireImmune(), "salamander");
	public static final EntityType<SludgeLordEntity> SLUDGELORD = registerEntity(EntityType.Builder.of(SludgeLordEntity::new, EntityClassification.MONSTER).sized(2.2F, 3.7F), "sludgelord");
	public static final EntityType<LilSludgeEntity> LILSLUDGE = registerEntity(EntityType.Builder.of(LilSludgeEntity::new, EntityClassification.MONSTER).sized(1.0F, 2.0F), "lilsludge");
	public static final EntityType<RavenEntity> RAVEN = registerEntity(EntityType.Builder.of(RavenEntity::new, EntityClassification.CREATURE).sized(0.5F, 0.9F), "raven");
	public static final EntityType<RavenEntity> SEAGULL = registerEntity(EntityType.Builder.of(RavenEntity::new, EntityClassification.CREATURE).sized(0.5F, 0.9F), "seagull");
	public static final EntityType<VespaEntity> VESPA = registerEntity(EntityType.Builder.of(VespaEntity::new, EntityClassification.MONSTER).sized(1.6F, 1.0F), "vespa");	
	public static final EntityType<VespaCocoonEntity> VESPACOCOON = registerEntity(EntityType.Builder.of(VespaCocoonEntity::new, EntityClassification.MONSTER).sized(0.8F, 1.0F), "vespacocoon");	
	public static final EntityType<BoneWormEntity> BONEWORM = registerEntity(EntityType.Builder.of(BoneWormEntity::new, EntityClassification.MONSTER).sized(0.8F, 2.0F), "boneworm");
	public static final EntityType<PinguEntity> PINGU = registerEntity(EntityType.Builder.of(PinguEntity::new, EntityClassification.MONSTER).sized(0.5F, 0.8F), "pingu");
	public static final EntityType<GhostRayEntity> GHOSTRAY = registerEntity(EntityType.Builder.of(GhostRayEntity::new, EntityClassification.MONSTER).sized(1.6F, 0.25F), "ghostray");
	public static final EntityType<ForsakenEntity> FORSAKEN = registerEntity(EntityType.Builder.of(ForsakenEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.99F).fireImmune(), "forsaken");
	public static final EntityType<SkeletonKingEntity> SKELETONKING = registerEntity(EntityType.Builder.of(SkeletonKingEntity::new, EntityClassification.MONSTER).sized(1.25F, 3.1F).fireImmune(), "skeletonking");
	public static final EntityType<WarpedFireflyEntity> WARPEDFIREFLY = registerEntity(EntityType.Builder.of(WarpedFireflyEntity::new, EntityClassification.MONSTER).sized(0.7F, 0.6F).fireImmune().clientTrackingRange(8), "warpedfirefly");
	public static final EntityType<GraveRobberEntity> GRAVEROBBER = registerEntity(EntityType.Builder.of(GraveRobberEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8), "graverobber");
	public static final EntityType<GraveRobberGhostEntity> GRAVEROBBERGHOST = registerEntity(EntityType.Builder.of(GraveRobberGhostEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8), "graverobberghost");
	public static final EntityType<ScarabEntity> SCARAB = registerEntity(EntityType.Builder.of(ScarabEntity::new, EntityClassification.MONSTER).sized(1.0F, 0.6F).clientTrackingRange(8), "scarab");
	public static final EntityType<BeelzebubEntity> BEELZEBUB = registerEntity(EntityType.Builder.of(BeelzebubEntity::new, EntityClassification.MONSTER).sized(1.6F, 1.0F), "beelzebub");
	public static final EntityType<VespaCocoonEntity> BEELZEBUBPUPA = registerEntity(EntityType.Builder.of(VespaCocoonEntity::new, EntityClassification.MONSTER).sized(0.8F, 1.0F), "beelzebubpupa");
	public static final EntityType<EnigmothEntity> ENIGMOTH = registerEntity(EntityType.Builder.of(EnigmothEntity::new, EntityClassification.MONSTER).sized(1.6F, 1.0F).fireImmune(), "enigmoth");
	public static final EntityType<UndeadFishEntity> MUMMIFIEDCOD = registerEntity(EntityType.Builder.of(UndeadFishEntity::new, EntityClassification.WATER_AMBIENT).sized(0.5F, 0.3F).clientTrackingRange(4), "mummified_cod");
	public static final EntityType<UndeadFishEntity> BONETROUT = registerEntity(EntityType.Builder.of(UndeadFishEntity::new, EntityClassification.WATER_AMBIENT).sized(0.5F, 0.3F).clientTrackingRange(4), "bone_trout");
	public static final EntityType<LampreyEntity> LAMPREY = registerEntity(EntityType.Builder.of(LampreyEntity::new, EntityClassification.WATER_AMBIENT).sized(0.8F, 0.3F).clientTrackingRange(4), "lamprey");
	public static final EntityType<GhoulEntity> GHOUL = registerEntity(EntityType.Builder.of(GhoulEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.2F), "ghoul");
	public static final EntityType<LivingArmorEntity> LIVING_ARMOR = registerEntity(EntityType.Builder.of(LivingArmorEntity::new, EntityClassification.MONSTER).sized(1.0F, 1.95F), "living_armor");
	
	public static final EntityType<WarSmallFireballEntity> WAR_SMALL_FIREBALL = registerEntity(EntityType.Builder.<WarSmallFireballEntity>of(WarSmallFireballEntity::new, EntityClassification.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(1), "warsmallfireball");
	public static final EntityType<PiranhaLauncherEntity> PIRANHA_LAUNCHER = registerEntity(EntityType.Builder.<PiranhaLauncherEntity>of(PiranhaLauncherEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10), "piranhalauncher");
	public static final EntityType<AcidJetEntity> ACIDJET = registerEntity(EntityType.Builder.<AcidJetEntity>of(AcidJetEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10), "acidjet");
	public static final EntityType<SludgeJetEntity> SLUDGEJET = registerEntity(EntityType.Builder.<SludgeJetEntity>of(SludgeJetEntity::new, EntityClassification.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(1), "sludgejet");
	public static final EntityType<SandBurstEntity> SANDBURST = registerEntity(EntityType.Builder.<SandBurstEntity>of(SandBurstEntity::new, EntityClassification.MISC).sized(0.5F, 0.8F).clientTrackingRange(6).updateInterval(2), "sandburst");
	public static final EntityType<DeathCoilEntity> DEATHCOIL = registerEntity(EntityType.Builder.<DeathCoilEntity>of(DeathCoilEntity::new, EntityClassification.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(1), "deathcoil");
	public static final EntityType<FlameJetEntity> FLAMEJET = registerEntity(EntityType.Builder.<FlameJetEntity>of(FlameJetEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10), "flamejet");	
	public static final EntityType<SapJetEntity> SAPJET = registerEntity(EntityType.Builder.<SapJetEntity>of(SapJetEntity::new, EntityClassification.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(1), "sapjet");
	public static final EntityType<MothScalesEntity> MOTH_SCALES = registerEntity(EntityType.Builder.<MothScalesEntity>of(MothScalesEntity::new, EntityClassification.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(1), "moth_scales");
	*/
	
    @SubscribeEvent
    public static void spawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(LAVACOW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LavaCowEntity::checkLavaCowSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(FOGLET.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, FogletEntity::checkFogletSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(ISNACHI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, IsnachiEntity::checkIsnachiSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(IMP.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, ImpEntity::checkImpSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(SEAHAG.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SeaHagEntity::checkSeaHagSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(PIRANHA.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PiranhaEntity::checkPiranhaSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);       
        event.register(SWARMER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SwarmerEntity::checkSwarmerSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CACTYRANT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CactyrantEntity::checkCactyrantSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(WENDIGO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WendigoEntity::checkWendigoSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(SCARECROW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ScarecrowEntity::checkScarecrowSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);       
        event.register(WETA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WetaEntity::checkWetaSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(AVATON.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AvatonEntity::checkAvatonSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(WRAITH.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FloatingMobEntity::checkBansheeSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(WISP.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WispEntity::checkWispSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(MYCOSIS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, MycosisEntity::checkMycosisSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(FRIGID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FrigidEntity::checkFrigidSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(MUMMY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MummyEntity::checkMummySpawnRules, SpawnPlacementRegisterEvent.Operation.AND);      
        event.register(UNDERTAKER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UndertakerEntity::checkUndertakerSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(BANSHEE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FloatingMobEntity::checkBansheeSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CACTOID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CactoidEntity::checkCactoidSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(MIMIC.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MimicEntity::checkMimicSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(PTERA.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, PteraEntity::checkPteraSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        
        /*
        event.register(PARASITE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ParasiteEntity::checkMonsterSpawnRules);
        event.register(UNDEADSWINE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UndeadSwineEntity::checkUndeadSwineSpawnRules);
        event.register(SALAMANDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SalamanderEntity::checkSalamanderSpawnRules);        
        event.register(SLUDGELORD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SludgeLordEntity::checkSludgeLordSpawnRules);
        event.register(RAVEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, RavenEntity::checkRavenSpawnRules);
        event.register(SEAGULL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, RavenEntity::checkSeagullSpawnRules);
        event.register(VESPA.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, FlyingMobEntity::checkFlyerSpawnRules);        
        event.register(BONEWORM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BoneWormEntity::checkBoneWormSpawnRules);
        event.register(PINGU.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PinguEntity::checkPinguSpawnRules);
        event.register(GHOSTRAY.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GhostRayEntity::checkGhostRaySpawnRules);
        event.register(FORSAKEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ForsakenEntity::checkForsakenSpawnRules);
        event.register(SKELETONKING.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
        event.register(WARPEDFIREFLY.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FlyingMobEntity::checkFlyerSpawnRules);
        event.register(GRAVEROBBER.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
        event.register(GRAVEROBBERGHOST.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FloatingMobEntity::checkBansheeSpawnRules);
        event.register(BEELZEBUB.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, FlyingMobEntity::checkFlyerSpawnRules);
        event.register(ENIGMOTH.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, EnigmothEntity::checkEnigmothSpawnRules);
        event.register(MUMMIFIEDCOD.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UndeadFishEntity::checkUndeadFishSpawnRules);
        event.register(BONETROUT.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UndeadFishEntity::checkUndeadFishSpawnRules);
        event.register(LAMPREY.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SwarmerEntity::checkSwarmerSpawnRules);
        event.register(GHOUL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, GhoulEntity::checkGhoulSpawnRules);
        event.register(LIVING_ARMOR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, LivingArmorEntity::checkLivingArmorSpawnRules);*/
    }    

    @SubscribeEvent
    public static void initializeAttributes(EntityAttributeCreationEvent event) {
        event.put(LAVACOW.get(), LavaCowEntity.createAttributes().build());
        event.put(FOGLET.get(), FogletEntity.createAttributesFoglet().build());
        event.put(ISNACHI.get(), IsnachiEntity.createAttributesIsnachi().build());
        event.put(IMP.get(), ImpEntity.createAttributesImp().build());
        event.put(SEAHAG.get(), SeaHagEntity.createAttributes().build());
        event.put(PIRANHA.get(), PiranhaEntity.createAttributes().build());
        event.put(SWARMER.get(), SwarmerEntity.createAttributes().build());
        event.put(CACTYRANT.get(), CactyrantEntity.createAttributes().build());
        event.put(WENDIGO.get(), WendigoEntity.createAttributes().build());
        event.put(SCARECROW.get(), ScarecrowEntity.createAttributes().build());
        event.put(WETA.get(), WetaEntity.createAttributes().build());
        event.put(AVATON.get(), AvatonEntity.createAttributes().build());
        event.put(WRAITH.get(), SeaHagEntity.createAttributes().build());
        event.put(WISP.get(), WispEntity.createAttributes().build());
        event.put(UNBURIED.get(), UnburiedEntity.createAttributes().build());
        event.put(MYCOSIS.get(), MycosisEntity.createAttributes().build());
        event.put(FRIGID.get(), FrigidEntity.createAttributes().build());
        event.put(MUMMY.get(), MummyEntity.createAttributes().build());   
        event.put(UNDERTAKER.get(), UndertakerEntity.createAttributes().build());
        event.put(BANSHEE.get(), BansheeEntity.createAttributes().build());
        event.put(CACTOID.get(), CactoidEntity.createAttributes().build());
        event.put(MIMIC.get(), MimicEntity.createAttributes().build());
        event.put(PTERA.get(), PteraEntity.createAttributes().build());
        
        /*
        event.put(PARASITE, ParasiteEntity.createAttributes().build());
        event.put(UNDEADSWINE, UndeadSwineEntity.createAttributes().build());
        event.put(SALAMANDER, SalamanderEntity.createAttributes().build());   
        event.put(SLUDGELORD, SludgeLordEntity.createAttributes().build());
        event.put(LILSLUDGE, LilSludgeEntity.createAttributes().build());
        event.put(RAVEN, RavenEntity.createAttributes().build());
        event.put(SEAGULL, RavenEntity.createAttributes().build());
        event.put(VESPA, VespaEntity.createAttributes().build());     
        event.put(VESPACOCOON, VespaCocoonEntity.createAttributes().build());
        event.put(BONEWORM, BoneWormEntity.createAttributes().build());
        event.put(PINGU, PinguEntity.createAttributes().build());
        event.put(GHOSTRAY, GhostRayEntity.createAttributes().build());
        event.put(FORSAKEN, ForsakenEntity.createAttributes().build());
        event.put(SKELETONKING, SkeletonKingEntity.createAttributes().build());
        event.put(WARPEDFIREFLY, WarpedFireflyEntity.createAttributes().build());
        event.put(GRAVEROBBER, GraveRobberEntity.createAttributes().build());
        event.put(GRAVEROBBERGHOST, GraveRobberGhostEntity.createAttributes().build());
        event.put(SCARAB, ScarabEntity.createAttributes().build());
        event.put(BEELZEBUB, BeelzebubEntity.createAttributes().build());
        event.put(BEELZEBUBPUPA, VespaCocoonEntity.createAttributes().build());
        event.put(ENIGMOTH, BeelzebubEntity.createAttributes().build());
        event.put(MUMMIFIEDCOD, CodEntity.createAttributes().build());
        event.put(BONETROUT, CodEntity.createAttributes().build());
        event.put(LAMPREY, LampreyEntity.createAttributes().build());
        event.put(GHOUL, GhoulEntity.createAttributes().build());
        event.put(LIVING_ARMOR, LivingArmorEntity.createAttributes().build());*/
    }
}
