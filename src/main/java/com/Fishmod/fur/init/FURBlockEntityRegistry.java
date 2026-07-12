package com.Fishmod.fur.init;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.block.blockentity.SalamanderEggBlockEntity;
import com.Fishmod.fur.block.blockentity.ScarecrowHead_commonTileEntity;
import com.Fishmod.fur.block.blockentity.ScarecrowHead_plagueTileEntity;
import com.Fishmod.fur.block.blockentity.ScarecrowHead_strawTileEntity;
import com.Fishmod.fur.block.blockentity.SoulFurnaceBlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FURBlockEntityRegistry {
	public static final DeferredRegister<BlockEntityType<?>> DEF_REG = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, mod_LavaCow.MODID);
	
	public static final RegistryObject<BlockEntityType<ScarecrowHead_commonTileEntity>> SCARECROWHEAD_COMMON = DEF_REG.register("scarecrowhead_common", () -> BlockEntityType.Builder.of(ScarecrowHead_commonTileEntity::new, FURBlockRegistry.SCARECROWHEAD_COMMON.get()).build(null));
	public static final RegistryObject<BlockEntityType<ScarecrowHead_strawTileEntity>> SCARECROWHEAD_STRAW = DEF_REG.register("scarecrowhead_straw", () -> BlockEntityType.Builder.of(ScarecrowHead_strawTileEntity::new, FURBlockRegistry.SCARECROWHEAD_STRAW.get()).build(null));
	public static final RegistryObject<BlockEntityType<ScarecrowHead_plagueTileEntity>> SCARECROWHEAD_PLAGUE = DEF_REG.register("scarecrowhead_plague", () -> BlockEntityType.Builder.of(ScarecrowHead_plagueTileEntity::new, FURBlockRegistry.SCARECROWHEAD_PLAGUE.get()).build(null));  
	public static final RegistryObject<BlockEntityType<SoulFurnaceBlockEntity>> SOUL_FURNACE = DEF_REG.register("soul_furnace", () -> BlockEntityType.Builder.of(SoulFurnaceBlockEntity::new, FURBlockRegistry.SOUL_FURNACE.get()).build(null));    
	public static final RegistryObject<BlockEntityType<SalamanderEggBlockEntity>> SALAMANDER_EGG = DEF_REG.register("salamander_egg", () -> BlockEntityType.Builder.of(SalamanderEggBlockEntity::new, FURBlockRegistry.SALAMANDER_EGG.get()).build(null));
}
