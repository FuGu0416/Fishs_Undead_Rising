package com.Fishmod.fur.init;

import java.util.function.Supplier;

import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FURBlockRegistry {
	public static final DeferredRegister<Block> DEF_REG = DeferredRegister.create(ForgeRegistries.BLOCKS, mod_LavaCow.MODID);
	
	public static final RegistryObject<Block> ECTOPLASM_BLOCK = registerBlocks("ectoplasm_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).speedFactor(0.4F).noOcclusion().sound(SoundType.NETHER_BRICKS)));
	/*public static final Block GLOWSHROOM = new FURShroomBlock(AbstractBlock.Properties.of(Material.PLANT, MaterialColor.COLOR_CYAN).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).lightLevel((p_235417_0_) -> {
	      return 15;
	   }), "mod_lavacow:glowshroom");
	public static final Block SLUDGEPILE = new CarpetBlock(DyeColor.WHITE, AbstractBlock.Properties.of(Material.WEB, MaterialColor.SNOW).strength(0.2F).sound(SoundType.SLIME_BLOCK).speedFactor(1.3F)).setRegistryName("mod_lavacow:sludgepile");
	public static final Block GLOWSHROOM_BLOCK_STEM = new FURHugeShroomBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(0.2F).sound(SoundType.WOOD)).setRegistryName("mod_lavacow:glowshroom_block_stem");
    public static final Block GLOWSHROOM_BLOCK_CAP = new FURHugeShroomBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_CYAN).strength(0.2F).sound(SoundType.SHROOMLIGHT).randomTicks().lightLevel((p_235439_0_) -> {
	      return 15;
	   })).setRegistryName("mod_lavacow:glowshroom_block_cap");
	public static final Block BLOODTOOTH_SHROOM = new FURShroomBlock(AbstractBlock.Properties.of(Material.PLANT, MaterialColor.COLOR_PINK).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).lightLevel((p_235417_0_) -> {
	      return 1;
	   }), "mod_lavacow:bloodtooth_shroom");
	public static final Block CORDY_SHROOM = new FURShroomBlock(AbstractBlock.Properties.of(Material.PLANT, MaterialColor.COLOR_BROWN).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).lightLevel((p_235417_0_) -> {
	      return 1;
	   }), "mod_lavacow:cordy_shroom");
	public static final Block VEIL_SHROOM = new FURShroomBlock(AbstractBlock.Properties.of(Material.PLANT, MaterialColor.COLOR_BROWN).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).lightLevel((p_235417_0_) -> {
	      return 1;
	   }), "mod_lavacow:veil_shroom");	
	public static final Block TOMBSTONE = new TombStoneBlock(AbstractBlock.Properties.of(Material.STONE).randomTicks().requiresCorrectToolForDrops().strength(1.5F, 10.0F).sound(SoundType.STONE).noOcclusion()).setRegistryName("mod_lavacow:tombstone");
	public static final Block SCARECROWHEAD_COMMON = new ScarecrowHeadBlock(0, AbstractBlock.Properties.of(Material.DECORATION).strength(1.0F).noCollission().noOcclusion().instabreak()).setRegistryName("mod_lavacow:scarecrowhead_common");
	public static final Block SCARECROWHEAD_STRAW = new ScarecrowHeadBlock(1, AbstractBlock.Properties.of(Material.DECORATION).strength(1.0F).noCollission().noOcclusion().instabreak()).setRegistryName("mod_lavacow:scarecrowhead_straw");
	public static final Block SCARECROWHEAD_PLAGUE = new ScarecrowHeadBlock(2, AbstractBlock.Properties.of(Material.DECORATION).strength(1.0F).noCollission().noOcclusion().instabreak()).setRegistryName("mod_lavacow:scarecrowhead_plague");
	public static final Block CACTOID_SPROUT = new CactoidSproutBlock(AbstractBlock.Properties.of(Material.PLANT).randomTicks().strength(0.4F).sound(SoundType.WOOL).noOcclusion()).setRegistryName("mod_lavacow:cactoid_sprout");
	public static final Block GLOWING_AIR = new AirBlock(AbstractBlock.Properties.of(Material.AIR).noCollission().noDrops().air().lightLevel((p_235417_0_) -> {
	      return 15;
	   })).setRegistryName("mod_lavacow:glowing_air");
	public static final Block DISEASED_HAY_BLOCK = new DiseasedHayBlock(AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_GREEN).strength(0.5F).sound(SoundType.GRASS)).setRegistryName("mod_lavacow:diseased_hay_block");*/	
	
	private static RegistryObject<Block> registerBlocks(String name, Supplier<Block> block) {
        RegistryObject<Block> blockObj = DEF_REG.register(name, block);
        FURItemRegistry.DEF_REG.register(name, () -> new BlockItem(blockObj.get(), new Item.Properties()));
        return blockObj;		
	}
}
