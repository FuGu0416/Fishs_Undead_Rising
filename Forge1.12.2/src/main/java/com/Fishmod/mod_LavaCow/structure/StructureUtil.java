package com.Fishmod.mod_LavaCow.structure;

import java.util.Map;
import java.util.Map.Entry;

import com.Fishmod.mod_LavaCow.entities.floating.EntityAvaton;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityMimic;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityMummy;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityScarab;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class StructureUtil {
	private static final IBlockState RED_SAND = Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND);
	
    private static ResourceLocation[] Desert_Tomb_SpawnEntityID = { 
    		EntityList.getKey(EntityScarab.class), 
    		EntityList.getKey(EntityAvaton.class), 
    		EntityList.getKey(EntitySpider.class), 
    		EntityList.getKey(EntityCaveSpider.class), 
    		EntityList.getKey(EntityMummy.class)
    		};
    
    public static boolean CanStructureGenonBlock(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        
        if (!state.isOpaqueCube() || !state.isSideSolid(world, pos, EnumFacing.UP)) {
            return false;
        }
        
        return !world.getBlockState(pos.up()).isOpaqueCube() && !(world.getBlockState(pos.up(2)).getBlock() instanceof BlockLiquid);
    }
    
    public static void GenStructureWithLoot(ResourceLocation structure, ResourceLocation loot, World world, BlockPos pos) {
        EnumFacing facing = EnumFacing.getHorizontal(world.rand.nextInt(4));
        Rotation rotation = getRotationFromFacing(facing);
        MinecraftServer server = world.getMinecraftServer();
        TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
        PlacementSettings settings = new PlacementSettings().setRotation(rotation).setMirror(Mirror.NONE);
        Template template = templateManager.getTemplate(server, structure);

        template.addBlocksToWorld(world, pos, new DesertTombBlockProcessor(pos, settings, loot, false), settings, 2);
    }
    
	public static void GenDesertTomb(ResourceLocation structure, ResourceLocation loot, World world, BlockPos pos) {
        EnumFacing facing = EnumFacing.getHorizontal(world.rand.nextInt(4));
        Rotation rotation = getRotationFromFacing(facing);
        MinecraftServer server = world.getMinecraftServer();
        TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
        PlacementSettings settings = new PlacementSettings().setRotation(rotation).setMirror(Mirror.NONE);
        Template template = templateManager.getTemplate(server, structure);
        Boolean isBadland = false;
        
        for(int i = -8; i < 8; i++) {
        	for(int j = -8; j < 8; j++) {
        		for(int k = -8; k < 8; k++) {
        			if (world.getBlockState(pos.add(i, j, k)).equals(RED_SAND)) {
        				isBadland = true;
        				break;
        			}
        		}
        		
        		if(isBadland) {
        			break;
        		}
        	}
        	
    		if(isBadland) {
    			break;
    		}
        }
        
        template.addBlocksToWorld(world, pos, new DesertTombBlockProcessor(pos, settings, loot, isBadland), settings, 2);
        
        Map<BlockPos, String> map = template.getDataBlocks(pos, settings);
        int GenMimic = world.rand.nextInt(4);
        int i = 0;
        
        for (Entry<BlockPos, String> entry : map.entrySet()) {
        	if(entry.getValue().contains("chest")) {
        		BlockPos blockpos = entry.getKey();

        		if ("chest0".equals(entry.getValue())) {
                    world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 3);
        		}
        		
        		if ("chest1".equals(entry.getValue())) {
        			if (isBadland) {
        				world.setBlockState(blockpos, Blocks.RED_SANDSTONE.getDefaultState().withProperty(BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.CHISELED), 3);       				
        			} else {
        				world.setBlockState(blockpos, Blocks.SANDSTONE.getDefaultState().withProperty(BlockSandStone.TYPE, BlockSandStone.EnumType.CHISELED), 3);
        			}
        		}
        		
        		if ("chest2".equals(entry.getValue())) {
        			if (isBadland) {
        				world.setBlockState(blockpos, Blocks.RED_SANDSTONE.getDefaultState().withProperty(BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.CHISELED), 3);       				
        			} else {
        				world.setBlockState(blockpos, Blocks.SANDSTONE.getDefaultState().withProperty(BlockSandStone.TYPE, BlockSandStone.EnumType.CHISELED), 3);
        			}
        		}
        		
        		if ("chest3".equals(entry.getValue())) {
        			 world.setBlockState(blockpos, Blocks.SANDSTONE.getDefaultState(), 3);
        		}
        		        		
        		if(i == GenMimic) {
        			EnumFacing Chestfacing = (EnumFacing)world.getBlockState(blockpos.down()).getValue(BlockChest.FACING);
        			world.setBlockState(blockpos.down(), Blocks.AIR.getDefaultState(), 3);
      			
        			EntityMimic Mimic = new EntityMimic(world);
        			Mimic.setPosition(blockpos.down().getX() + 0.5D, blockpos.down().getY(), blockpos.down().getZ() + 0.5D);
        			world.spawnEntity(Mimic);
        			Mimic.onInitialSpawn(world.getDifficultyForLocation(blockpos.down()), null);
        			Mimic.setSkin(7);
        			Mimic.inventory.set(0, new ItemStack(FishItems.KINGS_CROWN, 1, 0));
        			Mimic.doMimicChest(Chestfacing);
        		}
        		i++;
        	}
        	
        	if(entry.getValue().contains("mobspawner")) {
        		BlockPos blockpos = entry.getKey();
        		
        		world.setBlockState(blockpos, Blocks.MOB_SPAWNER.getDefaultState(), 3);
                TileEntity tileentity = world.getTileEntity(blockpos);

                if (tileentity instanceof TileEntityMobSpawner)
                {
                    ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityId(Desert_Tomb_SpawnEntityID[world.rand.nextInt(Desert_Tomb_SpawnEntityID.length)]);
                }
        	}        	
        }
    }
    
    public static Rotation getRotationFromFacing(EnumFacing facing) {
        switch (facing) {
            case EAST:
                return Rotation.CLOCKWISE_90;
            case SOUTH:
                return Rotation.CLOCKWISE_180;
            case WEST:
                return Rotation.COUNTERCLOCKWISE_90;
            default:
                return Rotation.NONE;
        }
    }
}
