package com.Fishmod.mod_LavaCow.init;

import java.lang.reflect.Field;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.block.CactoidSproutBlock;
import com.Fishmod.mod_LavaCow.block.FURHugeShroomBlock;
import com.Fishmod.mod_LavaCow.block.FURShroomBlock;
import com.Fishmod.mod_LavaCow.block.ScarecrowHeadBlock;
import com.Fishmod.mod_LavaCow.block.TombStoneBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.DyeColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FURBlockRegistry {
	public static final Block GLOWSHROOM = new FURShroomBlock(AbstractBlock.Properties.of(Material.PLANT, MaterialColor.COLOR_CYAN).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).lightLevel((p_235417_0_) -> {
	      return 15;
	   }), "mod_lavacow:glowshroom");
	public static final Block SLUDGEPILE = new CarpetBlock(DyeColor.WHITE, AbstractBlock.Properties.of(Material.WEB, MaterialColor.SNOW).strength(0.2F).sound(SoundType.SLIME_BLOCK).speedFactor(1.3F)).setRegistryName("mod_lavacow:sludgepile");
	public static final Block GLOWSHROOM_BLOCK_STEM = new FURHugeShroomBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(0.2F).sound(SoundType.WOOD)).setRegistryName("mod_lavacow:glowshroom_block_stem");
    public static final Block GLOWSHROOM_BLOCK_CAP = new FURHugeShroomBlock(AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_CYAN).strength(1.0F).sound(SoundType.SHROOMLIGHT).randomTicks().lightLevel((p_235439_0_) -> {
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
	public static final Block ECTOPLASM_BLOCK = new Block(AbstractBlock.Properties.of(Material.CLAY, MaterialColor.COLOR_CYAN).speedFactor(0.4F).noOcclusion().sound(SoundType.NETHER_BRICKS)).setRegistryName("mod_lavacow:ectoplasm_block");
	
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        try {
            for (Field f : FURBlockRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Block) {
                    event.getRegistry().register((Block) obj);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
