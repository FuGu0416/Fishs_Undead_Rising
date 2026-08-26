package com.Fishmod.fur.init;

import java.util.function.Supplier;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.block.CactoidSproutBlock;
import com.Fishmod.fur.block.DiseasedHayBlock;
import com.Fishmod.fur.block.DreamcatcherBlock;
import com.Fishmod.fur.block.FURHugeShroomBlock;
import com.Fishmod.fur.block.FURShroomBlock;
import com.Fishmod.fur.block.GlimmercapBlock;
import com.Fishmod.fur.block.GlowingAirBlock;
import com.Fishmod.fur.block.LuminousFilamentBlock;
import com.Fishmod.fur.block.LuminousMyceliumBlock;
import com.Fishmod.fur.block.MycelialMatBlock;
import com.Fishmod.fur.block.MycelialTendrilsBlock;
import com.Fishmod.fur.block.EmberwickFungusBlock;
import com.Fishmod.fur.block.MycelialVeinBlock;
import com.Fishmod.fur.block.EnigmothEggBlock;
import com.Fishmod.fur.block.InfestedSandstoneBlock;
import com.Fishmod.fur.block.SalamanderEggBlock;
import com.Fishmod.fur.block.ScarecrowHeadBlock;
import com.Fishmod.fur.block.SoulFurnaceBlock;
import com.Fishmod.fur.block.TombStoneBlock;
import com.Fishmod.fur.item.DreamcatcherItem;
import com.Fishmod.fur.item.ScarecrowHeadItem;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FURBlockRegistry {
	public static final DeferredRegister<Block> DEF_REG = DeferredRegister.create(ForgeRegistries.BLOCKS, mod_LavaCow.MODID);
	
	public static final RegistryObject<Block> BLOODTOOTH_SHROOM = registerBlocks("bloodtooth_shroom", () -> new FURShroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).noCollission().noOcclusion().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).lightLevel(state -> 1)));
	public static final RegistryObject<Block> CACTOID_SPROUT = registerBlocks("cactoid_sprout", () -> new CactoidSproutBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.4F).sound(SoundType.WOOL).noOcclusion().randomTicks().forceSolidOn().dynamicShape().pushReaction(PushReaction.DESTROY).offsetType(BlockBehaviour.OffsetType.XZ)));
	public static final RegistryObject<Block> CORDY_SHROOM = registerBlocks("cordy_shroom", () -> new FURShroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().noOcclusion().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).lightLevel(state -> 1)));
	public static final RegistryObject<Block> DISEASED_HAY_BLOCK = registerBlocks("diseased_hay_block", () -> new DiseasedHayBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BANJO).strength(0.5F).sound(SoundType.GRASS)));
	public static final RegistryObject<Block> DREAMCATCHER = registerBlocks("dreamcatcher", () -> new DreamcatcherBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).strength(0.4F).sound(SoundType.WOOL).noOcclusion().noCollission().pushReaction(PushReaction.DESTROY).emissiveRendering((state, world, pos) -> state.getValue(DreamcatcherBlock.CHARGE) >= 3)));
	public static final RegistryObject<Block> ECTOPLASM_BLOCK = registerBlocks("ectoplasm_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).speedFactor(0.4F).noOcclusion().sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> EMBERWICK_FUNGUS = registerBlocks("emberwick_fungus", () -> new EmberwickFungusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).replaceable().noCollission().noOcclusion().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).lightLevel(state -> 8)));
	public static final RegistryObject<Block> ENIGMOTH_EGG = registerBlocks("enigmoth_egg", () -> new EnigmothEggBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).lightLevel(state -> 3).strength(0.5F).sound(SoundType.METAL).noOcclusion().randomTicks().pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> GLIMMERCAP = registerBlocks("glimmercap", () -> new GlimmercapBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).replaceable().noCollission().noOcclusion().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ).ignitedByLava().pushReaction(PushReaction.DESTROY).lightLevel(state -> 8)));
    public static final RegistryObject<Block> GLIMMERCAP_BLOCK_CAP = registerBlocks("glimmercap_block_cap", () -> new FURHugeShroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).strength(0.2F).sound(SoundType.SHROOMLIGHT).lightLevel(state -> 10)));
	public static final RegistryObject<Block> GLIMMERCAP_BLOCK_STEM = registerBlocks("glimmercap_block_stem", () -> new FURHugeShroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(0.2F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> GLOWING_AIR = registerBlocks("glowing_air", () -> new GlowingAirBlock(BlockBehaviour.Properties.of().noCollission().noOcclusion().air().lightLevel(state -> state.getValue(GlowingAirBlock.LIGHT))));
	public static final RegistryObject<Block> GLOWSHROOM = registerBlocks("glowshroom", () -> new FURShroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).noCollission().noOcclusion().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).lightLevel(state -> 10)));
    public static final RegistryObject<Block> GLOWSHROOM_BLOCK_CAP = registerBlocks("glowshroom_block_cap", () -> new FURHugeShroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(0.2F).sound(SoundType.SHROOMLIGHT).lightLevel(state -> 10).emissiveRendering((state, world, pos) -> true)));
	public static final RegistryObject<Block> GLOWSHROOM_BLOCK_STEM = registerBlocks("glowshroom_block_stem", () -> new FURHugeShroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(0.2F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> INFESTED_SANDSTONE = registerBlocks("infested_sandstone", () -> new InfestedSandstoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(0.4F)));
	public static final RegistryObject<Block> LUMINOUS_FILAMENT = registerBlocks("luminous_filament", () -> new LuminousFilamentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).noCollission().instabreak().sound(SoundType.VINE).noOcclusion().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).emissiveRendering((state, world, pos) -> true)));
	public static final RegistryObject<Block> LUMINOUS_MYCELIUM = registerBlocks("luminous_mycelium", () -> new LuminousMyceliumBlock(BlockBehaviour.Properties.of().mapColor(MapColor.LAPIS).randomTicks().strength(0.6F).sound(SoundType.GRASS)));
	public static final RegistryObject<Block> MYCELIAL_MAT = registerBlocks("mycelial_mat", () -> new MycelialMatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.LAPIS).strength(0.1F).sound(SoundType.MOSS).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> MYCELIAL_TENDRILS = registerBlocks("mycelial_tendrils", () -> new MycelialTendrilsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.LAPIS).replaceable().noCollission().noOcclusion().instabreak().sound(SoundType.NETHER_SPROUTS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).lightLevel(state -> 3)));
	public static final RegistryObject<Block> MYCELIAL_VEIL = registerBlocks("mycelial_veil", () -> new MycelialVeinBlock(BlockBehaviour.Properties.of().mapColor(MapColor.LAPIS).strength(0.1F).sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> SALAMANDER_EGG = registerBlocks("salamander_egg", () -> new SalamanderEggBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).lightLevel(state -> 5).strength(0.5F).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<Block> SCARECROWHEAD_COMMON = registerBlocks("scarecrowhead_common", () -> new ScarecrowHeadBlock(ScarecrowHeadBlock.Types.SCARECROW_COMMON, BlockBehaviour.Properties.of().strength(1.0F).noCollission().noOcclusion().instabreak()));
	public static final RegistryObject<Block> SCARECROWHEAD_PLAGUE = registerBlocks("scarecrowhead_plague", () -> new ScarecrowHeadBlock(ScarecrowHeadBlock.Types.SCARECROW_PLAGUE, BlockBehaviour.Properties.of().strength(1.0F).noCollission().noOcclusion().instabreak()));
	public static final RegistryObject<Block> SCARECROWHEAD_STRAW = registerBlocks("scarecrowhead_straw", () -> new ScarecrowHeadBlock(ScarecrowHeadBlock.Types.SCARECROW_STRAW, BlockBehaviour.Properties.of().strength(1.0F).noCollission().noOcclusion().instabreak()));
	public static final RegistryObject<Block> SOUL_FURNACE = registerBlocks("soul_furnace", () -> new SoulFurnaceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.5F).lightLevel(state -> 10)));
	public static final RegistryObject<Block> TOMBSTONE = registerBlocks("tombstone", () -> new TombStoneBlock(BlockBehaviour.Properties.of().randomTicks().requiresCorrectToolForDrops().strength(1.5F, 10.0F).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).noOcclusion()));
	public static final RegistryObject<Block> VEIL_SHROOM = registerBlocks("veil_shroom", () -> new FURShroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().noOcclusion().randomTicks().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).lightLevel(state -> 1)));
	
	private static RegistryObject<Block> registerBlocks(String name, Supplier<Block> block) {
        RegistryObject<Block> blockObj = DEF_REG.register(name, block);
        if (name.contains("scarecrowhead_")) {
        	FURItemRegistry.DEF_REG.register(name, () -> new ScarecrowHeadItem(blockObj.get(), new Item.Properties()));
        } else if (name.contains("salamander_egg")) {
        	FURItemRegistry.DEF_REG.register(name, () -> new BlockItem(blockObj.get(), new Item.Properties().fireResistant()));
        } else if (name.equals("dreamcatcher")) {
        	FURItemRegistry.DEF_REG.register(name, () -> new DreamcatcherItem(blockObj.get(), new Item.Properties()));
        } else {
        	FURItemRegistry.DEF_REG.register(name, () -> new BlockItem(blockObj.get(), new Item.Properties()));
        }
        return blockObj;		
	}
}
