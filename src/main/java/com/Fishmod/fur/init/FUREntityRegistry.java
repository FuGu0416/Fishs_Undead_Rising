package com.Fishmod.fur.init;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.CactyrantEntity;
import com.Fishmod.fur.entities.FogletEntity;
import com.Fishmod.fur.entities.GhoulEntity;
import com.Fishmod.fur.entities.GraveRobberEntity;
import com.Fishmod.fur.entities.ImpEntity;
import com.Fishmod.fur.entities.IsnachiEntity;
import com.Fishmod.fur.entities.LavaCowEntity;
import com.Fishmod.fur.entities.MummyLordEntity;
import com.Fishmod.fur.entities.ParasiteEntity;
import com.Fishmod.fur.entities.UndertakerEntity;
import com.Fishmod.fur.entities.WendigoEntity;
import com.Fishmod.fur.entities.aquatic.LampreyEntity;
import com.Fishmod.fur.entities.aquatic.PiranhaEntity;
import com.Fishmod.fur.entities.aquatic.SwarmerEntity;
import com.Fishmod.fur.entities.aquatic.UndeadFishEntity;
import com.Fishmod.fur.entities.floating.AvatonEntity;
import com.Fishmod.fur.entities.floating.BansheeEntity;
import com.Fishmod.fur.entities.floating.FloatingMobEntity;
import com.Fishmod.fur.entities.floating.SeaHagEntity;
import com.Fishmod.fur.entities.floating.WraithEntity;
import com.Fishmod.fur.entities.flying.EnigmothEntity;
import com.Fishmod.fur.entities.flying.FlyingMobEntity;
import com.Fishmod.fur.entities.flying.GhostRayEntity;
import com.Fishmod.fur.entities.flying.PteraEntity;
import com.Fishmod.fur.entities.flying.VespaEntity;
import com.Fishmod.fur.entities.flying.WarpedFireflyEntity;
import com.Fishmod.fur.entities.projectiles.BasicBombEntity;
import com.Fishmod.fur.entities.projectiles.CactusThornEntity;
import com.Fishmod.fur.entities.projectiles.FURArrowEntity;
import com.Fishmod.fur.entities.projectiles.FangDaggerEntity;
import com.Fishmod.fur.entities.projectiles.MothScalesEntity;
import com.Fishmod.fur.entities.projectiles.SwarmerLauncherEntity;
import com.Fishmod.fur.entities.projectiles.LocustSwarmEntity;
import com.Fishmod.fur.entities.projectiles.MoltenGlobEntity;
import com.Fishmod.fur.entities.projectiles.MoltenPoolEntity;
import com.Fishmod.fur.entities.projectiles.WarSmallFireballEntity;
import com.Fishmod.fur.entities.tameable.CactoidEntity;
import com.Fishmod.fur.entities.tameable.CocoonEntity;
import com.Fishmod.fur.entities.tameable.MimicEntity;
import com.Fishmod.fur.entities.tameable.RavenEntity;
import com.Fishmod.fur.entities.tameable.SalamanderEntity;
import com.Fishmod.fur.entities.tameable.ScarabEntity;
import com.Fishmod.fur.entities.tameable.ShroomlingEntity;
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
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.entity.monster.Monster;
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
	
	public static final RegistryObject<EntityType<LavaCowEntity>> LAVACOW = DEF_REG.register("lavacow", () -> (EntityType<LavaCowEntity>) EntityType.Builder.of(LavaCowEntity::new, MobCategory.CREATURE).sized(0.8F, 1.5F).setTrackingRange(8).fireImmune().build("lavacow"));
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
	public static final RegistryObject<EntityType<MummyLordEntity>> MUMMY_LORD = DEF_REG.register("mummy_lord", () -> (EntityType<MummyLordEntity>) EntityType.Builder.of(MummyLordEntity::new, MobCategory.MONSTER).sized(1.2F, 2.4F).setTrackingRange(8).build("mummy_lord"));
	public static final RegistryObject<EntityType<UndertakerEntity>> UNDERTAKER = DEF_REG.register("undertaker", () -> (EntityType<UndertakerEntity>) EntityType.Builder.of(UndertakerEntity::new, MobCategory.MONSTER).sized(1.8F, 2.4F).setTrackingRange(8).build("undertaker"));
	public static final RegistryObject<EntityType<BansheeEntity>> BANSHEE = DEF_REG.register("banshee", () -> (EntityType<BansheeEntity>) EntityType.Builder.of(BansheeEntity::new, MobCategory.MONSTER).sized(0.75F, 1.75F).setTrackingRange(8).build("banshee"));
	public static final RegistryObject<EntityType<CactoidEntity>> CACTOID = DEF_REG.register("cactoid", () -> (EntityType<CactoidEntity>) EntityType.Builder.of(CactoidEntity::new, MobCategory.MONSTER).sized(0.5F, 1.1F).setTrackingRange(8).build("cactoid"));
	public static final RegistryObject<EntityType<ShroomlingEntity>> SHROOMLING = DEF_REG.register("shroomling", () -> (EntityType<ShroomlingEntity>) EntityType.Builder.of(ShroomlingEntity::new, MobCategory.MONSTER).sized(0.6F, 1.0F).setTrackingRange(8).build("shroomling"));
	public static final RegistryObject<EntityType<MimicEntity>> MIMIC = DEF_REG.register("mimic", () -> (EntityType<MimicEntity>) EntityType.Builder.of(MimicEntity::new, MobCategory.MONSTER).sized(1.0F, 1.0F).setTrackingRange(8).build("mimic"));
	public static final RegistryObject<EntityType<PteraEntity>> PTERA = DEF_REG.register("ptera", () -> (EntityType<PteraEntity>) EntityType.Builder.of(PteraEntity::new, MobCategory.MONSTER).sized(1.6F, 0.8F).setTrackingRange(8).build("ptera"));
	public static final RegistryObject<EntityType<SalamanderEntity>> SALAMANDER = DEF_REG.register("salamander", () -> (EntityType<SalamanderEntity>) EntityType.Builder.of(SalamanderEntity::new, MobCategory.MONSTER).sized(1.95F, 1.6F).setTrackingRange(8).fireImmune().build("salamander"));
	public static final RegistryObject<EntityType<EnigmothEntity>> ENIGMOTH = DEF_REG.register("enigmoth", () -> (EntityType<EnigmothEntity>) EntityType.Builder.of(EnigmothEntity::new, MobCategory.MONSTER).sized(1.6F, 1.0F).fireImmune().setTrackingRange(8).build("enigmoth"));
	public static final RegistryObject<EntityType<VespaEntity>> VESPA = DEF_REG.register("vespa", () -> (EntityType<VespaEntity>) EntityType.Builder.of(VespaEntity::new, MobCategory.MONSTER).sized(1.6F, 1.0F).setTrackingRange(8).build("vespa"));
	public static final RegistryObject<EntityType<CocoonEntity>> COCOON = DEF_REG.register("cocoon", () -> (EntityType<CocoonEntity>) EntityType.Builder.of(CocoonEntity::new, MobCategory.MONSTER).sized(0.8F, 1.0F).setTrackingRange(8).build("cocoon"));
	public static final RegistryObject<EntityType<ScarabEntity>> SCARAB = DEF_REG.register("scarab", () -> (EntityType<ScarabEntity>) EntityType.Builder.of(ScarabEntity::new, MobCategory.MONSTER).sized(1.0F, 0.6F).setTrackingRange(8).build("scarab"));
	public static final RegistryObject<EntityType<ParasiteEntity>> PARASITE = DEF_REG.register("parasite", () -> (EntityType<ParasiteEntity>) EntityType.Builder.of(ParasiteEntity::new, MobCategory.MONSTER).sized(0.8F, 0.3F).setTrackingRange(8).build("parasite"));
	public static final RegistryObject<EntityType<UndeadFishEntity>> BONE_TROUT = DEF_REG.register("bone_trout", () -> (EntityType<UndeadFishEntity>) EntityType.Builder.of(UndeadFishEntity::new, MobCategory.WATER_AMBIENT).sized(0.5F, 0.3F).setTrackingRange(4).build("bone_trout"));
	public static final RegistryObject<EntityType<UndeadFishEntity>> MUMMIFIED_COD = DEF_REG.register("mummified_cod", () -> (EntityType<UndeadFishEntity>) EntityType.Builder.of(UndeadFishEntity::new, MobCategory.WATER_AMBIENT).sized(0.5F, 0.3F).setTrackingRange(4).build("mummified_cod"));
	public static final RegistryObject<EntityType<GhoulEntity>> GHOUL = DEF_REG.register("ghoul", () -> (EntityType<GhoulEntity>) EntityType.Builder.of(GhoulEntity::new, MobCategory.MONSTER).sized(0.6F, 1.2F).setTrackingRange(8).build("ghoul"));
	public static final RegistryObject<EntityType<LampreyEntity>> LAMPREY = DEF_REG.register("lamprey", () -> (EntityType<LampreyEntity>) EntityType.Builder.of(LampreyEntity::new, MobCategory.WATER_AMBIENT).sized(0.8F, 0.3F).setTrackingRange(8).build("lamprey"));
	public static final RegistryObject<EntityType<RavenEntity>> RAVEN = DEF_REG.register("raven", () -> (EntityType<RavenEntity>) EntityType.Builder.of(RavenEntity::new, MobCategory.CREATURE).sized(0.5F, 0.9F).setTrackingRange(8).build("raven"));
	public static final RegistryObject<EntityType<GhostRayEntity>> GHOSTRAY = DEF_REG.register("ghostray", () -> (EntityType<GhostRayEntity>) EntityType.Builder.of(GhostRayEntity::new, MobCategory.MONSTER).sized(1.6F, 0.25F).setTrackingRange(8).build("ghostray"));
	public static final RegistryObject<EntityType<WarpedFireflyEntity>> WARPEDFIREFLY = DEF_REG.register("warpedfirefly", () -> (EntityType<WarpedFireflyEntity>) EntityType.Builder.of(WarpedFireflyEntity::new, MobCategory.MONSTER).sized(0.7F, 0.6F).fireImmune().setTrackingRange(8).build("warpedfirefly"));
	public static final RegistryObject<EntityType<GraveRobberEntity>> GRAVEROBBER = DEF_REG.register("graverobber", () -> (EntityType<GraveRobberEntity>) EntityType.Builder.of(GraveRobberEntity::new, MobCategory.MONSTER).sized(0.6F, 1.95F).setTrackingRange(8).build("graverobber"));

	public static final RegistryObject<EntityType<CactusThornEntity>> CACTUS_THORN = DEF_REG.register("cactus_thorn", () -> (EntityType) EntityType.Builder.of(CactusThornEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).setTrackingRange(4).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).build("cactus_thorn"));
	public static final RegistryObject<EntityType<BasicBombEntity>> BASIC_BOMB = DEF_REG.register("basic_bomb", () -> (EntityType) EntityType.Builder.of(BasicBombEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(4).setShouldReceiveVelocityUpdates(true).setUpdateInterval(10).build("basic_bomb"));
	public static final RegistryObject<EntityType<BasicBombEntity>> HOLY_GRENADE = DEF_REG.register("holy_grenade", () -> (EntityType) EntityType.Builder.of(BasicBombEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(4).setShouldReceiveVelocityUpdates(true).setUpdateInterval(10).build("holygrenade"));
	public static final RegistryObject<EntityType<BasicBombEntity>> GHOST_BOMB = DEF_REG.register("ghost_bomb", () -> (EntityType) EntityType.Builder.of(BasicBombEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(4).setShouldReceiveVelocityUpdates(true).setUpdateInterval(10).build("ghostbomb"));
	public static final RegistryObject<EntityType<BasicBombEntity>> SONIC_BOMB = DEF_REG.register("sonic_bomb", () -> (EntityType) EntityType.Builder.of(BasicBombEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(4).setShouldReceiveVelocityUpdates(true).setUpdateInterval(10).build("sonicbomb"));
	public static final RegistryObject<EntityType<FURArrowEntity>> GHOUL_ARROW = DEF_REG.register("ghoul_arrow", () -> (EntityType) EntityType.Builder.of(FURArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("ghoul_arrow"));
	public static final RegistryObject<EntityType<FURArrowEntity>> FANG_ARROW = DEF_REG.register("fang_arrow", () -> (EntityType) EntityType.Builder.of(FURArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("fang_arrow"));
	public static final RegistryObject<EntityType<FangDaggerEntity>> FANG_DAGGER = DEF_REG.register("fang_dagger", () -> (EntityType) EntityType.Builder.of(FangDaggerEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("fang_dagger"));
	public static final RegistryObject<EntityType<WarSmallFireballEntity>> WAR_SMALL_FIREBALL = DEF_REG.register("warsmallfireball", () -> (EntityType) EntityType.Builder.of(WarSmallFireballEntity::new, MobCategory.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10).build("warsmallfireball"));
	public static final RegistryObject<EntityType<MoltenGlobEntity>> MOLTEN_GLOB = DEF_REG.register("molten_glob", () -> (EntityType) EntityType.Builder.of(MoltenGlobEntity::new, MobCategory.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10).build("molten_glob"));
	public static final RegistryObject<EntityType<MoltenPoolEntity>> MOLTEN_POOL = DEF_REG.register("molten_pool", () -> (EntityType) EntityType.Builder.<MoltenPoolEntity>of(MoltenPoolEntity::new, MobCategory.MISC).fireImmune().sized(6.0F, 0.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE).build("molten_pool"));
	public static final RegistryObject<EntityType<MothScalesEntity>> MOTH_SCALES = DEF_REG.register("moth_scales", () -> (EntityType) EntityType.Builder.of(MothScalesEntity::new, MobCategory.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(1).build("moth_scales"));
	public static final RegistryObject<EntityType<LocustSwarmEntity>> LOCUST_SWARM = DEF_REG.register("locust_swarm", () -> (EntityType) EntityType.Builder.of(LocustSwarmEntity::new, MobCategory.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(1).build("locust_swarm"));
	public static final RegistryObject<EntityType<SwarmerLauncherEntity>> SWARMER_LAUNCHER = DEF_REG.register("swarmer_launcher", () -> (EntityType) EntityType.Builder.of(SwarmerLauncherEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build("swarmer_launcher"));
	
	/*
	public static final EntityType<UndeadSwineEntity> UNDEADSWINE = registerEntity(EntityType.Builder.of(UndeadSwineEntity::new, EntityClassification.MONSTER).sized(1.6F, 1.8F), "undeadswine");
	public static final EntityType<SludgeLordEntity> SLUDGELORD = registerEntity(EntityType.Builder.of(SludgeLordEntity::new, EntityClassification.MONSTER).sized(2.2F, 3.7F), "sludgelord");
	public static final EntityType<BoneWormEntity> BONEWORM = registerEntity(EntityType.Builder.of(BoneWormEntity::new, EntityClassification.MONSTER).sized(0.8F, 2.0F), "boneworm");
	public static final EntityType<PinguEntity> PINGU = registerEntity(EntityType.Builder.of(PinguEntity::new, EntityClassification.MONSTER).sized(0.5F, 0.8F), "pingu");
	public static final EntityType<ForsakenEntity> FORSAKEN = registerEntity(EntityType.Builder.of(ForsakenEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.99F).fireImmune(), "forsaken");
	public static final EntityType<SkeletonKingEntity> SKELETONKING = registerEntity(EntityType.Builder.of(SkeletonKingEntity::new, EntityClassification.MONSTER).sized(1.25F, 3.1F).fireImmune(), "skeletonking");
	public static final EntityType<BeelzebubEntity> BEELZEBUB = registerEntity(EntityType.Builder.of(BeelzebubEntity::new, EntityClassification.MONSTER).sized(1.6F, 1.0F), "beelzebub");
	public static final EntityType<VespaCocoonEntity> BEELZEBUBPUPA = registerEntity(EntityType.Builder.of(VespaCocoonEntity::new, EntityClassification.MONSTER).sized(0.8F, 1.0F), "beelzebubpupa");
	
	public static final EntityType<AcidJetEntity> ACIDJET = registerEntity(EntityType.Builder.<AcidJetEntity>of(AcidJetEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10), "acidjet");
	public static final EntityType<SludgeJetEntity> SLUDGEJET = registerEntity(EntityType.Builder.<SludgeJetEntity>of(SludgeJetEntity::new, EntityClassification.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(1), "sludgejet");
	public static final EntityType<SandBurstEntity> SANDBURST = registerEntity(EntityType.Builder.<SandBurstEntity>of(SandBurstEntity::new, EntityClassification.MISC).sized(0.5F, 0.8F).clientTrackingRange(6).updateInterval(2), "sandburst");
	public static final EntityType<DeathCoilEntity> DEATHCOIL = registerEntity(EntityType.Builder.<DeathCoilEntity>of(DeathCoilEntity::new, EntityClassification.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(1), "deathcoil");
	public static final EntityType<FlameJetEntity> FLAMEJET = registerEntity(EntityType.Builder.<FlameJetEntity>of(FlameJetEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10), "flamejet");	
	*/
	
    @SubscribeEvent
    public static void spawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(LAVACOW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LavaCowEntity::checkLavaCowSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(FOGLET.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, FogletEntity::checkFogletSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(ISNACHI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, IsnachiEntity::checkIsnachiSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(IMP.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, ImpEntity::checkImpSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(SEAHAG.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, SeaHagEntity::checkSeaHagSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(PIRANHA.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PiranhaEntity::checkPiranhaSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);       
        event.register(SWARMER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SwarmerEntity::checkSwarmerSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CACTYRANT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CactyrantEntity::checkCactyrantSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(WENDIGO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, WendigoEntity::checkWendigoSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(SCARECROW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, ScarecrowEntity::checkScarecrowSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);       
        event.register(WETA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WetaEntity::checkWetaSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(AVATON.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, AvatonEntity::checkAvatonSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(WRAITH.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, FloatingMobEntity::checkBansheeSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(WISP.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, WispEntity::checkWispSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(MYCOSIS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, MycosisEntity::checkMycosisSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(FRIGID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, FrigidEntity::checkFrigidSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(MUMMY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MummyEntity::checkMummySpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(MUMMY_LORD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MummyLordEntity::checkMummyLordSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(UNDERTAKER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, UndertakerEntity::checkUndertakerSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(BANSHEE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, FloatingMobEntity::checkBansheeSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CACTOID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CactoidEntity::checkCactoidSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(MIMIC.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MimicEntity::checkMimicSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(PTERA.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, PteraEntity::checkPteraSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(SALAMANDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SalamanderEntity::checkSalamanderSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(ENIGMOTH.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, EnigmothEntity::checkEnigmothSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(VESPA.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, VespaEntity::checkVespaSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(PARASITE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ParasiteEntity::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(MUMMIFIED_COD.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UndeadFishEntity::checkUndeadFishSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(BONE_TROUT.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UndeadFishEntity::checkBoneTroutSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(GHOUL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, GhoulEntity::checkGhoulSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(LAMPREY.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SwarmerEntity::checkSwarmerSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(RAVEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, RavenEntity::checkRavenSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(SHROOMLING.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, ShroomlingEntity::checkShroomlingSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(GHOSTRAY.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GhostRayEntity::checkGhostRaySpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(WARPEDFIREFLY.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FlyingMobEntity::checkFlyerSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(GRAVEROBBER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);

        /*
        event.register(UNDEADSWINE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UndeadSwineEntity::checkUndeadSwineSpawnRules);
        event.register(SLUDGELORD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SludgeLordEntity::checkSludgeLordSpawnRules);
        event.register(BONEWORM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BoneWormEntity::checkBoneWormSpawnRules);
        event.register(PINGU.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PinguEntity::checkPinguSpawnRules);
        event.register(FORSAKEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ForsakenEntity::checkForsakenSpawnRules);
        event.register(SKELETONKING.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
        event.register(BEELZEBUB.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, FlyingMobEntity::checkFlyerSpawnRules);*/
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
        event.put(MUMMY_LORD.get(), MummyLordEntity.createAttributes().build());
        event.put(UNDERTAKER.get(), UndertakerEntity.createAttributes().build());
        event.put(BANSHEE.get(), BansheeEntity.createAttributes().build());
        event.put(CACTOID.get(), CactoidEntity.createAttributes().build());
        event.put(SHROOMLING.get(), ShroomlingEntity.createAttributes().build());
        event.put(GHOSTRAY.get(), GhostRayEntity.createAttributes().build());
        event.put(WARPEDFIREFLY.get(), WarpedFireflyEntity.createAttributes().build());
        event.put(GRAVEROBBER.get(), GraveRobberEntity.createAttributes().build());
        event.put(MIMIC.get(), MimicEntity.createAttributes().build());
        event.put(PTERA.get(), PteraEntity.createAttributes().build());
        event.put(SALAMANDER.get(), SalamanderEntity.createAttributes().build());   
        event.put(ENIGMOTH.get(), EnigmothEntity.createAttributes().build());
        event.put(VESPA.get(), VespaEntity.createAttributes().build());
        event.put(COCOON.get(), CocoonEntity.createAttributes().build());
        event.put(SCARAB.get(), ScarabEntity.createAttributes().build());
        event.put(PARASITE.get(), ParasiteEntity.createAttributes().build());
        event.put(MUMMIFIED_COD.get(), Cod.createAttributes().build());
        event.put(BONE_TROUT.get(), Cod.createAttributes().build());
        event.put(GHOUL.get(), GhoulEntity.createAttributes().build());
        event.put(LAMPREY.get(), LampreyEntity.createAttributes().build());
        event.put(RAVEN.get(), RavenEntity.createAttributes().build());

        /*
        event.put(UNDEADSWINE, UndeadSwineEntity.createAttributes().build());
        event.put(SLUDGELORD, SludgeLordEntity.createAttributes().build());
        event.put(BONEWORM, BoneWormEntity.createAttributes().build());
        event.put(PINGU, PinguEntity.createAttributes().build());
        event.put(FORSAKEN, ForsakenEntity.createAttributes().build());
        event.put(SKELETONKING, SkeletonKingEntity.createAttributes().build());
        event.put(BEELZEBUB, BeelzebubEntity.createAttributes().build());
        event.put(BEELZEBUBPUPA, VespaCocoonEntity.createAttributes().build());*/
    }
}
