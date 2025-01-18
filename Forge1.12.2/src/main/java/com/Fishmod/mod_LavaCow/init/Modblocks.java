package com.Fishmod.mod_LavaCow.init;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.lang.reflect.Field;
import java.util.Locale;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.blocks.BlockBasic;
import com.Fishmod.mod_LavaCow.blocks.BlockCactoidSprout;
import com.Fishmod.mod_LavaCow.blocks.BlockEctoplasm;
import com.Fishmod.mod_LavaCow.blocks.BlockGlowShroom;
import com.Fishmod.mod_LavaCow.blocks.BlockGlowShroomHuge;
import com.Fishmod.mod_LavaCow.blocks.BlockMaterial;
import com.Fishmod.mod_LavaCow.blocks.BlockMoltenAlloy;
import com.Fishmod.mod_LavaCow.blocks.BlockPileofSludge;
import com.Fishmod.mod_LavaCow.blocks.BlockScarecrowHead;
import com.Fishmod.mod_LavaCow.blocks.BlockTombStone;
import com.Fishmod.mod_LavaCow.tileentity.TileEntityMimic;
import com.Fishmod.mod_LavaCow.tileentity.TileEntityScarecrowHead;
import com.Fishmod.mod_LavaCow.tileentity.TileEntityScarecrowHead_common;
import com.Fishmod.mod_LavaCow.tileentity.TileEntityScarecrowHead_plague;
import com.Fishmod.mod_LavaCow.tileentity.TileEntityScarecrowHead_straw;

@ObjectHolder(mod_LavaCow.MODID)
public class Modblocks {
	
	public static final BlockGlowShroom GLOWSHROOM = new BlockGlowShroom(true);
	public static final BlockPileofSludge PILEOFSLUDGE= new BlockPileofSludge();
	public static final BlockGlowShroomHuge GLOWSHROOM_BLOCK_STEM = new BlockGlowShroomHuge(MapColor.OBSIDIAN, GLOWSHROOM, false);
	public static final BlockGlowShroomHuge GLOWSHROOM_BLOCK_CAP = new BlockGlowShroomHuge(MapColor.CYAN, GLOWSHROOM, true);
	public static final BlockGlowShroom BLOODTOOTH_SHROOM = new BlockGlowShroom(false);
	public static final BlockGlowShroom CORDY_SHROOM = new BlockGlowShroom(false);
	public static final BlockGlowShroom VEIL_SHROOM = new BlockGlowShroom(false);
	public static final BlockScarecrowHead SCARECROWHEAD_COMMON = new BlockScarecrowHead(0);
	public static final BlockScarecrowHead SCARECROWHEAD_STRAW = new BlockScarecrowHead(1);
	public static final BlockScarecrowHead SCARECROWHEAD_PLAGUE = new BlockScarecrowHead(2);
	public static final BlockTombStone TOMBSTONE = new BlockTombStone();
	public static final BlockCactoidSprout CACTOID_SPROUT = new BlockCactoidSprout();
	public static final BlockEctoplasm ECTOPLASM_BLOCK = new BlockEctoplasm();
	public static final BlockMoltenAlloy MOLTEN_ALLOY_BLOCK = new BlockMoltenAlloy();
	public static final BlockMaterial SCYTHE_CLAW_BLOCK = new BlockMaterial(Material.WOOD, MapColor.GRAY, SoundType.WOOD);
	public static final BlockMaterial VESPA_CARAPACE_BLOCK = new BlockMaterial(Material.WOOD, MapColor.LIME, SoundType.WOOD);
	public static final BlockMaterial MOLTEN_MEAT_BLOCK = new BlockMaterial(Material.WOOD, MapColor.NETHERRACK, SoundType.WOOD);
	
	@ObjectHolder("glowshroom")
    public static final ItemBlock item_block_glowshroom = null;
	@ObjectHolder("pileofsludge")
    public static final ItemBlock item_block_pileofsludge = null;
	@ObjectHolder("glowshroom_block_stem")
	public static final ItemBlock item_block_glowshroom_stem = null;
	@ObjectHolder("glowshroom_block_cap")
	public static final ItemBlock item_block_glowshroom_cap = null;
	@ObjectHolder("bloodtooth_shroom")
    public static final ItemBlock item_block_bloodtooth_shroom = null;
	@ObjectHolder("cordy_shroom")
    public static final ItemBlock item_block_cordy_shroom = null;
	@ObjectHolder("veil_shroom")
    public static final ItemBlock item_block_veil_shroom = null;
	@ObjectHolder("scarecrowhead_common")
	public static final ItemBlock item_scarecrowhead_common = null;
	@ObjectHolder("scarecrowhead_straw")
	public static final ItemBlock item_scarecrowhead_straw = null;
	@ObjectHolder("scarecrowhead_plague")
	public static final ItemBlock item_scarecrowhead_plague = null;
	@ObjectHolder("tombstone")
	public static final ItemBlock tombstone = null;
	@ObjectHolder("cactoid_sprout")
	public static final ItemBlock item_block_cactoid_sprout = null;
	@ObjectHolder("ectoplasm_block")
	public static final ItemBlock item_block_ectoplasm = null;
	@ObjectHolder("molten_alloy_block")
	public static final ItemBlock item_block_molten_alloy = null;
	@ObjectHolder("vespa_carapace_block")
	public static final ItemBlock item_block_vespa_carapace = null;
	@ObjectHolder("scythe_claw_block")
	public static final ItemBlock item_block_scythe_claw = null;
	@ObjectHolder("molten_meat_block")
	public static final ItemBlock item_block_molten_meat = null;
	
	@EventBusSubscriber(modid = mod_LavaCow.MODID)
    public static class RegistrationHandler
    {
	    /**
	     * Register this mod's {@link Block}s.
	     *
	     * @param event The event
	     */
	    @SubscribeEvent
	    public static void registerBlocks(final RegistryEvent.Register<Block> event)
	    {
	        // DEBUG
	        System.out.println("Registering Blocks");

	        final IForgeRegistry<Block> registry = event.getRegistry();
	        
            for (Field field : Modblocks.class.getDeclaredFields()) {
                Object obj;
				try {
					obj = field.get(null);
	                if (obj instanceof Block) {
	                    Block block = (Block) obj;
	                    String name = field.getName().toLowerCase(Locale.ENGLISH);
	                    registry.register(BlockBasic.setBlockName(block, name));
	                }
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
            }
	        
	        GameRegistry.registerTileEntity(TileEntityMimic.class, new ResourceLocation("mod_lavacow:tileEntityMimic"));
	        GameRegistry.registerTileEntity(TileEntityScarecrowHead.class, new ResourceLocation("mod_lavacow:tileEntityScarecrowHead"));
	        GameRegistry.registerTileEntity(TileEntityScarecrowHead_common.class, new ResourceLocation("mod_lavacow:tileEntityScarecrowHead_common"));
	        GameRegistry.registerTileEntity(TileEntityScarecrowHead_straw.class, new ResourceLocation("mod_lavacow:tileEntityScarecrowHead_straw"));
	        GameRegistry.registerTileEntity(TileEntityScarecrowHead_plague.class, new ResourceLocation("mod_lavacow:tileEntityScarecrowHead_plague"));
	    }
		
		/**
         * On model event.
         *
         * @param event the event
         */
        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void onModelEvent(final ModelRegistryEvent event)
        {
            // DEBUG
            System.out.println("Registering block models");
            
            for (Field field : Modblocks.class.getDeclaredFields()) {
                Object obj;
				try {
					obj = field.get(null);
	                if (obj instanceof Block) {
	                    Block block = (Block) obj;
	                    //String name = field.getName().toLowerCase(Locale.ENGLISH);
	                    registerBlockModel(block);
	                }
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
            }
            
            //registerItemBlockModels();
        }
    }

    /**
     * Register block model.
     *
     * @param parBlock the par block
     */
    @SideOnly(Side.CLIENT)
    public static void registerBlockModel(Block parBlock)
    {
        registerBlockModel(parBlock, 0);
    }

    /**
     * Register block model.
     *
     * @param parBlock the par block
     * @param parMetaData the par meta data
     */
    @SideOnly(Side.CLIENT)
    public static void registerBlockModel(Block parBlock, int parMetaData)
    {
//        // DEBUG
//        System.out.println("Registering block model for"
//                + ": " + parBlock.getRegistryName());

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(parBlock), parMetaData,
                new ModelResourceLocation(parBlock.getRegistryName(), "inventory"));
    }

    /**
     * Register item block models.
     */
    @SideOnly(Side.CLIENT)
    public static void registerItemBlockModels()
    {
        for (Field field : Modblocks.class.getDeclaredFields()) {
            Object obj;
			try {
				obj = field.get(null);
                if (obj instanceof ItemBlock) {
                	ItemBlock block = (ItemBlock) obj;
                    //String name = field.getName().toLowerCase(Locale.ENGLISH);
                	registerItemBlockModel(block);
                }
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        }
    }

    /**
     * Register block model.
     *
     * @param parBlock the par block
     */
    @SideOnly(Side.CLIENT)
    public static void registerItemBlockModel(ItemBlock parBlock)
    {
        registerItemBlockModel(parBlock, 0);
    }

    /**
     * Register block model.
     *
     * @param parBlock the par block
     * @param parMetaData the par meta data
     */
    @SideOnly(Side.CLIENT)
    public static void registerItemBlockModel(ItemBlock parBlock, int parMetaData)
    {
//        // DEBUG
//        System.out.println("Registering item block model for"
//                + ": " + parBlock.getRegistryName());
        
        ModelLoader.setCustomModelResourceLocation(parBlock, parMetaData,
                new ModelResourceLocation(parBlock.getRegistryName(), "inventory"));
    }
}
